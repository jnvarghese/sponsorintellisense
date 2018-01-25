package com.sposnor.intellisense.sponsorintellisense.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sposnor.intellisense.sponsorintellisense.data.model.Center;
import com.sposnor.intellisense.sponsorintellisense.data.model.Region;
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
		return "{ 'sponsorCount' :"+sponsorCount+", 'studentCount' : "+studentCount+"}";
	}
	
	@GetMapping("/init/region")
	public List getRegions() {
		List consolidatedList = new ArrayList();
		List<Region> regions = initMapper.getRegions();
		return consolidatedList;
	}
	
	@GetMapping("/init/center")
	public List<Center> getCenters() {
		return initMapper.getCenters();
	}
	
}
