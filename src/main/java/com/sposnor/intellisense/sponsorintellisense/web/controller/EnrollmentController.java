package com.sposnor.intellisense.sponsorintellisense.web.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sposnor.intellisense.sponsorintellisense.data.model.Enrollment;
import com.sposnor.intellisense.sponsorintellisense.data.model.Sponsee;
import com.sposnor.intellisense.sponsorintellisense.data.model.SponsorMaxOut;
import com.sposnor.intellisense.sponsorintellisense.data.model.StudentMaxOut;
import com.sposnor.intellisense.sponsorintellisense.mapper.EnrollmentMapper;
import com.sposnor.intellisense.sponsorintellisense.mapper.MaxOutMapper;

@RestController
@RequestMapping("/api")
public class EnrollmentController {

	@Autowired
	private EnrollmentMapper enrollmentMapper;
	
	@Autowired
	private MaxOutMapper maxOutMapper;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EnrollmentController.class);
	
	@PostMapping("/enroll")
	public ResponseEntity<String> createStudent(@RequestHeader(value="userId") int userId, @RequestBody Enrollment enrollment) {
		enrollment.setCreatedBy(userId);
		if(enrollment.getEnrollmentId() == 0) {
			LOGGER.debug("Creating new enrollment");
			createNewEnrollment(enrollment);
		}else {
			LOGGER.debug("Updating new enrollment");
			duplicateEnrollment(enrollment, userId);
		}

	    return ResponseEntity.ok().body("Success");
	}
	DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	private void createNewEnrollment(Enrollment enrollment) {
		double contribution = enrollment.getContributionAmount();
		enrollment.setActualamount(contribution);
		enrollment.setContributionAmount(contribution-enrollment.getMiscAmount());
		enrollmentMapper.insert(enrollment);
		List<Date> dates = new ArrayList<Date>();
		Calendar c = null;
		for(Sponsee sponsee:  enrollment.getSponsees()) {
			c = Calendar.getInstance();
			sponsee.setEnrollmentId(enrollment.getId());
			enrollmentMapper.insertSponsee(sponsee);
			System.out.println(" before " + dateFormat.format(c.getTime())); 
			c.set(sponsee.getExpirationYear(), sponsee.getExpirationMonth() - 1, 1, 0, 0);  
			System.out.println(" after " + dateFormat.format(c.getTime())); 
			dates.add(c.getTime());
			maxOutMapper.insertStudentMaxOut(new StudentMaxOut(sponsee.getStudentId(), enrollment.getId(), c.getTime()));
		}
		Date maxDate = dates.stream().map(date -> date).max(Date::compareTo).get();
		System.out.println(" max  " + dateFormat.format(maxDate.getTime())); 
		maxOutMapper.insertSponsorMaxOut(new SponsorMaxOut(enrollment.getSponsorId(), enrollment.getId(), maxDate));
	}
	
	private void disableEnrollment(Enrollment en, List<Sponsee> sponsees, List<SponsorMaxOut> sponsorMaxOuts, List<StudentMaxOut> studentMaxOuts, int userId) {
		

		List<Long> sponseeIds = sponsees.stream().map(sponsee -> sponsee.getId()).collect(Collectors.toList());
		List<Integer> spMaxOutIds = sponsorMaxOuts.stream().map(sp -> sp.getId()).collect(Collectors.toList());
		List<Integer> stMaxOutIds = studentMaxOuts.stream().map(st -> st.getId()).collect(Collectors.toList());

		enrollmentMapper.updateEnrollmentStatus(en.getId(), userId);
		if(!CollectionUtils.isEmpty(sponseeIds))
			enrollmentMapper.updateSponseeStatus(sponseeIds);
		if(!CollectionUtils.isEmpty(spMaxOutIds))
			maxOutMapper.updateSponsorMaxOutStatus(spMaxOutIds);
		if(!CollectionUtils.isEmpty(stMaxOutIds))
			maxOutMapper.updateStudentMaxOutStatus(stMaxOutIds);
	}
	
	private void duplicateEnrollment(Enrollment enrollment, int userId) {
		Long enrollmentId = enrollment.getEnrollmentId();
		Long sponsorId = enrollment.getSponsorId();
		Enrollment dbEnrollment =  enrollmentMapper.findEnrollment(enrollmentId);
		List<Sponsee> sponsees = enrollmentMapper.findSponseesByEnrollmentId(enrollmentId);
		List<SponsorMaxOut> sponsorMaxOuts = maxOutMapper.findSponsorMaxOut(sponsorId, enrollmentId);
		
		List<Long> studentIds = sponsees.stream().map(sponsee -> sponsee.getStudentId()).collect(Collectors.toList());
		List<StudentMaxOut> studentMaxOuts = maxOutMapper.findStudentMaxOut(studentIds, enrollmentId);
		
		disableEnrollment(dbEnrollment, sponsees, sponsorMaxOuts, studentMaxOuts, userId); // set the status to 1 for enr, sponsee and maxouts
		modifyEnrollment(enrollment, sponsees, studentMaxOuts);  // build up new enrollment entity with exiting and new/ modified students
		
		createNewEnrollment(enrollment);
		
	}
	
	private void modifyEnrollment(Enrollment enrollment, List<Sponsee> dbSponsees, List<StudentMaxOut> dbStudentMaxOuts) {
		
		Set<Sponsee> sponseesTodb = new HashSet<Sponsee>();
		List<StudentMaxOut> studentMaxoutTodb = new ArrayList<StudentMaxOut>();
		List<SponsorMaxOut> sponsorMaxoutTodb = new ArrayList<SponsorMaxOut>();
		
		sponseesTodb.addAll(enrollment.getSponsees()); // get all enrollment students to the list
		Calendar c = Calendar.getInstance();
		
		for(Sponsee enSponsee: enrollment.getSponsees()) {
			c.set(enSponsee.getExpirationYear(), enSponsee.getExpirationMonth() - 1, 1, 0, 0); 
			for(Sponsee dbSponsee: dbSponsees) {
				if(!dbSponsee.getStudentId().equals(enSponsee.getStudentId())) { // check the incoming student matches the db students
					sponseesTodb.add(dbSponsee);  // add the db students to the list in case they are not present in the incoming list
				}
			}
			studentMaxoutTodb.add(new StudentMaxOut(enSponsee.getStudentId(), enrollment.getId(), c.getTime()));
			
			for(StudentMaxOut dbStudentMaxOut: dbStudentMaxOuts) {
				if(!dbStudentMaxOut.getStudentId().equals(enSponsee.getStudentId())) {
					sponsorMaxoutTodb.add(new SponsorMaxOut(enrollment.getSponsorId(), enrollment.getId(), dbStudentMaxOut.getMaxOut()));
				}
			}
			sponsorMaxoutTodb.add(new SponsorMaxOut(enrollment.getSponsorId(), enrollment.getId(), c.getTime()));
		}
		enrollment.setSponsees(sponseesTodb);
	}
	
}
