package com.sposnor.intellisense.sponsorintellisense.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

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

public class VelocityReceiptGeneratorRunner {

	
	public static void main(String[] args) throws Exception {
		
		VelocityReceiptGeneratorRunner parser = new VelocityReceiptGeneratorRunner();
		
		parser.generatePdf();
		
	}
	
	private void generatePdf() throws Exception {
   
		List<Initiative> initiatives = new ArrayList<Initiative>();
		Initiative i = new Initiative();
		i.setName("A");
		initiatives.add(i);
		
	    Receipts receipt = new Receipts();
	    
	    receipt.setReceiptId(2342L);
	    receipt.setRdate("01/14/2021");
	    receipt.setFullName("");
	    receipt.setFirstName("Jinu");
	    receipt.setMiddleName("G");
	    receipt.setLastName("Varghese");
	    receipt.setStreetAddress("354 Spotswood Englishtown Rd");
	    receipt.setCity("Monroe");
	    receipt.setState("NJ");
	    receipt.setZipCode("08831");
	    receipt.setReceiptType(2);
	    receipt.setParishName("Test Parish");
	    receipt.setParishCity("Test P City");
	    receipt.setOrgName("Test Org");
	    receipt.setInitiativeName("L2L");
	    receipt.setInitiatives(initiatives);
	    receipt.setSubInitiativeName("Test Sub Init");
	    receipt.setAmount(240.00);
	    receipt.setReceiptType(2);
	    receipt.setTransaction("Paypall");
	    receipt.setAmountInWords("two hundred");
	    receipt.setCoSponsorName("Test Co Spo");

	 
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
		
		OutputStream out = new FileOutputStream("C://Users//HP//Desktop//"+System.currentTimeMillis()+".pdf");
		out.write(pdfBytes);
		out.close();
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
