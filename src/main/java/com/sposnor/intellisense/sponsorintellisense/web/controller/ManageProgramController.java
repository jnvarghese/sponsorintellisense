package com.sposnor.intellisense.sponsorintellisense.web.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.ExceptionConverter;
import com.itextpdf.text.Image;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfTemplate;
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
import com.sposnor.intellisense.sponsorintellisense.data.model.Contribution;
import com.sposnor.intellisense.sponsorintellisense.data.model.EnrollmentSummary;
import com.sposnor.intellisense.sponsorintellisense.data.model.Parish;
import com.sposnor.intellisense.sponsorintellisense.data.model.SponseeReport;
import com.sposnor.intellisense.sponsorintellisense.data.model.SponsorReport;
import com.sposnor.intellisense.sponsorintellisense.data.model.SponsorshipInfo;
import com.sposnor.intellisense.sponsorintellisense.data.model.StudentSummary;
import com.sposnor.intellisense.sponsorintellisense.data.model.ViewEnroll;
import com.sposnor.intellisense.sponsorintellisense.mapper.ManageProgramMapper;
import com.sposnor.intellisense.sponsorintellisense.mapper.ParishMapper;
import com.sposnor.intellisense.sponsorintellisense.mapper.SponsorMapper;
import com.sposnor.intellisense.sponsorintellisense.mapper.StudentMapper;
import com.sposnor.intellisense.sponsorintellisense.s3.S3Wrapper;
import com.sposnor.intellisense.sponsorintellisense.util.VelocityTemplateParser;
//import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
@RestController
@RequestMapping("/api")
public class ManageProgramController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ManageProgramController.class);
	
	@Autowired
	private ParishMapper parishMapper;
	
	@Autowired
	private ManageProgramMapper manageProgramMapper;

	@Autowired
	private SponsorMapper sponsorMapper;

	@Autowired
	private StudentMapper studentMapper;
	
	@Autowired
	S3Wrapper s3Wrapper;

	@GetMapping("/view/enrollment/{id}")
	public List<ViewEnroll> listEnrollments(@PathVariable(value = "id") Long parishId) {
		return manageProgramMapper.selectEnrollments(parishId);
	}

	private Map<String, Object> getDataMap(SponsorReport sponser, List<SponseeReport> sponseeList) {
		SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd yyyy");
		Date date = new Date();
		String time = sdf.format(date);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("timeNow", time);
		map.put("sponsorId", sponser.getUniqueId());
		map.put("sponsorName", sponser.getSponsorName());
		map.put("sponsorParish", sponser.getParishName() + " - " + sponser.getParishCity());
		map.put("sponsorParishRegion", sponser.getRegionName() + " - " + sponser.getCenterName());
		if (null != sponser.getAppartmentNumber()) {
			map.put("sponsorAddress", sponser.getAppartmentNumber() + " " + sponser.getStreet() + ", "
					+ sponser.getSponsorCity() + ", " + sponser.getSponsorState() + " - " + sponser.getPostalCode());
		} else {
			map.put("sponsorAddress", sponser.getStreet() + ", " + sponser.getSponsorCity() + ", "
					+ sponser.getSponsorState() + " - " + sponser.getPostalCode());
		}

		map.put("sponsorPhone", sponser.getPhone1());
		map.put("sponsorEmail", sponser.getEmailAddress() == null ? "N/A" : sponser.getEmailAddress());
		map.put("sponseeList", sponseeList);
		map.put("totalChildrenSposored", sponseeList.size());
		
		if(sponser.getNetContribution().equals("0.00")) {
			map.put("fundUsed", sponser.getContribution());
			map.put("totalSponsorshipReceived", sponser.getTotal());
		}else {
			map.put("fundUsed", sponser.getNetContribution());
			map.put("totalSponsorshipReceived", sponser.getNetTotal());
		}
		map.put("totalBalance", sponser.getMiscAmount());
		map.put("spnStartDate", sponser.getPaymentDate());
		map.put("renewalDue", sponser.getRenewalDue());
		map.put("sign2", sponser.getSign2());
		map.put("sign1", sponser.getSign1());
		map.put("waterMark", sponser.getWaterMark());

		return map;
	} 

	@RequestMapping(value = "/enrollment/generatereport/{enrollmentId}", method = RequestMethod.GET, produces = "application/pdf")
	ResponseEntity<byte[]> generatePdf(@PathVariable(value = "enrollmentId") Long enrollmentId) throws Exception {

		List<SponseeReport> sponseeList = studentMapper.listSponseesByEnrolmentId(enrollmentId);
 
		for(SponseeReport sr : sponseeList) {
			if("Y".equalsIgnoreCase(sr.getUploadstatus()))
			sr.setProfilePicture(s3Wrapper.downloadProfilePicture(sr.getProjectId(), sr.getStudentId(), sr.getImageLinkRef()));
		}
		
		SponsorReport sponsorReport = sponsorMapper.findSponsorByEnrolmentId(enrollmentId);

		String coverLetter = VelocityTemplateParser.generateCoverLetter(sponsorReport, sponseeList.size());
		String htmlstring = VelocityTemplateParser.generateHTML(getDataMap(sponsorReport, sponseeList),sponseeList.size());
		String consolidatedData = (coverLetter + htmlstring).replaceAll("&", "&amp;");
		 //System.out.println(" consolidatedData "+consolidatedData);

		ByteArrayOutputStream byteArrayPutStream = new ByteArrayOutputStream();
		byte[] pdfBytes = byteArrayPutStream.toByteArray();

		// step 1
		Document document = new Document();
		// step 2

		PdfWriter writer = PdfWriter.getInstance(document, byteArrayPutStream);
		// TableHeader event = new TableHeader();
		// writer.setPageEvent(event);
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
		p.parse(new ByteArrayInputStream(consolidatedData.getBytes()));

		// step 5
		document.close();

		pdfBytes = byteArrayPutStream.toByteArray();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/pdf"));
		String filename = "output.pdf";
		headers.setContentDispositionFormData(filename, filename);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(pdfBytes, headers, HttpStatus.OK);
		return response;
	}

	@RequestMapping("/exampleVelocity/{enrollmentId}")
	String home(@PathVariable(value = "enrollmentId") Long enrollmentId) throws Exception {

		List<SponseeReport> sponseeList = studentMapper.listSponseesByEnrolmentId(new Long(9));
		SponsorReport sponsorReport = sponsorMapper.findSponsorByEnrolmentId(new Long(9));

		String htmlstring = VelocityTemplateParser.generateHTML(getDataMap(sponsorReport, sponseeList), sponseeList.size());
		// result = writer.toString();
  
		return htmlstring;
	}
	
	
	@GetMapping("/manage/view/{id}")
	public List<SponsorshipInfo> listSponsorShipInfo(@PathVariable(value = "id") Long studentId) {
		return manageProgramMapper.getSponsorshipInfoByStudentId(studentId);
	}
	
	@RequestMapping(value = "/enrollment/summarypdf/{id}", method = RequestMethod.GET, produces = "application/pdf")
	ResponseEntity<byte[]> generateSummaryPdf(@PathVariable(value = "id") Long parishId) throws Exception {
		
		Parish parish = parishMapper.findById(parishId);
		
	    List<EnrollmentSummary> list = manageProgramMapper.getSummaryByParishId(parishId);
		list.stream().forEach(i-> {
			List<StudentSummary> studentlist = manageProgramMapper.getStudentByEnrollmentId(i.getEnrollmentId());
			if(!ObjectUtils.isEmpty(studentlist)) {
				i.setStudents(studentlist);
				i.setNumberOfStudents(studentlist.size());
			}
		});
	          
		String summary = VelocityTemplateParser.generateSummary(list, parish);
	
		ByteArrayOutputStream byteArrayPutStream = new ByteArrayOutputStream();
		byte[] pdfBytes = byteArrayPutStream.toByteArray();

		// step 1
		Document document = new Document();
		// step 2

		PdfWriter writer = PdfWriter.getInstance(document, byteArrayPutStream);
		// TableHeader event = new TableHeader();
		// writer.setPageEvent(event);
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
		p.parse(new ByteArrayInputStream(summary.getBytes()));

		// step 5
		document.close();

		pdfBytes = byteArrayPutStream.toByteArray();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/pdf"));
		String filename = "output.pdf";
		headers.setContentDispositionFormData(filename, filename);
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(pdfBytes, headers, HttpStatus.OK);
		return response;
	}

	
	@GetMapping("/enrollment/viewsummary/{id}")
	public List<EnrollmentSummary> getSummary(@PathVariable(value = "id") Long parishId) {
		List<EnrollmentSummary> list = manageProgramMapper.getSummaryByParishId(parishId);
		list.stream().forEach(i-> {
			List<StudentSummary> studentlist = manageProgramMapper.getStudentByEnrollmentId(i.getEnrollmentId());
			if(!ObjectUtils.isEmpty(studentlist)) {
				i.setStudents(studentlist);
				i.setNumberOfStudents(studentlist.size());
			}
		});
		return list;
	}

	@GetMapping("/manage/viewcontribution/{sponsorid}/{studentid}")
	public List<Contribution> getSponsorshipContribution(@PathVariable(value = "studentid") Long studentId,
			@PathVariable(value = "sponsorid") Long sponsorId) {
		return manageProgramMapper.getSponsorshipContribution(studentId, sponsorId);
	}
/*
	@RequestMapping(value = "/enrollment/generatereceipt/{enrollmentId}", method = RequestMethod.GET, produces = "application/pdf")
	ResponseEntity<byte[]> generatereceipt(@PathVariable(value = "enrollmentId") Long enrollmentId) throws Exception {

		Receipt receipt = manageProgramMapper.getReceipt(enrollmentId);
		String receiptHtml = VelocityTemplateParser.generateReceipt(receipt);

		// System.out.println(receipt);
		ByteArrayOutputStream byteArrayPutStream = new ByteArrayOutputStream();
		byte[] pdfBytes = byteArrayPutStream.toByteArray();

		// step 1
		Document document = new Document();
		// step 2

		PdfWriter writer = PdfWriter.getInstance(document, byteArrayPutStream);
		// TableHeader event = new TableHeader();
		// writer.setPageEvent(event);
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
		return response;
	}
*/
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

	/**
	 * Inner class to add a table as header.
	 */
	class TableHeader extends PdfPageEventHelper {
		/** The header text. */
		String header;
		/** The template with the total number of pages. */
		PdfTemplate total;

		/**
		 * Allows us to change the content of the header.
		 * 
		 * @param header
		 *            The new header String
		 */
		public void setHeader(String header) {
			this.header = header;
		}

		/**
		 * Creates the PdfTemplate that will hold the total number of pages.
		 * 
		 * @see com.itextpdf.text.pdf.PdfPageEventHelper#onOpenDocument(
		 *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
		 */
		public void onOpenDocument(PdfWriter writer, Document document) {
			total = writer.getDirectContent().createTemplate(30, 16);
		}

		/**
		 * Adds a header to every page
		 * 
		 * @see com.itextpdf.text.pdf.PdfPageEventHelper#onEndPage(
		 *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
		 */
		public void onEndPage(PdfWriter writer, Document document) {
			PdfPTable table = new PdfPTable(3);
			try {
				table.setWidths(new int[] { 24, 24, 2 });
				table.setTotalWidth(527);
				table.setLockedWidth(true);
				table.getDefaultCell().setFixedHeight(20);
				table.getDefaultCell().setBorder(Rectangle.BOTTOM);
				table.addCell(header);
				table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
				table.addCell(String.format("Page %d of", writer.getPageNumber()));
				PdfPCell cell = new PdfPCell(Image.getInstance(total));
				cell.setBorder(Rectangle.BOTTOM);
				table.addCell(cell);
				table.writeSelectedRows(0, -1, 34, 803, writer.getDirectContent());
			} catch (DocumentException de) {
				throw new ExceptionConverter(de);
			}
		}

		/**
		 * Fills out the total number of pages before the document is closed.
		 * 
		 * @see com.itextpdf.text.pdf.PdfPageEventHelper#onCloseDocument(
		 *      com.itextpdf.text.pdf.PdfWriter, com.itextpdf.text.Document)
		 */
		public void onCloseDocument(PdfWriter writer, Document document) {
			ColumnText.showTextAligned(total, Element.ALIGN_LEFT,
					new Phrase(String.valueOf(writer.getPageNumber() - 1)), 2, 2, 0);
		}
	}

}
