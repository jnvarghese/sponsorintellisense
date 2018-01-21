package com.sposnor.intellisense.sponsorintellisense.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sposnor.intellisense.sponsorintellisense.data.model.Enrollment;
import com.sposnor.intellisense.sponsorintellisense.data.model.Student;
import com.sposnor.intellisense.sponsorintellisense.mapper.EnrollmentMapper;

@RestController
@RequestMapping("/api/enroll")
public class EnrollmentController {

	@Autowired
	private EnrollmentMapper enrollmentMapper;
	
	@PostMapping("/enroll")
	public ResponseEntity<String> createStudent(@RequestBody Enrollment enrollment) {
		enrollmentMapper.insert(enrollment);
		System.out.println( enrollment );
	    return ResponseEntity.ok().body("Success");
	}
	
}
