package com.sposnor.intellisense.sponsorintellisense.data.model;

public class Contribution {

	private String paymentDate;
	
	private String maxOut;
	
	private float contriAmount;
	
	private float miscAmount;
	
	private int noOfKids;
	
	private String studentName;
	
	private String projectName;
	
	public int getNoOfKids() {
		return noOfKids;
	}

	public void setNoOfKids(int noOfKids) {
		this.noOfKids = noOfKids;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
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
	
	
	
}
