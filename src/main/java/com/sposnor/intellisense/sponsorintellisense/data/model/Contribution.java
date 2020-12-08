package com.sposnor.intellisense.sponsorintellisense.data.model;

public class Contribution {

	private String effectiveDate;
	
	private String maxOut;
	
	private float contriAmount;
	
	private float miscAmount;
	
	private int noOfKids;
	
	private String studentName;
	
	private String projectName;
	
	private String renewed;
	
	private String maxOutDate;
	
	
	public String getMaxOutDate() {
		return maxOutDate;
	}

	public void setMaxOutDate(String maxOutDate) {
		this.maxOutDate = maxOutDate;
	}

	public int getNoOfKids() {
		return noOfKids;
	}

	public void setNoOfKids(int noOfKids) {
		this.noOfKids = noOfKids;
	}

	public String getMaxOut() {
		return maxOut;
	}

	public void setMaxOut(String maxOut) {
		this.maxOut = maxOut;
	}

	public float getContriAmount() {
		return contriAmount;
	}

	public void setContriAmount(float contriAmount) {
		this.contriAmount = contriAmount;
	}

	public float getMiscAmount() {
		return miscAmount;
	}

	public void setMiscAmount(float miscAmount) {
		this.miscAmount = miscAmount;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getRenewed() {
		return renewed;
	}

	public void setRenewed(String renewed) {
		this.renewed = renewed;
	}
}
