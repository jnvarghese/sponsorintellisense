package com.sposnor.intellisense.sponsorintellisense.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sposnor.intellisense.sponsorintellisense.data.model.Agency;
import com.sposnor.intellisense.sponsorintellisense.data.model.Parish;
import com.sposnor.intellisense.sponsorintellisense.data.model.Project;
import com.sposnor.intellisense.sponsorintellisense.mapper.DashboardMapper;
import com.sposnor.intellisense.sponsorintellisense.mapper.InitMapper;

@RestController
@RequestMapping("/api")
public class DashboardController {

	@Autowired
	private DashboardMapper dashboardMapper;
	
	@Autowired
	private InitMapper initMapper;
		
	@GetMapping("/dashboard")
	public String getDashboard() {
		Long sponsorCount = dashboardMapper.getCountOfActiveSponsor();
		Long studentCount = dashboardMapper.getCountOfActiveStudent();
		return "{sponsorCount :"+sponsorCount+", studentCount: "+studentCount+"}";
	}
	
	@GetMapping("/init")
	public List getInit() {
		List consolidatedList = new ArrayList();
		List<Project> projectList = initMapper.getProjectList(0);
		List<Parish> parishList =initMapper.getParishList(0);
		List<Agency> agencyList =initMapper.getAgencyList(0);
		consolidatedList.add(projectList);
		consolidatedList.add(parishList);
		consolidatedList.add(agencyList);
		return consolidatedList;
	}
	
}
