package com.sposnor.intellisense.sponsorintellisense.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;

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
import com.sposnor.intellisense.sponsorintellisense.data.model.SponsorReport;

public class VelocityPDFSponsorshipCoverLetter {

	
	public static void main(String[] args) throws Exception {
		
		VelocityPDFSponsorshipCoverLetter parser = new VelocityPDFSponsorshipCoverLetter();
		
		parser.generatePdf();
		
	}
	
	private void generatePdf() throws Exception {
		SponsorReport sr = new SponsorReport();
		sr.setSponsorName("Sponsor Name Jacob");
		sr.setParishName(" Test Parish");
		sr.setParishCity("Test City");
		
		
		String receiptHtml = VelocityTemplateParser.generateCoverLetter(sr, 2);

		// System.out.println(receipt);
		ByteArrayOutputStream byteArrayPutStream = new ByteArrayOutputStream();
		byte[] pdfBytes = byteArrayPutStream.toByteArray();

		// step 1
		Document document = new Document();
		// step 2

		PdfWriter writer = PdfWriter.getInstance(document, byteArrayPutStream);
		HeaderFooterPageEvent event = new HeaderFooterPageEvent();
		writer.setPageEvent(event);
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
