package com.sposnor.intellisense.sponsorintellisense.web.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
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
import com.sposnor.intellisense.sponsorintellisense.data.model.SponsorshipInfo;
import com.sposnor.intellisense.sponsorintellisense.data.model.ViewEnroll;
import com.sposnor.intellisense.sponsorintellisense.mapper.ManageProgramMapper;
import com.sposnor.intellisense.sponsorintellisense.util.VelocityTemplateParser;

@RestController
@RequestMapping("/api")
public class ManageProgramController {

	@Autowired
	private ManageProgramMapper manageProgramMapper;
	
	@GetMapping("/view/enrollment")
	public List<ViewEnroll> listEnrollments() {
		return manageProgramMapper.selectEnrollments();
	}
	
	@RequestMapping(value = "/enrollment/generatereport", method = RequestMethod.GET, produces = "application/pdf")
	ResponseEntity<byte[]> generatePdf() throws Exception{
		
		String htmlstring = VelocityTemplateParser.generateHTML();
		
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
