package com.sposnor.intellisense.sponsorintellisense.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sposnor.intellisense.sponsorintellisense.data.model.Center;
import com.sposnor.intellisense.sponsorintellisense.data.model.Dashboard;
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
	public Dashboard getDashboard() {
		int sponsorCount = dashboardMapper.getCountOfActiveSponsor();
		int studentCount = dashboardMapper.getCountOfActiveStudent();
		int enrollmentCount = dashboardMapper.getCountOfActiveEnrollments();
		Dashboard db = new Dashboard();
		db.setStudentCount(studentCount);
		db.setSponsorCount(sponsorCount);
		db.setEnrollmentCount(enrollmentCount);
		db.setMaxOutOverviews0(dashboardMapper.getMaxedOut());
		db.setMaxOutOverviews1(dashboardMapper.getMaxOutInOneMonth());
		db.setMaxOutOverviews2(dashboardMapper.getMaxOutInTwoMonth());
		return db;
	}
	
	@GetMapping("/init/region")
	public List getRegions() {
		List consolidatedList = new ArrayList();
		List<Region> regions = initMapper.getRegions();
		return consolidatedList;
	}
	
	@GetMapping("/dashboard/center")
	public List<Center> getCenters() {
		return initMapper.getCenters();
	}
	
}
