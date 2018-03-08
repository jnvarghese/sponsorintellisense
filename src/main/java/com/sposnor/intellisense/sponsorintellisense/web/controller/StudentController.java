package com.sposnor.intellisense.sponsorintellisense.web.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sposnor.intellisense.sponsorintellisense.data.model.Student;
import com.sposnor.intellisense.sponsorintellisense.mapper.StudentMapper;

@RestController
@RequestMapping("/api/student")
public class StudentController {
	
    SimpleDateFormat MYSQL_DT_FORMAT = new SimpleDateFormat("yyyyy-MM-dd"); 

	@Autowired
	private StudentMapper studentMapper;
	
	@GetMapping("/list")
	public List<Student> getAllStudents() {
		return studentMapper.list();
	}
	
	@PostMapping("/add")
	public Student createStudent(@RequestBody Student note) {
		studentMapper.insert(note);	    
	    return note;
	}
	
	@PostMapping("/image/{id}")
	public ResponseEntity<String> uploadImage(
			@RequestParam("file") MultipartFile multipartFile,
			@PathVariable(value = "id") Long studentId) throws IOException {
			String message = "";
			String name = null ;
			Student student = new Student();
			try {
				name = multipartFile.getOriginalFilename();
				System.out.println("File name: "+multipartFile);
				student.setProfilePicture(multipartFile.getBytes());
				student.setId(studentId);
				studentMapper.uploadImage(student);
				message = "You successfully uploaded " + name + "!";
				return ResponseEntity.status(HttpStatus.OK).body(message);
			} catch (Exception e) {
				message = "FAIL to upload " + name + "!";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
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
	public Student updateStudent(@PathVariable(value = "id") Long studentId, 
			@Valid @RequestBody Student studentToModify) {		
		studentMapper.update(studentToModify);	    
	    return studentToModify;
	}
	
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
		List<Student> sponsors = studentMapper.findStudentsBySponsorshipStatus(name+"%", projectId, MYSQL_DT_FORMAT.format(c.getTime()));
	    if(sponsors == null) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok().body(sponsors);
	}
	
	@GetMapping("/search/{name}")
	public List<Student> getStudentsByName(
			@PathVariable(value = "name") String name){
		return studentMapper.listMatchingStudentsByName(name+"%");
	}
	
	
}
