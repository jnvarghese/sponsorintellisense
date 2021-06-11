package com.sposnor.intellisense.sponsorintellisense.web.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.sposnor.intellisense.sponsorintellisense.data.model.Sponsee;
import com.sposnor.intellisense.sponsorintellisense.data.model.SponsorReport;
import com.sposnor.intellisense.sponsorintellisense.data.model.Student;
import com.sposnor.intellisense.sponsorintellisense.data.model.Substitution;
import com.sposnor.intellisense.sponsorintellisense.mapper.EnrollmentMapper;
import com.sposnor.intellisense.sponsorintellisense.mapper.SponsorMapper;
import com.sposnor.intellisense.sponsorintellisense.mapper.StudentMapper;
import com.sposnor.intellisense.sponsorintellisense.s3.S3Wrapper;
import com.sposnor.intellisense.sponsorintellisense.util.HeaderFooterPageEvent;
import com.sposnor.intellisense.sponsorintellisense.util.VelocityTemplateParser;


@RestController
@RequestMapping("/api/substitute")
public class SubstitutionController {

	private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);
	
	@Autowired
	private SponsorMapper sponsorMapper;
	
	@Autowired
	private EnrollmentMapper enrollmentMapper;
	
	@Autowired
	S3Wrapper s3Wrapper;
	
	@Autowired
	private StudentMapper studentMapper;
	
	@GetMapping("/list")
	public ResponseEntity<List<Substitution>> getSequenceByParishId() {
		List<Substitution> substitutions = sponsorMapper.getSubstitutions();	
		return ResponseEntity.ok().body(substitutions);
	}
	
	@RequestMapping(value = "/generatereport/{enrollmentId}/{oldStudentId}/{newStudentId}", method = RequestMethod.GET, produces = "application/pdf")
	ResponseEntity<byte[]> generatePdf(@PathVariable(value = "enrollmentId") Long enrollmentId,
			@PathVariable(value = "oldStudentId") Long oldStudentId,
			@PathVariable(value = "newStudentId") Long newStudentId) throws Exception {

		List<Sponsee> noOfKids = enrollmentMapper.findSponseesByEnrollmentId(enrollmentId);
		
		Map<String,List<Student>> substitutions= new HashMap<String,List<Student>>();
		List<Student> students = new ArrayList<Student>();
		Student oldStudent = studentMapper.findByIdAndEnrollmentId(oldStudentId, enrollmentId);
		Student newStudent = studentMapper.findByIdAndEnrollmentId(newStudentId, enrollmentId);
		students.add(newStudent);
		students.add(oldStudent);	
		
		for(Student student : students) {
			if("Y".equalsIgnoreCase(student.getUploadstatus()))
				student.setProfilePicture(s3Wrapper.downloadProfilePicture(String.valueOf(student.getProjectId()), 
						String.valueOf(student.getId()), student.getImageLinkRef()));
		}
		substitutions.put("1", students);
		SponsorReport sponsorReport = sponsorMapper.findSponsorByEnrolmentId(enrollmentId);

		String coverLetter = VelocityTemplateParser.generateSubstitutionCoverLetter(sponsorReport, noOfKids.size());
	
		String htmlstring = VelocityTemplateParser.generateHTML(getDataMap(sponsorReport, substitutions), noOfKids.size(), "substitution");
		String consolidatedData = (coverLetter + htmlstring);//.replaceAll("&", "&amp;");
		 //System.out.println(" consolidatedData "+consolidatedData);

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
	
	private Map<String, Object> getDataMap(SponsorReport sponser, Map<String,List<Student>> substitutions) {
		SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd yyyy");
		Date date = new Date();
		String time = sdf.format(date);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("timeNow", time);
		map.put("sponsorId", sponser.getUniqueId());
		map.put("sponsorName", sponser.getSponsorName());
		map.put("parishName", sponser.getParishName());
		map.put("parishCity", sponser.getParishCity());
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
		//map.put("sponseeList", substitutions.);
		map.put("substitutions", substitutions);
		map.put("totalChildrenSposored", substitutions.get("1").size());
		
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


		return map;
	} 
}
