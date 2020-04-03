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

import com.sposnor.intellisense.sponsorintellisense.data.model.Parish;
import com.sposnor.intellisense.sponsorintellisense.data.model.Sequence;
import com.sposnor.intellisense.sponsorintellisense.data.model.Sponsor;
import com.sposnor.intellisense.sponsorintellisense.data.model.SponsorReceipts;
import com.sposnor.intellisense.sponsorintellisense.data.model.SponsorSearch;
import com.sposnor.intellisense.sponsorintellisense.mapper.ParishMapper;
import com.sposnor.intellisense.sponsorintellisense.mapper.SponsorMapper;

@RestController
@RequestMapping("/api/sponsor")
public class SponsorController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SponsorController.class);
	
	@Autowired
	private SponsorMapper sponsorMapper;
	
	@Autowired
	private ParishMapper parishMapper;
	
	@GetMapping("/list")
	public List<Sponsor> getAllSponsors() {
		return sponsorMapper.list();
	}
	
	@GetMapping("/list/{firstName}/{lastName}/{id}")
	public List<Sponsor> getAllSponsors(@PathVariable(value = "firstName") String firstName, 
			@PathVariable(value = "lastName") String lastName,
			@PathVariable(value= "id") Long parishId) {
		return sponsorMapper.list2(firstName, lastName, parishId);
	}
	
	@PostMapping("/searchsponsor")
	public ResponseEntity<List<Sponsor>> search(@RequestHeader Long userId, @RequestBody SponsorSearch search) {	
		 List<Parish> parishes = parishMapper.searchByCity(search.getCity());
		 Long[] ids = parishes.stream().map(parish -> parish.getId()).toArray(Long[]::new);
         List<Sponsor> sponsors = sponsorMapper.searchSponsor(ids, search.getZipCode(), search.getSponsorCode(), 
				 search.getFirstName(), search.getLastName());
		 return new ResponseEntity<List<Sponsor>>(sponsors, HttpStatus.OK);
	}
	
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
	
}
