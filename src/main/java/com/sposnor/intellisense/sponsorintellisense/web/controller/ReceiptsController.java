package com.sposnor.intellisense.sponsorintellisense.web.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.SdkClientException;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorker;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.itextpdf.tool.xml.html.Tags;
import com.itextpdf.tool.xml.parser.XMLParser;
import com.itextpdf.tool.xml.pipeline.css.CSSResolver;
import com.itextpdf.tool.xml.pipeline.css.CssResolverPipeline;
import com.itextpdf.tool.xml.pipeline.end.PdfWriterPipeline;
import com.itextpdf.tool.xml.pipeline.html.AbstractImageProvider;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipeline;
import com.itextpdf.tool.xml.pipeline.html.HtmlPipelineContext;
import com.sposnor.intellisense.sponsorintellisense.data.model.Initiative;
import com.sposnor.intellisense.sponsorintellisense.data.model.Receipts;
import com.sposnor.intellisense.sponsorintellisense.data.model.Sponsor;
import com.sposnor.intellisense.sponsorintellisense.data.model.SponsorReceipts;
import com.sposnor.intellisense.sponsorintellisense.mapper.InitMapper;
import com.sposnor.intellisense.sponsorintellisense.mapper.ReceiptsMapper;
import com.sposnor.intellisense.sponsorintellisense.mapper.SponsorMapper;
import com.sposnor.intellisense.sponsorintellisense.s3.S3Wrapper;
import com.sposnor.intellisense.sponsorintellisense.util.HeaderFooterPageEvent;
import com.sposnor.intellisense.sponsorintellisense.util.VelocityTemplateParser;

@RestController
@RequestMapping("/api/receipts")
public class ReceiptsController {

	java.text.SimpleDateFormat MYSQL_DATE_FORMAT = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static final Logger LOGGER = LoggerFactory.getLogger(ReceiptsController.class);

	@Autowired
	private ReceiptsMapper receiptsMapper;

	@Autowired
	private InitMapper initMapper;

	@Autowired
	private SponsorMapper sponsorMapper;
	
	@Value("${amazon.s3.receipts}")
	private String receipt_folder;
	
	@Autowired
	S3Wrapper s3Wrapper;
	
	 private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@GetMapping("/amount/{id}")
	public Receipts getSponsorReceiptAmount(@PathVariable(value = "id") Long receiptId) {
		return receiptsMapper.getSponsorReceiptAmount(receiptId);
	}

	@GetMapping("/list")
	@Deprecated
	public List<Receipts> list() {
		LOGGER.debug(" Listing Receipts");
		return receiptsMapper.list();
	}

	@GetMapping("/listbyrange/{id}")
	public List<Receipts> listByRange(@PathVariable(value = "id") int range) {
		return receiptsMapper.listByRange(range);
	}

	@GetMapping("/listbyparish/{id}")
	public List<Receipts> listByParishId(@PathVariable(value = "id") Long parishId) {
		// return receiptsMapper.listByParishId(parishId);
		return null;
	}

	/*@GetMapping("/find/{id}")
	public Receipts listById(@PathVariable(value = "id") Long receiptId) {
		return receiptsMapper.findById(receiptId);
	}*/
	
	@GetMapping("/receipts/{id}")
	public List<Receipts> getReceiptsById(@PathVariable(value = "id") Long receiptId) {
		return receiptsMapper.receiptsById(receiptId);
	}
	
	@GetMapping("/receipts/fn/{firstname}/ln/{lastname}")
	public List<Receipts> getReceiptsById(@PathVariable(value = "firstname") String firstName, 
				@PathVariable(value = "lastname") String lastName) {
		return receiptsMapper.receiptsByMatchingFnAndLn(firstName.replace("0", ""), lastName.replace("0", ""));
	}

	@GetMapping("/listbyreceiptid/{id}")
	public ResponseEntity<List<SponsorReceipts>> listByReceiptId(@PathVariable(value = "id") Long receiptId) {
		List<SponsorReceipts> lists = receiptsMapper.listByReceiptId(receiptId);
		return new ResponseEntity<List<SponsorReceipts>>(lists, HttpStatus.OK);
	}

	// from the Parish to Sponsor distrbution
	@PostMapping("/addSponsorReceipt")
	public ResponseEntity<SponsorReceipts> addSponsorToReceipt(@RequestHeader Long userId,
			@Valid @RequestBody SponsorReceipts r) {
		
		SponsorReceipts toSave = new SponsorReceipts(r.getSponsorId(), r.getReceiptId(), r.getAmount(), userId, 
				Integer.valueOf(r.getType()), r.getMonths().size());
		
		receiptsMapper.insertSponsorReceipts(toSave);
		
		r.getMonths().forEach(obj ->{
			obj.setReceiptId(r.getReceiptId());
			receiptsMapper.insertStudentExtendedMonth(obj);
		});
		
		if(r.getMonths().size()>0) {
			receiptsMapper.updateReferenceIdAndDonationType(r.getParishId(), r.getReceiptId(), userId, 2);
		}else {
			receiptsMapper.updateReferenceId(r.getParishId(), r.getReceiptId(), userId);	
		}
		
		return new ResponseEntity<SponsorReceipts>(toSave, HttpStatus.OK);
	}

	@PutMapping("/modifySponsorReceipt")
	public ResponseEntity<SponsorReceipts> updateReceipts(@RequestHeader Long userId,
			@Valid @RequestBody SponsorReceipts requestPayload) {
		String currentTime = MYSQL_DATE_FORMAT.format(new java.util.Date());
		SponsorReceipts sponsorReceipts = receiptsMapper.getSponsorReceipt(requestPayload.getId());
		receiptsMapper.deleteSponsorReceipts(requestPayload.getId(), userId, currentTime);
		SponsorReceipts sr = new SponsorReceipts(sponsorReceipts.getSponsorId(), sponsorReceipts.getReceiptId(),
				requestPayload.getAmount(), userId, "P");
		receiptsMapper.insertSponsorReceipts(sr);
		return new ResponseEntity<SponsorReceipts>(sr, HttpStatus.OK);
	}

	@PutMapping("/deleteSponsorReceipt/{id}")
	public void deleteReceipts(@RequestHeader Long userId, @PathVariable(value = "id") Long id) {
		String currentTime = MYSQL_DATE_FORMAT.format(new java.util.Date());
		receiptsMapper.deleteSponsorReceipts(id, userId, currentTime);
	}

	private Receipts getSponsorAddedToReceipt(Receipts receipts) {
		Sponsor sponsor = sponsorMapper.findById(receipts.getSponsorId());
		receipts.setFirstName(sponsor.getFirstName());
		receipts.setLastName(sponsor.getLastName());
		receipts.setMiddleName(sponsor.getMiddleInitial());
		receipts.setFullName(sponsor.getFirstName() + " " + sponsor.getLastName());
		receipts.setStreetAddress(sponsor.getStreet());
		receipts.setCity(sponsor.getCity());
		receipts.setState(sponsor.getState());
		receipts.setZipCode(sponsor.getPostalCode());
		receipts.setEmail1(sponsor.getEmailAddress());
		receipts.setPhone1(sponsor.getPhone1());
		return receipts;
	}

	@PostMapping("/add")
	public ResponseEntity<Receipts> createReceipts(@RequestHeader Long userId, @Valid @RequestBody Receipts r) {
		r.setCreatedby(userId);
		if (null != r.getSponsorId()) {  //FIX-ME not used any more
			LOGGER.info("receipt getSponsorId is not null and reference id is " + r.getReferenceId());
			this.getSponsorAddedToReceipt(r);
			if (r.getReferenceId() != 82) {
				receiptsMapper.insert(r);
				receiptsMapper.insertSponsorReceipts(
						new SponsorReceipts(r.getSponsorId(), r.getReceiptId(), r.getAmount(), userId, r.getReceiptType(), r.getNoOfRenewal()));
			}
		} else if (null == r.getSponsorId() && r.getReceiptType() == 2) { // Individual Receipts
			LOGGER.info("receipt getSponsorId is null and receiptType is 2 and reference id is " + r.getReferenceId());
			/*
			Sponsor sponsor = new Sponsor();
			sponsor.setCreatedBy(userId);
			sponsor.setParishId(r.getReferenceId());
			sponsor.setFirstName(r.getFirstName());
			sponsor.setMiddleInitial(r.getMiddleName());
			sponsor.setLastName(r.getLastName());
			sponsor.setPhone1(r.getPhone1());
			sponsor.setStreet(r.getStreetAddress());
			sponsor.setCity(r.getCity());
			sponsor.setState(r.getState());
			sponsor.setPostalCode(r.getZipCode());
			sponsor.setEmailAddress(r.getEmail1());
			sponsor.setCoSponserName(r.getCoSponsor());
			sponsor.setSponsorCode(
					String.valueOf(sponsorMapper.getSequenceByParishId(r.getReferenceId()).getSequence() + 1));*/
			//if (r.getReferenceId() != 82) {
			r.setCreatedby(userId);
			System.out.println(r);
				receiptsMapper.insert(r);
				//sponsorMapper.insert(sponsor); 1/11/20
				//receiptsMapper.insertSponsorReceipts( 1/11/20
					//	new SponsorReceipts(sponsor.getId(), r.getReceiptId(), r.getAmount(), userId, r.getReceiptType(), r.getNoOfRenewal()));
			//}
			//r.setSponsorId(sponsor.getId()); 1/11/20
			//r.setSponsorCode(sponsor.getSponsorCode()); 1/11/20
		} else {
			LOGGER.info("ELSE ---and reference id is " + r.getReferenceId());
			if (null != r.getSponsorId())
				this.getSponsorAddedToReceipt(r);
			if (r.getReferenceId() != 82) {
				receiptsMapper.insert(r);
			}
		}
		return new ResponseEntity<Receipts>(r, HttpStatus.OK);
	}

	@PutMapping("/modify/{id}")
	public void updateReceipts(@RequestHeader Long userId, @Valid @RequestBody Receipts r) {
		r.setUpdatedBy(userId);
		receiptsMapper.update(r);
	}
	
	@RequestMapping(value = "/reprintreceipt/{id}/filename/{filename}", method = RequestMethod.GET, produces = "application/pdf")
	ResponseEntity<byte[]> generateReceiptS3(@RequestHeader Long userId, 
			@PathVariable(value = "id") Long receiptId,
			@PathVariable(value = "filename") String objectKey
			) throws Exception {
		
		byte[] pdfBytes = s3Wrapper.downloadReceipt(objectKey+".pdf");
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/pdf"));
		String filename = "output.pdf";
		headers.setContentDispositionFormData(filename, filename);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(pdfBytes, headers, HttpStatus.OK);
		
		return response;
	}

	@RequestMapping(value = "/createreceipt/{id}", method = RequestMethod.GET, produces = "application/pdf")
	ResponseEntity<byte[]> generateReceipt(@RequestHeader Long userId, 
			@PathVariable(value = "id") Long receiptId
			) throws Exception {

		Receipts receipt = receiptsMapper.getReceipt(receiptId);

		List<Initiative> initiatives = initMapper.getActiveInitiatives();

		Long initiativeId = null;
		String subInitiativeName = null;

		List<Initiative> childtInitiative = initiatives.stream().filter(i -> i.getParentId() != 0)
				.filter(j -> j.getId() == receipt.getInitiativeId()).collect(Collectors.toList());
		if (childtInitiative.size() > 0) {
			subInitiativeName = childtInitiative.get(0).getName();
			initiativeId = childtInitiative.get(0).getParentId();
		}
		final Long initiativeIdToCompare = initiativeId;
		
		initiatives = initiatives.stream()
				.filter(i -> i.getParentId() == 0)
				.collect(Collectors.toList());
				
		initiatives.forEach(j -> {
			if (j.getId() == ((null != initiativeIdToCompare) ? initiativeIdToCompare : receipt.getInitiativeId()))
				j.setAmount(receipt.getAmount());
		});
		

		receipt.setInitiatives(initiatives);
		receipt.setSubInitiativeName(subInitiativeName);
		
		String receiptHtml = VelocityTemplateParser.generateReceipt(receipt);

		// System.out.println(receipt);
		ByteArrayOutputStream byteArrayPutStream = new ByteArrayOutputStream();
		byte[] pdfBytes = byteArrayPutStream.toByteArray();

		// step 1
		Document document = new Document(PageSize.A4, 20, 20, 50, 25);
		// step 2

		PdfWriter writer = PdfWriter.getInstance(document, byteArrayPutStream);
		HeaderFooterPageEvent event = new HeaderFooterPageEvent();
		writer.setPageEvent(event);
		// step 3
		document.open();
		// step 4

		// CSS
		CSSResolver cssResolver = XMLWorkerHelper.getInstance().getDefaultCssResolver(true);

		// HTML
		HtmlPipelineContext htmlContext = new HtmlPipelineContext(null);
		htmlContext.setTagFactory(Tags.getHtmlTagProcessorFactory());
		htmlContext.setImageProvider(new Base64ImageProvider());

		// Pipelines
		PdfWriterPipeline pdf = new PdfWriterPipeline(document, writer);
		HtmlPipeline html = new HtmlPipeline(htmlContext, pdf);
		CssResolverPipeline css = new CssResolverPipeline(cssResolver, html);

		// XML Worker
		XMLWorker worker = new XMLWorker(css, true);
		XMLParser p = new XMLParser(worker);
		p.parse(new ByteArrayInputStream(receiptHtml.getBytes()));

		// step 5
		document.close();

		pdfBytes = byteArrayPutStream.toByteArray();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/pdf"));
		String filename = "output.pdf";
		headers.setContentDispositionFormData(filename, filename);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(pdfBytes, headers, HttpStatus.OK);
		
		try {
			String fileName = receipt.getReceiptId()+"-"+sdf.format(new Date())+".pdf";
			s3Wrapper.upload(byteArrayPutStream.toByteArray(), 
					fileName, userId, receipt_folder);
			receiptsMapper.updateReceiptUploadStatus(receiptId, fileName);
		} catch (SdkClientException ex) {
			ex.printStackTrace();
			LOGGER.error(" Unable to upload receipt to S3" + ex.getMessage());
		}
		
		
		return response;
	}

	class Base64ImageProvider extends AbstractImageProvider {

		@Override
		public Image retrieve(String src) {
			int pos = src.indexOf("base64,");
			try {
				if (src.startsWith("data") && pos > 0) {
					String str = src.substring(pos + 7);
					byte[] img = Base64.getDecoder().decode(str);
					return Image.getInstance(img);
				} else {
					return Image.getInstance(src);
				}
			} catch (BadElementException ex) {
				return null;
			} catch (IOException ex) {
				return null;
			}
		}

		@Override
		public String getImageRootPath() {
			return null;
		}
	}
}
