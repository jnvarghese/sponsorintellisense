package com.sposnor.intellisense.sponsorintellisense.data.model;

public class ViewEnroll {

	private Long enrollmentId;
	
	private String sponsorName;
	
	private String sponsorNickName;
	
	private String parishName;
	
	private float contribution;
	
	private String paymentDate;

	public Long getEnrollmentId() {
		return enrollmentId;
	}

	public void setEnrollmentId(Long enrollmentId) {
		this.enrollmentId = enrollmentId;
	}

	public String getSponsorName() {
		return sponsorName;
	}

	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}

	public String getSponsorNickName() {
		return sponsorNickName;
	}

	public void setSponsorNickName(String sponsorNickName) {
		this.sponsorNickName = sponsorNickName;
	}

	public String getParishName() {
		return parishName;
	}

	public void setParishName(String parishName) {
		this.parishName = parishName;
	}

	public float getContribution() {
		return contribution;
	}

	public void setContribution(float contribution) {
		this.contribution = contribution;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}
	
	
}
