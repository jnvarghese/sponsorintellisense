package com.sposnor.intellisense.sponsorintellisense.web.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
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
import com.sposnor.intellisense.sponsorintellisense.data.model.Receipts;
import com.sposnor.intellisense.sponsorintellisense.data.model.SponsorReceipts;
import com.sposnor.intellisense.sponsorintellisense.mapper.ReceiptsMapper;
import com.sposnor.intellisense.sponsorintellisense.util.HeaderFooterPageEvent;
import com.sposnor.intellisense.sponsorintellisense.util.VelocityTemplateParser;

@RestController
@RequestMapping("/api/renewal")
public class RenewalController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ReceiptsController.class);

	@Autowired
	private ReceiptsMapper receiptsMapper;
	
	
	@GetMapping("/list")
	public List<Receipts> list() {
		LOGGER.debug(" Listing Receipts");
		return receiptsMapper.listByType(2);
	}
	
	@RequestMapping(value = "/generaterenewal/{id}", method = RequestMethod.GET, produces = "application/pdf")
	ResponseEntity<byte[]> generatereceipt(@PathVariable(value = "id") Long receiptId) throws Exception {

		Receipts receipt = receiptsMapper.getReceipt(receiptId);
		
		SponsorReceipts sr = receiptsMapper.getSponsorReceiptByReceiptId(receipt.getReceiptId());
		
		String receiptHtml = VelocityTemplateParser.generateRenewalLetter(receipt, sr.getNoOfRenewal());

		// System.out.println(receipt);
		ByteArrayOutputStream byteArrayPutStream = new ByteArrayOutputStream();
		byte[] pdfBytes = byteArrayPutStream.toByteArray();

		// step 1
		Document document = new Document();
		// step 2

		PdfWriter writer = PdfWriter.getInstance(document, byteArrayPutStream);
		HeaderFooterPageEvent event = new HeaderFooterPageEvent();
		// writer.setPageEvent(event);
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
