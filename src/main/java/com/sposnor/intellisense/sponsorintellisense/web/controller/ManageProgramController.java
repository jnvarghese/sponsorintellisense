package com.sposnor.intellisense.sponsorintellisense.web.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
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

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import com.sposnor.intellisense.sponsorintellisense.data.model.Contribution;
import com.sposnor.intellisense.sponsorintellisense.data.model.SponseeReport;
import com.sposnor.intellisense.sponsorintellisense.data.model.SponsorReport;
import com.sposnor.intellisense.sponsorintellisense.data.model.SponsorshipInfo;
import com.sposnor.intellisense.sponsorintellisense.data.model.ViewEnroll;
import com.sposnor.intellisense.sponsorintellisense.mapper.ManageProgramMapper;
import com.sposnor.intellisense.sponsorintellisense.mapper.SponsorMapper;
import com.sposnor.intellisense.sponsorintellisense.mapper.StudentMapper;
import com.sposnor.intellisense.sponsorintellisense.util.VelocityTemplateParser;

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
		Map<String, Object> map = new HashMap<String,Object>();
		map.put("sponsorId", sponser.getUniqueId());
		map.put("sponsorName", sponser.getSponsorName());
		map.put("sponsorParish", sponser.getParishName() +" "+sponser.getParishCity());
		map.put("sponsorParishRegion", sponser.getRegionName()+" "+sponser.getCenterName());
		map.put("sponsorAddress", sponser.getAppartmentNumber()+" "+sponser.getStreet()+ " "+sponser.getSponsorCity()+" "+sponser.getSponsorState()+" "+sponser.getPostalCode());
		map.put("sponsorPhone", "N/A");
		map.put("sponsorEmail", sponser.getEmailAddress() == null  ? "N/A" : sponser.getEmailAddress());
		map.put("sponseeList", sponseeList);
		map.put("totalChildrenSposored", sponseeList.size());
		map.put("totalPaymentReceived", sponser.getContribution());
		map.put("spnStartDate", sponser.getEffectiveDate());
		return map;
	}
	@RequestMapping(value = "/enrollment/generatereport/{enrollmentId}", method = RequestMethod.GET, produces = "application/pdf")
	ResponseEntity<byte[]> generatePdf(@PathVariable(value = "enrollmentId") Long enrollmentId) throws Exception{
		
		List<SponseeReport> sponseeList= sponsorMapper.listSponseesByEnrolmentId(enrollmentId);
		SponsorReport sponsorReport = studentMapper.findSponsorByEnrolmentId(enrollmentId);
		
		String htmlstring = VelocityTemplateParser.generateHTML(getDataMap(sponsorReport,sponseeList));
		
        ByteArrayOutputStream byteArrayPutStream = new ByteArrayOutputStream();
        byte[] pdfBytes = byteArrayPutStream.toByteArray();
        
		InputStream is = new ByteArrayInputStream(htmlstring.getBytes());
		
		 // step 1
        Document document = new Document();
        // step 2
        PdfWriter writer = PdfWriter.getInstance(document, byteArrayPutStream);
        writer.setInitialLeading(12);
        // step 3
        document.open();
        // step 4
        XMLWorkerHelper.getInstance().parseXHtml(writer, document, is);
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
	
	@GetMapping("/manage/view/{id}")
	public List<SponsorshipInfo> listSponsorShipInfo(@PathVariable(value = "id") Long studentId) {
		return manageProgramMapper.getSponsorshipInfoByStudentId(studentId);
	}
	
	@GetMapping("/manage/viewcontribution/{sponsorid}/{studentid}")
	public List<Contribution> getSponsorshipContribution
		(@PathVariable(value = "studentid") Long studentId, @PathVariable(value = "sponsorid") Long sponsorId) {
		return manageProgramMapper.getSponsorshipContribution(studentId, sponsorId);
	}
	
}
