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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sposnor.intellisense.sponsorintellisense.data.model.Enrollment;
import com.sposnor.intellisense.sponsorintellisense.data.model.Sponsee;
import com.sposnor.intellisense.sponsorintellisense.data.model.SponsorMaxOut;
import com.sposnor.intellisense.sponsorintellisense.data.model.StudentMaxOut;
import com.sposnor.intellisense.sponsorintellisense.data.model.StudentSummary;
import com.sposnor.intellisense.sponsorintellisense.mapper.EnrollmentMapper;
import com.sposnor.intellisense.sponsorintellisense.mapper.MaxOutMapper;
import com.sposnor.intellisense.sponsorintellisense.mapper.StudentMapper;

@RestController
@RequestMapping("/api")
public class EnrollmentController {

	@Autowired
	private EnrollmentMapper enrollmentMapper;
	
	@Autowired
	private MaxOutMapper maxOutMapper;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EnrollmentController.class);
	
	
	@PostMapping("/release")
	public ResponseEntity<String> release(@RequestHeader(value="userId") int userId, @RequestBody Enrollment enrollment){
		enrollmentMapper.updateEnrollment(enrollment.getEnrollmentId(), userId);
		enrollmentMapper.updateSponsee(enrollment.getEnrollmentId());
		enrollmentMapper.updateSponsorMaxout(enrollment.getEnrollmentId());
		enrollmentMapper.updateStudentMaxout(enrollment.getEnrollmentId());
		return ResponseEntity.ok().body("Success");
	}
	
	@PostMapping("/enroll")
	public ResponseEntity<String> createStudent(@RequestHeader(value="userId") int userId, @RequestBody Enrollment enrollment) {
		enrollment.setCreatedBy(userId);
		LOGGER.debug("Enrollment Id is " + enrollment.getEnrollmentId());
		if(enrollment.getEnrollmentId() == 0) {
			LOGGER.debug("Creating new enrollment");
			enrollment.setNetAmount(enrollment.getContributionAmount());
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
		LOGGER.debug("Total number of sponsors adding to the enrollment, "+ enrollment.getSponsees()); 
		for(Sponsee sponsee:  enrollment.getSponsees()) {
			c = Calendar.getInstance();
			sponsee.setEnrollmentId(enrollment.getId());
			enrollmentMapper.insertSponsee(sponsee);
			c.set(sponsee.getExpirationYear(), sponsee.getExpirationMonth() - 1, 1, 0, 0);  
			dates.add(c.getTime());
			maxOutMapper.insertStudentMaxOut(new StudentMaxOut(sponsee.getStudentId(), enrollment.getId(), c.getTime()));
		}
		Date maxDate = dates.stream().map(date -> date).max(Date::compareTo).get();
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
		setNetAmountForActiveEnrollment(enrollment, dbEnrollment);
		System.out.println(" After new net amount > "+enrollment.getNetAmount());
		createNewEnrollment(enrollment);
		
	}
	
	private void setNetAmountForActiveEnrollment(Enrollment newEnrollment, Enrollment existingEnrollment) {
		System.out.println(newEnrollment.getContributionAmount() + " < actual : existing net > "+existingEnrollment.getNetAmount());
		newEnrollment.setNetAmount(newEnrollment.getContributionAmount() + existingEnrollment.getNetAmount());
		/*if(existingEnrollment.getNetAmount() > 0) {
			newEnrollment.setNetAmount(newEnrollment.getActualamount()+existingEnrollment.getNetAmount());
		} else if(existingEnrollment.getActualamount() > 0) {
			newEnrollment.setNetAmount(newEnrollment.getActualamount()+existingEnrollment.getActualamount());
		} else {
			newEnrollment.setNetAmount(newEnrollment.getActualamount()+existingEnrollment.getContributionAmount()+existingEnrollment.getMiscAmount());
		} */
	}
		
	/**
	 * 
	 * @param enrollment
	 * @param dbSponsees is enrollmentMapper.findSponseesByEnrollmentId(enrollmentId)
	 * @param dbStudentMaxOuts is maxOutMapper.findStudentMaxOut(studentIds, enrollmentId) 
	 */
	private void modifyEnrollment(Enrollment enrollment, List<Sponsee> dbSponsees, List<StudentMaxOut> dbStudentMaxOuts) {
		
		Set<Sponsee> payloadSponsees = enrollment.getSponsees();
		Set<Sponsee> sponseesTodb = new HashSet<Sponsee>();
		//List<StudentMaxOut> studentMaxoutTodb = new ArrayList<StudentMaxOut>();
		//List<SponsorMaxOut> sponsorMaxoutTodb = new ArrayList<SponsorMaxOut>();
		LOGGER.debug("Database Sponsees Size "+ dbSponsees.size());
		sponseesTodb.addAll(payloadSponsees); // get all enrollment students to the list
		LOGGER.debug("SponseesTodb size after adding from the payload enrollment "+ payloadSponsees.size());
		//Calendar c = Calendar.getInstance();
		
		List<Long> payloadStudentIds = payloadSponsees.stream().map(sponsee -> sponsee.getStudentId()).collect(Collectors.toList());
		for(Sponsee dbSponsee: dbSponsees) {
			if(!payloadStudentIds.contains(dbSponsee.getStudentId())) { // check the incoming student matches the db students
				sponseesTodb.add(dbSponsee);  // add the db students to the list in case they are not present in the incoming list
			}
		}
		/*
		for(Sponsee enSponsee: payloadSponsees) {
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
			
		} */
		LOGGER.debug("Enrollment sponsees size before adding the sponseesTodb "+ payloadSponsees.size());
		enrollment.setSponsees(sponseesTodb);
		LOGGER.debug("Enrollment sponsees size after adding the sponseesTodb "+ enrollment.getSponsees().size());
	}
	
	class EnrollmentPayload{
		private Long enrollmentId;

		public Long getEnrollmentId() {
			return enrollmentId;
		}

		public void setEnrollmentId(Long enrollmentId) {
			this.enrollmentId = enrollmentId;
		}
		
	}
	
}
