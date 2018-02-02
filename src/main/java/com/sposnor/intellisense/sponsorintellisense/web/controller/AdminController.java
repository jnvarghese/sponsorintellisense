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
		return parishProjectMapper.findByParishId(parishId);
	}

	@PostMapping("/projects/add")
	public ResponseEntity<String> createProject(@Valid @RequestBody Project project) {
		projectMapper.insert(project);
		return ResponseEntity.ok().body("Success");
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
	public ResponseEntity<String> updateProject(@PathVariable(value = "id") Long projectId,
			@Valid @RequestBody Project project) {
		projectMapper.update(project);
		return ResponseEntity.ok().body("Success");
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
	public ResponseEntity<String> createParish(@RequestBody Parish parish) {	
		try {
			parishMapper.insert(parish);
			for(ParishProject pp: parish.getProjects()) {
				pp.setParishId(parish.getId());
				System.out.println("  -- pp --"+pp);
				parishProjectMapper.insertBatch(pp);
			}			
		} catch (SQLIntegrityConstraintViolationException e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.ok().body("Success");
	}

	@GetMapping("/parishes/find/{id}")
	public ResponseEntity<Parish> getParishById(@PathVariable(value = "id") Long parishId) {
		List<ParishProject> sets = parishProjectMapper.findByParishId(parishId);
		Parish parish = parishMapper.findById(parishId);		
		if (parish == null) {
			return ResponseEntity.notFound().build();
		}
		parish.setProjects(sets);
		return ResponseEntity.ok().body(parish);
	}

	@PutMapping("/parishes/modify/{id}")
	public ResponseEntity<String> updateParish(@PathVariable(value = "id") Long parishId,
			@Valid @RequestBody Parish parish) {
		parishMapper.update(parish);
		return ResponseEntity.ok().body("Success");
	}

	@GetMapping("/agencies/list")
	public List<Agency> getAgencies() {
		return agencyMapper.list();
	}

	@PostMapping("/agencies/add")
	public ResponseEntity<String> createSponsor(@Valid @RequestBody Agency agency) {
		try {
			agencyMapper.insert(agency);			
		} catch (SQLIntegrityConstraintViolationException ex) {
			ex.printStackTrace();
			return ResponseEntity.badRequest().body("Duplicate Code "+agency.getCode()+".Please try a different code.");
		}
		
		return ResponseEntity.ok().body("Success");
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
	public ResponseEntity<String> updateAgency(@PathVariable(value = "id") Long agencyId,
			@Valid @RequestBody Agency agency) {
		agencyMapper.update(agency);
		return ResponseEntity.ok().body("Success");
	}
}
