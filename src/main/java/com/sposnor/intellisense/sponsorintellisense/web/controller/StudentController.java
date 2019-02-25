package com.sposnor.intellisense.sponsorintellisense.web.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.model.PutObjectResult;
import com.sposnor.intellisense.sponsorintellisense.data.model.Sequence;
import com.sposnor.intellisense.sponsorintellisense.data.model.Student;
import com.sposnor.intellisense.sponsorintellisense.mapper.StudentMapper;
import com.sposnor.intellisense.sponsorintellisense.s3.S3Wrapper;

@RestController
@RequestMapping("/api/student")
public class StudentController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(StudentController.class);
	
    SimpleDateFormat MYSQL_DT_FORMAT = new SimpleDateFormat("yyyyy-MM-dd"); 

    @Autowired
	S3Wrapper s3Wrapper;
    
	@Autowired
	private StudentMapper studentMapper;
	
	@GetMapping("/list")
	public List<Student> getAllStudents() {
		return studentMapper.list();
	}
	
	@GetMapping("/sequence/{id}")
	public ResponseEntity<Sequence> getSequenceByParishId(@PathVariable(value = "id") Long projectId) {
		Sequence sequence = studentMapper.getSequenceByProjectId(projectId);	    
	    return ResponseEntity.ok().body(sequence);
	}
	
	@GetMapping("/list/byenrollmentid/{id}")
	public List<Student> getStudentsByEnrollmentId(@PathVariable(value = "id") Long enrollmentId) {
		return studentMapper.listByEnrollmentId(enrollmentId);
	}
	
	@GetMapping("/list/byproject/{id}")
	public List<Student> getAllStudentsByProjectId(@PathVariable(value = "id") Long projectId) {
		return studentMapper.listByProjectId(projectId);
	}
	
	@PostMapping("/add")
	public Student createStudent(@RequestHeader Long userId, @RequestBody Student note) {
		note.setCreatedBy(userId);
		studentMapper.insert(note);	    
	    return note;
	}	
	@PostMapping("/image/{id}")
	public ResponseEntity<String> uploadImage(
			@RequestParam(value = "userId", required=false, defaultValue = "default") String userId,
			@RequestParam("file") MultipartFile multipartFile,
			@PathVariable(value = "id") Long studentId) throws IOException {
			String message = "";
			String name = null ;
			Student student = studentMapper.findById(studentId);
			try {
				PutObjectResult result= s3Wrapper.upload(multipartFile.getInputStream(), 
						student.getId(), student.getImageLinkRef(), student.getProjectId(), userId);
				student.setUploadstatus("Y");
				studentMapper.updateUploadStatus(student);
				message = "You successfully uploaded " + name + "!";
				return ResponseEntity.status(HttpStatus.OK).body(message);
			} catch (Exception e) {
				e.printStackTrace();
				LOGGER.error(
						String.format("Error uploading profile picture for student id %d project id %d , uploaded by user %s",
								student.getId(),student.getProjectId(), userId), e);
				message = "FAIL to upload " + name + "!";
				
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(e.getMessage());
			}
	}

	@GetMapping("/find/{id}")
	public ResponseEntity<Student> getStudentById(@PathVariable(value = "id") Long studentId) {
		Student note = studentMapper.findById(studentId);
	    if(note == null) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok().body(note);
	}
	
	@PutMapping("/modify/{id}")
	public Student updateStudent(@RequestHeader Long userId,@PathVariable(value = "id") Long studentId, 
			@Valid @RequestBody Student studentToModify) {	
		studentToModify.setUpdatedBy(userId);
		studentMapper.update(studentToModify);	    
	    return studentToModify;
	}
	
	@Deprecated
	@GetMapping("/search/{name}/{projectId}/{month}/{date}/{year}")
	public ResponseEntity<List<Student>> getStudentByName(
			@PathVariable(value = "name") String name,
			@PathVariable(value = "projectId") Long projectId,
			@PathVariable(value = "month") int month,
			@PathVariable(value = "date") int date,
			@PathVariable(value = "year") int year
			) {
		Calendar c = Calendar.getInstance();
		c.set(year, month, 1, 0, 0);  
		System.out.println(" Effective Date in search "+ c.getTime());
		List<Student> students = studentMapper.findStudentsBySponsorshipStatus(name+"%", projectId, MYSQL_DT_FORMAT.format(c.getTime()));
	    for( Student student: students) {
	    	student.setGender(student.getGender().equals("M") ? "Male" : student.getGender().equals("F") ? "Female" : student.getGender().equals("O") ? "Other" : "");
	    }
		if(students == null) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok().body(students);
	}
	
	@GetMapping("/list/unenrolled/{parishId}/{projectId}")
	public ResponseEntity<List<Student>> getUnEnrolledStudents(
			@PathVariable(value = "parishId") Long parishId,
			@PathVariable(value = "projectId") Long projectId
			) {

		List<Student> students = studentMapper.findUnEnrolledStudents(parishId, projectId);
		if(students == null) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok().body(students);
	}
	
	@GetMapping("/search/{name}")
	public List<Student> getStudentsByName(
			@PathVariable(value = "name") String name){
		return studentMapper.listMatchingStudentsByName(name+"%");
	}
	
	
}
