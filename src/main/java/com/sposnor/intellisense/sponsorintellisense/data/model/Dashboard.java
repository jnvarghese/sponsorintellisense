package com.sposnor.intellisense.sponsorintellisense.data.model;

import java.util.List;

public class Dashboard {

	private int sponsorCount;
	
	private int studentCount;
	
	private int enrollmentCount;
	
	List<MaxOutOverview> maxOutOverviews0;

	List<MaxOutOverview> maxOutOverviews1;
	
	List<MaxOutOverview> maxOutOverviews2;
	
	

	public List<MaxOutOverview> getMaxOutOverviews0() {
		return maxOutOverviews0;
	}

	public void setMaxOutOverviews0(List<MaxOutOverview> maxOutOverviews0) {
		this.maxOutOverviews0 = maxOutOverviews0;
	}

	public List<MaxOutOverview> getMaxOutOverviews1() {
		return maxOutOverviews1;
	}

	public void setMaxOutOverviews1(List<MaxOutOverview> maxOutOverviews1) {
		this.maxOutOverviews1 = maxOutOverviews1;
	}

	public List<MaxOutOverview> getMaxOutOverviews2() {
		return maxOutOverviews2;
	}

	public void setMaxOutOverviews2(List<MaxOutOverview> maxOutOverviews2) {
		this.maxOutOverviews2 = maxOutOverviews2;
	}

	public int getSponsorCount() {
		return sponsorCount;
	}

	public void setSponsorCount(int sponsorCount) {
		this.sponsorCount = sponsorCount;
	}

	public int getStudentCount() {
		return studentCount;
	}

	public void setStudentCount(int studentCount) {
		this.studentCount = studentCount;
	}

	public int getEnrollmentCount() {
		return enrollmentCount;
	}

	public void setEnrollmentCount(int enrollmentCount) {
		this.enrollmentCount = enrollmentCount;
	}

}
