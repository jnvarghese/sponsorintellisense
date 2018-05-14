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
import com.sposnor.intellisense.sponsorintellisense.data.model.Contribution;
import com.sposnor.intellisense.sponsorintellisense.data.model.Receipt;
import com.sposnor.intellisense.sponsorintellisense.data.model.SponseeReport;
import com.sposnor.intellisense.sponsorintellisense.data.model.SponsorReport;
import com.sposnor.intellisense.sponsorintellisense.data.model.SponsorshipInfo;
import com.sposnor.intellisense.sponsorintellisense.data.model.ViewEnroll;
import com.sposnor.intellisense.sponsorintellisense.mapper.ManageProgramMapper;
import com.sposnor.intellisense.sponsorintellisense.mapper.SponsorMapper;
import com.sposnor.intellisense.sponsorintellisense.mapper.StudentMapper;
import com.sposnor.intellisense.sponsorintellisense.util.VelocityTemplateParser;
//import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

@RestController
@RequestMapping("/api")
public class ManageProgramController {

	@Autowired
	private ManageProgramMapper manageProgramMapper;
	
	@Autowired
	private SponsorMapper sponsorMapper;
	
	@Autowired
	private StudentMapper studentMapper;
	
	@GetMapping("/view/enrollment")
	public List<ViewEnroll> listEnrollments() {
		return manageProgramMapper.selectEnrollments();
	}
	
	private Map<String,Object> getDataMap(SponsorReport sponser, List<SponseeReport> sponseeList){
		SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd yyyy");
		Date date = new Date();
		String time = sdf.format(date);
		
		
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("timeNow", time);
		map.put("sponsorId", sponser.getUniqueId());
		map.put("sponsorName", sponser.getSponsorName());
		map.put("sponsorParish", sponser.getParishName() +" - "+sponser.getParishCity());
		map.put("sponsorParishRegion", sponser.getRegionName()+" - "+sponser.getCenterName());
		if(null != sponser.getAppartmentNumber()) {
			map.put("sponsorAddress", sponser.getAppartmentNumber()+", "+sponser.getStreet()+ ", "+sponser.getSponsorCity()+", "+sponser.getSponsorState()+" - "+sponser.getPostalCode());
		}else {
			map.put("sponsorAddress", sponser.getStreet()+ ", "+sponser.getSponsorCity()+", "+sponser.getSponsorState()+" - "+sponser.getPostalCode());
		}
		
		map.put("sponsorPhone", sponser.getPhone1());
		map.put("sponsorEmail", sponser.getEmailAddress() == null  ? "N/A" : sponser.getEmailAddress());
		map.put("sponseeList", sponseeList);
		map.put("totalChildrenSposored", sponseeList.size());
		map.put("totalPaymentReceived", sponser.getContribution());
		map.put("spnStartDate", sponser.getEffectiveDate());
		map.put("renewalDue", sponser.getRenewalDue());		
		map.put("sign2", sponser.getSign2());
		map.put("sign1", sponser.getSign1());
		map.put("waterMark", sponser.getWaterMark());
		
		return map;
	}
	@RequestMapping(value = "/enrollment/generatereport/{enrollmentId}", method = RequestMethod.GET, produces = "application/pdf")
	ResponseEntity<byte[]> generatePdf(@PathVariable(value = "enrollmentId") Long enrollmentId) throws Exception{
		
		List<SponseeReport> sponseeList= sponsorMapper.listSponseesByEnrolmentId(enrollmentId);
		for(SponseeReport sr: sponseeList) {
			//System.out.println(" --- " +sr.getUniqueId());
		}
		SponsorReport sponsorReport = studentMapper.findSponsorByEnrolmentId(enrollmentId);
		
		String coverLetter = VelocityTemplateParser.generateCoverLetter(sponsorReport, sponseeList.size());
		String htmlstring = VelocityTemplateParser.generateHTML(getDataMap(sponsorReport,sponseeList));
		String consolidatedData =  coverLetter + htmlstring;
		//System.out.println(" consolidatedData "+consolidatedData);    

		ByteArrayOutputStream byteArrayPutStream = new ByteArrayOutputStream();
		 byte[] pdfBytes = byteArrayPutStream.toByteArray();
  
	     // step 1
        Document document = new Document();
        // step 2
     
        PdfWriter writer = PdfWriter.getInstance(document, byteArrayPutStream);
        // step 3
        document.open();
        // step 4   
        
        // CSS
        CSSResolver cssResolver = 
                XMLWorkerHelper.getInstance().getDefaultCssResolver(true);
 
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
	        String result = null;

	        List<SponseeReport> sponseeList= sponsorMapper.listSponseesByEnrolmentId(new Long(9));
			SponsorReport sponsorReport = studentMapper.findSponsorByEnrolmentId(new Long(9));
			
			String htmlstring = VelocityTemplateParser.generateHTML(getDataMap(sponsorReport,sponseeList));
	       // result = writer.toString();

	        return htmlstring;
	    }
	
	@GetMapping("/manage/view/{id}")
	public List<SponsorshipInfo> listSponsorShipInfo(@PathVariable(value = "id") Long studentId) {
		return manageProgramMapper.getSponsorshipInfoByStudentId(studentId);
	}
	
	@GetMapping("/manage/viewcontribution/{sponsorid}/{studentid}")
	public List<Contribution> getSponsorshipContribution
		(@PathVariable(value = "studentid") Long studentId, @PathVariable(value = "sponsorid") Long sponsorId) {
		return manageProgramMapper.getSponsorshipContribution(studentId, sponsorId);
	}
	 

	@RequestMapping(value = "/enrollment/generatereceipt/{enrollmentId}", method = RequestMethod.GET, produces = "application/pdf")
	ResponseEntity<byte[]> generatereceipt(@PathVariable(value = "enrollmentId") Long enrollmentId) throws Exception{
		
		Receipt receipt =  manageProgramMapper.getReceipt(enrollmentId);
		String receiptHtml = VelocityTemplateParser.generateReceipt(receipt);
		
		//System.out.println(receipt);
		ByteArrayOutputStream byteArrayPutStream = new ByteArrayOutputStream();
		 byte[] pdfBytes = byteArrayPutStream.toByteArray();
 
	     // step 1
       Document document = new Document();
       // step 2
    
       PdfWriter writer = PdfWriter.getInstance(document, byteArrayPutStream);
       // step 3
       document.open();
       // step 4   
       
       // CSS
       CSSResolver cssResolver = 
               XMLWorkerHelper.getInstance().getDefaultCssResolver(true);

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
	            }
	            else {
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
