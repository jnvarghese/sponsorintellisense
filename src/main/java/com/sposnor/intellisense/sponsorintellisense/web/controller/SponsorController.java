package com.sposnor.intellisense.sponsorintellisense.web.controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sposnor.intellisense.sponsorintellisense.data.model.MaxOutOverview;
import com.sposnor.intellisense.sponsorintellisense.data.model.Parish;
import com.sposnor.intellisense.sponsorintellisense.data.model.Sequence;
import com.sposnor.intellisense.sponsorintellisense.data.model.Sponsor;
import com.sposnor.intellisense.sponsorintellisense.data.model.SponsorSearch;
import com.sposnor.intellisense.sponsorintellisense.data.model.StudentSummary;
import com.sposnor.intellisense.sponsorintellisense.mapper.DashboardMapper;
import com.sposnor.intellisense.sponsorintellisense.mapper.ParishMapper;
import com.sposnor.intellisense.sponsorintellisense.mapper.SponsorMapper;
import com.sposnor.intellisense.sponsorintellisense.mapper.StudentMapper;

@RestController
@RequestMapping("/api/sponsor")
public class SponsorController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SponsorController.class);
	
	@Autowired
	private SponsorMapper sponsorMapper;
	
	@Autowired
	private ParishMapper parishMapper;
	
	@Autowired
	DashboardMapper dashboardMapper;
	
	@Autowired
	private StudentMapper studentMapper;
	
	@GetMapping("/list")
	public List<Sponsor> getAllSponsors() {
		return sponsorMapper.list();
	}
	
	
	
	@GetMapping("/listByDemography/{firstName}/{lastName}/{street}/{city}/{state}/{zipcode}")
	public List<Sponsor> listByDemography(
			@PathVariable(value = "firstName") String firstName, 
			@PathVariable(value = "lastName") String lastName,
			@PathVariable(value = "street") String street,
			@PathVariable(value = "city") String city,
			@PathVariable(value = "state") String state,
			@PathVariable(value = "zipcode") String zipcode
			) {
		return sponsorMapper.listByDemography(firstName.replace("1", ""), lastName.replace("1", ""), 
				street.replace("1", ""), city.replace("1", ""), state.replace("1", ""), zipcode.replace("Z", ""));
	}
	
	@GetMapping("/list/{firstName}/{lastName}/{id}")
	public List<Sponsor> getAllSponsorsByNameAndParishId(@PathVariable(value = "firstName") String firstName, 
			@PathVariable(value = "lastName") String lastName,
			@PathVariable(value= "id") Long parishId) {
		return sponsorMapper.listByMatchingFirstNameAndLastNameAndParishId(firstName, lastName, parishId);
	}

	@PostMapping("/searchsponsor")
	public ResponseEntity<List<Sponsor>> search(@RequestHeader Long userId, @RequestBody SponsorSearch search) {	
	     if("1".equalsIgnoreCase(search.getCity())) {
	    	 List<Sponsor> sponsors = sponsorMapper.searchExternalSponsor(search.getZipCode(), search.getSponsorCode(), 
					 search.getFirstName(), search.getLastName());
			 return new ResponseEntity<List<Sponsor>>(sponsors, HttpStatus.OK);
		 }
		 List<Parish> parishes = parishMapper.searchByCity(search.getCity());
		 Long[] ids = parishes.stream().map(parish -> parish.getId()).toArray(Long[]::new);
         List<Sponsor> sponsors = sponsorMapper.searchSponsor(ids, search.getZipCode(), search.getSponsorCode(), 
				 search.getFirstName(), search.getLastName());
		 return new ResponseEntity<List<Sponsor>>(sponsors, HttpStatus.OK);
	}
	
	@GetMapping("/receipts/{id}")
	public List<Sponsor> getSponsorsReceiptsByParishId(@PathVariable(value = "id") Long receiptId) {
		return sponsorMapper.getSponsorReceiptsByParish(receiptId);
	}
	
	@GetMapping("/activelist/{id}")
	public List<Sponsor> getAllActiveSponsorsByParishId(@PathVariable(value = "id") Long parishId) {
		List<Sponsor> sponsors = sponsorMapper.listByParishId(parishId);
		return sponsors;
	}
	/*
	@GetMapping("/list/activesponsors/enrollments/parish/{id}")
	public List<Sponsor> getAllActiveAndEnrolledSponsorsByParishId(@PathVariable(value = "id") Long parishId) {
		
		List<Sponsor> allActiveSponsonrs = sponsorMapper.listByParishId(parishId);
		List<Sponsor> sponsors = sponsorMapper.listSponsorsByParishId(parishId);
		List<Sponsor> amountsums = sponsorMapper.getSumOfSponsorAmountByParish(parishId);

		//if(null != amountsums) {
			// pair each id with its marks
			Map<Long, Double> amountsumsMap = amountsums.stream().filter(x -> x!=null).collect(Collectors.toMap(Sponsor::getId, Sponsor::getAmount));
			// go through list of `ObjectOne`s and lookup marks in the index
			sponsors.forEach(o1 -> {
				o1.setAmount(amountsumsMap.containsKey(o1.getId()) ? amountsumsMap.get(o1.getId()) : 0.00);
				}
			);
		//}

	        
	        List<Sponsor> listTwoCopy = new ArrayList<>(allActiveSponsonrs);
	        listTwoCopy.removeAll(sponsors);
	        sponsors.addAll(listTwoCopy);
	         
	        System.out.println(sponsors.size());
	        
		return sponsors;
	}
	*/
	
	@GetMapping("/listbyparish/{id}")
	public List<Sponsor> getAllSponsorsByParishId(@PathVariable(value = "id") Long parishId) {
		
		List<Sponsor> sponsors = sponsorMapper.listSponsorsByParishId(parishId);
		List<Sponsor> amountsums = sponsorMapper.getSumOfSponsorAmountByParish(parishId);

		//if(null != amountsums) {
			// pair each id with its marks
			Map<Long, Double> amountsumsMap = amountsums.stream().filter(x -> x!=null).collect(Collectors.toMap(Sponsor::getId, Sponsor::getAmount));
			// go through list of `ObjectOne`s and lookup marks in the index
			sponsors.forEach(o1 -> {
				o1.setAmount(amountsumsMap.containsKey(o1.getId()) ? amountsumsMap.get(o1.getId()) : 0.00);
				}
			);
		//}
		return sponsors;
	}
	
	@PostMapping("/add")
	public ResponseEntity<Sponsor> createSponsor(@RequestHeader Long userId, @Valid @RequestBody Sponsor sponsor) {	
		List<Sponsor> sponsors = sponsorMapper.searchByCode(sponsor.getSponsorCode(), sponsor.getParishId());
		if(!sponsors.isEmpty()) {
			LOGGER.warn("Sponsor code already existing, "+ sponsor.getSponsorCode());
			return new ResponseEntity<Sponsor>(sponsor, HttpStatus.BAD_REQUEST);
		}
		sponsor.setCreatedBy(userId);
		if(!StringUtils.isEmpty(sponsor.getCoSponserName())) {
			sponsor.setHasAnyCoSponser(true);
		}
		sponsorMapper.insert(sponsor);	
	    return new ResponseEntity<Sponsor>(sponsor, HttpStatus.OK);
	}
	
	@GetMapping("/find/{id}")
	public ResponseEntity<Sponsor> getSponsorById(@PathVariable(value = "id") Long sponsorId) {
		Sponsor sponsor = sponsorMapper.findById(sponsorId);
	    if(sponsor == null) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok().body(sponsor);
	}
	
	@GetMapping("/find/{parishId}/{sponsorCode}")
	public ResponseEntity<Sponsor> getSponsorByParishIdAndSponsorCode(@PathVariable(value = "parishId") Long parishId, @PathVariable(value = "sponsorCode") String sponsorCode) {
		Sponsor sponsor = sponsorMapper.getSponsorByParishIdAndSponsorCode(parishId, sponsorCode);
	    if(sponsor == null) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok().body(sponsor);
	}
	
	@Deprecated
	@GetMapping("/findByCode/{sponsorCode}")
	public ResponseEntity<List<Sponsor>> getSponsorBySponsorCode(@PathVariable(value = "sponsorCode") String sponsorCode) {
		List<Sponsor> sponsors = sponsorMapper.getSponsorBySponsorCode(sponsorCode);
	    if(sponsors == null) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok().body(sponsors);
	}
	
	
	@GetMapping("/sequence/{id}")
	public ResponseEntity<Sequence> getSequenceByParishId(@PathVariable(value = "id") Long parishId) {
		Sequence sequence = sponsorMapper.getSequenceByParishId(parishId);	    
	    return ResponseEntity.ok().body(sequence);
	}
	
	@PutMapping("/modify/{id}")
	public Sponsor updateSponsor(@RequestHeader Long userId, @PathVariable(value = "id") Long sponsorId, 
	                                       @Valid @RequestBody Sponsor sponsorToModify) {
		sponsorToModify.setUpdatedBy(userId);
		LOGGER.debug(" Dob {}", sponsorToModify.getDayOfBirth());
		if(StringUtils.isEmpty(sponsorToModify.getDayOfBirth())) {
			sponsorToModify.setDayOfBirth("1");
		}
		if(StringUtils.isEmpty(sponsorToModify.getMonthOfBirth())) {
			sponsorToModify.setMonthOfBirth("1");
		}
		if(!StringUtils.isEmpty(sponsorToModify.getCoSponserName())) {
			sponsorToModify.setHasAnyCoSponser(true);
		}else {
			sponsorToModify.setHasAnyCoSponser(false);
		}
		sponsorMapper.update(sponsorToModify);	    
	    return sponsorToModify;
	}

	@GetMapping("/search/{name}")
	public ResponseEntity<List<Sponsor>> getSponsorByName(@PathVariable(value = "name") String name) {
		List<Sponsor> sponsors = sponsorMapper.searchByName(name+"%");
	    if(sponsors == null) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok().body(sponsors);
	}
	
	@GetMapping("/search/{name}/parish/{id}")
	public ResponseEntity<List<Sponsor>> getSponsorByNameAndParishId(@PathVariable(value = "name") String name,
			@PathVariable(value = "id") Long parishId) {
		List<Sponsor> sponsors = sponsorMapper.searchByNameAndParishId(name+"%", parishId);
	    if(sponsors == null) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok().body(sponsors);
	}
	
	@GetMapping("/maxout/{fromDate}/{toDate}")
	public ResponseEntity<List<MaxOutOverview>> getMaxOutSponsors(
			@PathVariable(value = "fromDate") String fromDate, @PathVariable(value = "toDate") String toDate) {
		 return ResponseEntity.ok().body(dashboardMapper.getMaxedOut());
	}
	
	@GetMapping("/{id}/students")
	public ResponseEntity<List<StudentSummary>> enrollments(@RequestHeader(value="userId") int userId, @PathVariable(value = "id") Long sponsorId) {
	 return ResponseEntity.ok().body(studentMapper.summaryBySponsorId(sponsorId));
	}
}
