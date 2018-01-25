package com.sposnor.intellisense.sponsorintellisense.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sposnor.intellisense.sponsorintellisense.data.model.Agency;
import com.sposnor.intellisense.sponsorintellisense.data.model.Parish;
import com.sposnor.intellisense.sponsorintellisense.data.model.Project;
import com.sposnor.intellisense.sponsorintellisense.mapper.AgencyMapper;
import com.sposnor.intellisense.sponsorintellisense.mapper.ParishMapper;
import com.sposnor.intellisense.sponsorintellisense.mapper.ProjectMapper;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

	@Autowired
	private AgencyMapper agencyMapper;
	
	@Autowired
	private ParishMapper parishMapper;
	
	@Autowired
	private ProjectMapper projectMapper;
	
	@GetMapping("/projects/list")
	public List<Project> getProjects() {
		return projectMapper.list();
	}
	
	@GetMapping("/parishes/list/{id}")
	public List<Parish> getParishesByCenterId(@PathVariable(value = "id") Long centerId) {
		return parishMapper.listAllParishByCenterId(centerId);
	}
	
	@GetMapping("/parishes/list")
	public List<Parish> getParishes() {
		return parishMapper.list();
	}
	
	@GetMapping("/agencies/list")
	public List<Agency> getAgencies() {
		return agencyMapper.list();
	}
	
}
