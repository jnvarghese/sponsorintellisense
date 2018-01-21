package com.sposnor.intellisense.sponsorintellisense.web.controller;

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

import com.sposnor.intellisense.sponsorintellisense.data.model.Student;
import com.sposnor.intellisense.sponsorintellisense.mapper.StudentMapper;

@RestController
@RequestMapping("/api/student")
public class StudentController {

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
	
	@GetMapping("/search/{name}")
	public ResponseEntity<List<Student>> getStudentByName(@PathVariable(value = "name") String name) {
		List<Student> sponsors = studentMapper.searchByName(name+"%");
	    if(sponsors == null) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok().body(sponsors);
	}
	
}
