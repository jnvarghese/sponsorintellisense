package com.sposnor.intellisense.sponsorintellisense.web.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public ResponseEntity<String> createStudent(@RequestBody Student note) {
		studentMapper.insert(note);	    
	    return ResponseEntity.ok().body("Success");
	}
	
	@PostMapping("/image")
	public ResponseEntity<String> uploadImage(@RequestBody MultipartFile multipartFile) throws IOException {
		  String name = multipartFile.getOriginalFilename();
	      System.out.println("File name: "+name);
	      //todo save to a file via multipartFile.getInputStream()
	      byte[] bytes = multipartFile.getBytes();
	      System.out.println("File uploaded content:\n" + new String(bytes));
	     	
	    return ResponseEntity.ok().body("Success");
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
	public ResponseEntity<String> updateStudent(@PathVariable(value = "id") Long studentId, 
			@Valid @RequestBody Student studentToModify) {
		
		studentMapper.update(studentToModify);
	    
	    return ResponseEntity.ok("Success");
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
	
}
