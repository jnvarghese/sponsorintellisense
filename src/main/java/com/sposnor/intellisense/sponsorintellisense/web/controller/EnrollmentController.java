package com.sposnor.intellisense.sponsorintellisense.web.controller;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sposnor.intellisense.sponsorintellisense.data.model.Enrollment;
import com.sposnor.intellisense.sponsorintellisense.data.model.Sponsee;
import com.sposnor.intellisense.sponsorintellisense.data.model.Student;
import com.sposnor.intellisense.sponsorintellisense.mapper.EnrollmentMapper;
import com.sposnor.intellisense.sponsorintellisense.mapper.StudentMapper;

@RestController
@RequestMapping("/api/enroll")
public class EnrollmentController {

	@Autowired
	private EnrollmentMapper enrollmentMapper;
	
	@Autowired
	private StudentMapper studentMapper;
	
	@PostMapping("/enroll")
	public ResponseEntity<String> createStudent(@RequestBody Enrollment enrollment) {
		
		System.out.println( " Sponsee " + enrollment.getPaymentDate());
		enrollmentMapper.insert(enrollment);
		Calendar c = Calendar.getInstance();
		Enrollment e = enrollmentMapper.selectEnrollmentForId(enrollment.getSponsorId(), enrollment.getPaymentDate(), enrollment.getEffectiveDate());
		Student student = null;
		for(Sponsee sponsee:  enrollment.getSponsees()) {
			sponsee.setEnrollmentId(e.getId());
			enrollmentMapper.insertSponsee(sponsee);
			c.set(sponsee.getExpirationYear(), sponsee.getExpirationMonth() - 1, 1, 0, 0);  
			student = studentMapper.findById(sponsee.getStudentId());
			student.setValidUntil(c.getTime());
			studentMapper.update(student);
		}
	    return ResponseEntity.ok().body("Success");
	}
	
}
