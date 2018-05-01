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

import com.sposnor.intellisense.sponsorintellisense.data.model.Sponsor;
import com.sposnor.intellisense.sponsorintellisense.mapper.SponsorMapper;

@RestController
@RequestMapping("/api/sponsor")
public class SponsorController {
	
	@Autowired
	private SponsorMapper sponsorMapper;
	
	@GetMapping("/list")
	public List<Sponsor> getAllSponsors() {
		return sponsorMapper.list();
	}
	
	@GetMapping("/listbyparish/{id}")
	public List<Sponsor> getAllSponsorsByParishId(@PathVariable(value = "id") Long parishId) {
		return sponsorMapper.listSponsorsByParishId(parishId);
	}
	
	@PostMapping("/add")
	public Sponsor createSponsor(@Valid @RequestBody Sponsor sponsor) {		
		sponsorMapper.insert(sponsor);	    
	    return sponsor;
	}
	
	@GetMapping("/find/{id}")
	public ResponseEntity<Sponsor> getSponsorById(@PathVariable(value = "id") Long sponsorId) {
		Sponsor sponsor = sponsorMapper.findById(sponsorId);
	    if(sponsor == null) {
	        return ResponseEntity.notFound().build();
	    }
	    return ResponseEntity.ok().body(sponsor);
	}
	
	@PutMapping("/modify/{id}")
	public Sponsor updateSponsor(@PathVariable(value = "id") Long sponsorId, 
	                                       @Valid @RequestBody Sponsor sponsorToModify) {
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
