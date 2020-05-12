package com.sposnor.intellisense.sponsorintellisense.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

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
import com.sposnor.intellisense.sponsorintellisense.data.model.SponseeReport;

public class VelocityPDFGeneratorRunnerSubstitution {

	
	public static void main(String[] args) throws Exception {
		
		VelocityPDFGeneratorRunnerSubstitution parser = new VelocityPDFGeneratorRunnerSubstitution();
		
		parser.generatePdf();
		
	}
	
	private void generatePdf() throws Exception {
		
		BufferedImage bImage = ImageIO.read(new File("C:\\Users\\HP\\Downloads\\1999.png"));
	      ByteArrayOutputStream bos = new ByteArrayOutputStream();
	      ImageIO.write(bImage, "png", bos );
	      byte [] largeByte = bos.toByteArray();
	      
	      BufferedImage bImage2 = ImageIO.read(new File("C:\\Users\\HP\\Downloads\\1999.png"));
	      ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
	      ImageIO.write(bImage2, "png", bos2 );
	      byte [] smallByte = bos2.toByteArray();
	      
		Map<String,List<SponseeReport>> substitutions= new HashMap<String,List<SponseeReport>>();
		List<SponseeReport> sponseeList = new ArrayList<SponseeReport>();
		SponseeReport sr = new SponseeReport();
		sr.setUniqueId("KKD-PPCL-5062");
		sr.setStudentName("ALEN GEORGE");
		sr.setDateOfBirth("DECEMBER 27,2007");
		sr.setGender("Male");
		sr.setGrade("6th");
		sr.setBaseLanguage("Malayalam");
		sr.setHobby("XXXX");
		sr.setFavColor("YYYY");
		sr.setFavGame("ZZZZ");
		sr.setNameOfGuardian("Thomas George");
		sr.setOccupationOfGuardian("Fishermen");
		sr.setProjectName("KOTTAYAM KOCHI DIOCESE");
		sr.setAddress("Pazhampallichal - Kerala, India");
		sr.setRenewalDue("-");
		sr.setProfilePicture(smallByte);
		sponseeList.add(sr);
		
		sr = new SponseeReport();
		sr.setStudentName("studentName2");
		sr.setUniqueId("KKD-PPCL-5036");
		sr.setDateOfBirth("August 09 2007");
		sr.setGender("Female");
		sr.setGrade("6th");
		sr.setBaseLanguage("Malayalam");
		sr.setHobby("Drawing");
		sr.setFavColor("Rose");
		sr.setFavGame("Foot Ball");
		sr.setNameOfGuardian("Murugadas");
		sr.setOccupationOfGuardian("Farmer");
		sr.setProjectName("KOTTAYAM KOCHI DIOCESE");
		sr.setAddress("Pazhampallichal - Kerala, India");
		sr.setRenewalDue("April 2020");
		sr.setProfilePicture(largeByte);
		sponseeList.add(sr);
		
		substitutions.put("SRK-CGP-1000 to SRK-CGP-2020", sponseeList);
		//substitutions.put("Child2", sponseeList);
		//sr = new SponseeReport();
		//sponseeList.add(sr);
		
		Map<String,Object> dataMap = new HashMap<String,Object>();
		dataMap.put("timeNow", "3434");
		dataMap.put("sponsorId", "2");
		dataMap.put("sponsorName", "3");
		dataMap.put("sponsorParish", "4");
		dataMap.put("sponsorParishRegion", "5");
		dataMap.put("sponsorAddress", "6");
		dataMap.put("sponsorPhone", "7");
		dataMap.put("sponsorEmail", "8");
		dataMap.put("renewalDue", "9");
		dataMap.put("totalChildrenSposored", "10");
		dataMap.put("spnStartDate", "12");
		dataMap.put("totalPaymentReceived", "13");
		dataMap.put("fundUsed", "14");
		dataMap.put("totalBalance", "15");
		dataMap.put("totalSponsorshipReceived", "16");
		dataMap.put("substitutions", substitutions);

		
		
		String receiptHtml = VelocityTemplateParser.generateHTML(dataMap, 1, "substitution");

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
