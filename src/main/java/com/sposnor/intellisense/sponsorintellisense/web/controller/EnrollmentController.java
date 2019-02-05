package com.sposnor.intellisense.sponsorintellisense.web.controller;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sposnor.intellisense.sponsorintellisense.data.model.Enrollment;
import com.sposnor.intellisense.sponsorintellisense.data.model.Parish;
import com.sposnor.intellisense.sponsorintellisense.data.model.Receipt;
import com.sposnor.intellisense.sponsorintellisense.data.model.Sponsee;
import com.sposnor.intellisense.sponsorintellisense.data.model.Sponsor;
import com.sposnor.intellisense.sponsorintellisense.data.model.SponsorMaxOut;
import com.sposnor.intellisense.sponsorintellisense.data.model.StudentMaxOut;
import com.sposnor.intellisense.sponsorintellisense.mapper.EnrollmentMapper;
import com.sposnor.intellisense.sponsorintellisense.mapper.MaxOutMapper;
import com.sposnor.intellisense.sponsorintellisense.mapper.ParishMapper;
import com.sposnor.intellisense.sponsorintellisense.mapper.ReceiptMapper;
import com.sposnor.intellisense.sponsorintellisense.mapper.SponsorMapper;

@RestController
@RequestMapping("/api")
public class EnrollmentController {

	@Autowired
	private EnrollmentMapper enrollmentMapper;
	
	@Autowired
	private ParishMapper parishMapper;
	
	@Autowired
	private ReceiptMapper receiptMapper;
	
	@Autowired
	private SponsorMapper sponsorMapper;
	
	@Autowired
	private MaxOutMapper maxOutMapper;
	
	@PostMapping("/enroll")
	public ResponseEntity<String> createStudent(@RequestHeader(value="userId") Long userId, @RequestBody Enrollment enrollment) {
		enrollment.setCreatedBy(userId);
		Sponsor sponsor = sponsorMapper.findById(enrollment.getSponsorId());
		Parish parish = parishMapper.findById(sponsor.getParishId());
		/*Receipt receipt = new Receipt();
		if(null!=sponsor.getAppartmentNumber()) {
			receipt.setAddress(sponsor.getAppartmentNumber()+" "+sponsor.getStreet()+" "+sponsor.getCity()+ " "+ sponsor.getState()+ " "+ sponsor.getPostalCode());
		}else {
			receipt.setAddress(sponsor.getStreet()+" "+sponsor.getCity()+ " "+ sponsor.getState()+ " "+ sponsor.getPostalCode());
		}		
		receipt.setCreatedby(2L);
		receipt.setMissionname("Light To Life");
		receipt.setParish(parish.getName() + "-"+ parish.getCity());
		receipt.setPaymentmethod("Online");
		if(sponsor.isHasAnyCoSponser()) {
			receipt.setReceivedfrom(sponsor.getFirstName()+ " & "+ sponsor.getCoSponserName());
		}else {
			receipt.setReceivedfrom(sponsor.getFirstName()+ " "+ sponsor.getLastName());
		}
		
		receipt.setTotal(String.valueOf(enrollment.getContributionAmount()));
		receipt.setCreatedby(userId);
		receiptMapper.insert(receipt);
		
		enrollment.setReceiptId(receipt.getId()); */
		double contribution = enrollment.getContributionAmount();
		enrollment.setActualamount(contribution);
		enrollment.setContributionAmount(contribution-enrollment.getMiscAmount());
		enrollmentMapper.insert(enrollment);
		
		Calendar c = Calendar.getInstance();
		//Enrollment e = enrollmentMapper.selectEnrollmentForId(enrollment.getSponsorId(), enrollment.getEffectiveDate(), enrollment.getEffectiveDate());
		for(Sponsee sponsee:  enrollment.getSponsees()) {
			sponsee.setEnrollmentId(enrollment.getId());
			enrollmentMapper.insertSponsee(sponsee);
			c.set(sponsee.getExpirationYear(), sponsee.getExpirationMonth() - 1, 1, 0, 0);  
			//dateSet.add(c.getTime());	
			maxOutMapper.insertStudentMaxOut(new StudentMaxOut(sponsee.getStudentId(), enrollment.getId(), c.getTime()));
			maxOutMapper.insertSponsorMaxOut(new SponsorMaxOut(enrollment.getSponsorId(), enrollment.getId(), c.getTime()));
		}
		
		/*final Date maxDate = dateSet.stream()
		            .max(Date::compareTo)
		            .get();		*/
		
	    return ResponseEntity.ok().body("Success");
	}
	
}
