package com.sposnor.intellisense.sponsorintellisense.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sposnor.intellisense.sponsorintellisense.data.model.Center;
import com.sposnor.intellisense.sponsorintellisense.data.model.Dashboard;
import com.sposnor.intellisense.sponsorintellisense.data.model.Initiative;
import com.sposnor.intellisense.sponsorintellisense.data.model.Region;
import com.sposnor.intellisense.sponsorintellisense.data.model.graph.GraphData;
import com.sposnor.intellisense.sponsorintellisense.data.model.graph.Receipt;
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
	public List<Region> getRegions() {
		return initMapper.getRegions();
	}
	
	@GetMapping("/init/initiative/list")
	public List<Initiative> getInitiatives() {
		return initMapper.getInitiatives();
	}
	
	@GetMapping("/dashboard/center")
	public List<Center> getCenters() {
		return initMapper.getCenters();
	}
	
	@GetMapping("/dashboard/effectivedataset")
	public List<GraphData> getEnrollmentEffectiveElements() {
		return dashboardMapper.getEnrollmentEffectiveDataElement();
	}
	
	@GetMapping("/dashboard/exipationdataset")
	public List<GraphData> getEnrollmentExpirationElements() {
		return dashboardMapper.getEnrollmentExiprationDataElement();
	}
	
	@GetMapping("/dashboard/sponsors/{by}")
	public List<GraphData> getEnrollmentExpirationElements(@PathVariable(value = "by") String by) {
		if("region".equalsIgnoreCase(by)) {
			return dashboardMapper.getSponsorsByRegion();
		}else {
			return dashboardMapper.getSponsorsByCenter();
		}
	}
	
	@GetMapping("/dashboard/receipts")
	public List<Receipt> getReceipts() {
		return dashboardMapper.getReceipts();
	}
	
	@GetMapping("/dashboard/sponsors")
	public List<GraphData> getSponsors() {
		return dashboardMapper.getSponsors();
	}
	
	@GetMapping("/dashboard/contributionsandsponsorcount")
	public List<GraphData> getContributionsAndSponsorCount() {
		return dashboardMapper.getContributionsAndSponsorCount();
	}
	
}
