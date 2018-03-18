package com.sposnor.intellisense.sponsorintellisense.web.controller;

import java.sql.SQLIntegrityConstraintViolationException;
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

import com.sposnor.intellisense.sponsorintellisense.data.model.Agency;
import com.sposnor.intellisense.sponsorintellisense.data.model.Parish;
import com.sposnor.intellisense.sponsorintellisense.data.model.ParishProject;
import com.sposnor.intellisense.sponsorintellisense.data.model.Project;
import com.sposnor.intellisense.sponsorintellisense.mapper.AgencyMapper;
import com.sposnor.intellisense.sponsorintellisense.mapper.ParishMapper;
import com.sposnor.intellisense.sponsorintellisense.mapper.ParishProjectMapper;
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
	
	@Autowired
	private ParishProjectMapper parishProjectMapper;

	@GetMapping("/projects/list")
	public List<Project> getProjects() {
		return projectMapper.list();
	}
	
	@GetMapping("/projects/parish/list/{id}")
	public List<ParishProject> getProjectsWithSelection(@PathVariable(value = "id") Long parishId) {
		return parishProjectMapper.findMappedProjectsByParishId(parishId);
		//return parishProjectMapper.findProjectsByParishId(parishId);
	}

	@PostMapping("/projects/add")
	public Project createProject(@Valid @RequestBody Project project) {
		projectMapper.insert(project);
		return project;
	}

	@GetMapping("/projects/find/{id}")
	public ResponseEntity<Project> getProjectById(@PathVariable(value = "id") Long projectId) {
		Project project = projectMapper.findById(projectId);
		if (project == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(project);
	}

	@PutMapping("/projects/modify/{id}")
	public Project updateProject(@PathVariable(value = "id") Long projectId,
			@Valid @RequestBody Project project) {
		projectMapper.update(project);
		return project;
	}
	
	@GetMapping("/parishprojects/list/{id}")
	public List<Project> getParishProjectsByParishId(@PathVariable(value = "id") Long parishId) {
		return projectMapper.findProjectsByParishId(parishId);
	}
	
	@GetMapping("/parishes/list/{id}")
	public List<Parish> getParishesByCenterId(@PathVariable(value = "id") Long centerId) {
		return parishMapper.listAllParishByCenterId(centerId);
	}

	@GetMapping("/parishes/list")
	public List<Parish> getParishes() {
		return parishMapper.list();
	}

	@PostMapping("/parishes/add")
	public Parish createParish(@RequestBody Parish parish) {	
		try {
			parishMapper.insert(parish);
			for(ParishProject pp: parish.getProjects()) {
				pp.setParishId(parish.getId());
				if(pp.isselected()) {
					parishProjectMapper.insertBatch(pp);
				}
			}			
		} catch (SQLIntegrityConstraintViolationException e) {
			e.printStackTrace();
			return parish;
		}
		catch (Exception e) {
			e.printStackTrace();
			return parish;
		}
		return parish;
	}

	// Select Parish from the Admin -> Parish List - Modify
	@GetMapping("/parishes/find/{id}")
	public ResponseEntity<Parish> getParishById(@PathVariable(value = "id") Long parishId) {
		List<ParishProject> sets = parishProjectMapper.findMappedProjectsByParishId(parishId);
		Parish parish = parishMapper.findById(parishId);		
		if (parish == null) {
			return ResponseEntity.notFound().build();
		}
		parish.setProjects(sets);
		return ResponseEntity.ok().body(parish);
	}

	@PutMapping("/parishes/modify/{id}")
	public Parish updateParish(@PathVariable(value = "id") Long parishId,
			 @RequestBody Parish parish) {
		System.out.println( " -- parish "+ parish);
		parishMapper.update(parish);
		for(ParishProject pp: parish.getProjects()) {
			pp.setParishId(parish.getId());
			if(null != pp.getPpId()) {
				parishProjectMapper.updateBatch(pp);
			}else {
				if(pp.isselected()) {
					parishProjectMapper.insertBatch(pp);
				}
			}
		}	
		return parish;
	}

	@GetMapping("/agencies/list")
	public List<Agency> getAgencies() {
		return agencyMapper.list();
	}

	@PostMapping("/agencies/add")
	public Agency createSponsor(@Valid @RequestBody Agency agency) {
		try {
			agencyMapper.insert(agency);			
		} catch (SQLIntegrityConstraintViolationException ex) {
			ex.printStackTrace();
			return agency;
			//return ResponseEntity.badRequest().body("Duplicate Code "+agency.getCode()+".Please try a different code.");
		}
		
		return agency;
	}

	@GetMapping("/agencies/find/{id}")
	public ResponseEntity<Agency> getAgencyById(@PathVariable(value = "id") Long agencyId) {
		Agency agency = agencyMapper.findById(agencyId);
		if (agency == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok().body(agency);
	}

	@PutMapping("/agencies/modify/{id}")
	public Agency updateAgency(@PathVariable(value = "id") Long agencyId,
			@Valid @RequestBody Agency agency) {
		agencyMapper.update(agency);
		return agency;
	}
}
