package com.sposnor.intellisense.sponsorintellisense.data.model;

public class ViewEnroll {

	private Long enrollmentId;
	
	private Long sponsorId;
	
	private String sponsorName;
	
	private String sponsorNickName;
	
	private String parishName;
	
	private double contribution;
	
	private double netIndividualContribution;
	
	private double netContribution; // net enrollment contribution. previous netcontribution + previous actualcontribution
	
	private String renewed;
	
	private String paymentDate;
	
	private String effectiveDate;
	
	private String createdBy;
	
	private String uniqueId;
	
	private String updatedDate;
	
	private String createdDate;
	
	
	public double getNetContribution() {
		return netContribution;
	}

	public void setNetContribution(double netContribution) {
		this.netContribution = netContribution;
	}

	public Long getSponsorId() {
		return sponsorId;
	}

	public void setSponsorId(Long sponsorId) {
		this.sponsorId = sponsorId;
	}

	public String getRenewed() {
		return renewed;
	}

	public void setRenewed(String renewed) {
		this.renewed = renewed;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

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

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(String updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public double getContribution() {
		return contribution;
	}

	public void setContribution(double contribution) {
		this.contribution = contribution;
	}

	public double getNetIndividualContribution() {
		return netIndividualContribution;
	}

	public void setNetIndividualContribution(double netIndividualContribution) {
		this.netIndividualContribution = netIndividualContribution;
	}

	@Override
	public String toString() {
		return "ViewEnroll [enrollmentId=" + enrollmentId + ", sponsorId=" + sponsorId + ", sponsorName=" + sponsorName
				+ ", sponsorNickName=" + sponsorNickName + ", parishName=" + parishName + ", contribution="
				+ contribution + ", netIndividualContribution=" + netIndividualContribution + ", netContribution="
				+ netContribution + ", renewed=" + renewed + ", paymentDate=" + paymentDate + ", effectiveDate="
				+ effectiveDate + ", createdBy=" + createdBy + ", uniqueId=" + uniqueId + ", updatedDate=" + updatedDate
				+ ", createdDate=" + createdDate + ", getNetContribution()=" + getNetContribution()
				+ ", getSponsorId()=" + getSponsorId() + ", getRenewed()=" + getRenewed() + ", getEffectiveDate()="
				+ getEffectiveDate() + ", getCreatedBy()=" + getCreatedBy() + ", getUniqueId()=" + getUniqueId()
				+ ", getEnrollmentId()=" + getEnrollmentId() + ", getSponsorName()=" + getSponsorName()
				+ ", getSponsorNickName()=" + getSponsorNickName() + ", getParishName()=" + getParishName()
				+ ", getPaymentDate()=" + getPaymentDate() + ", getUpdatedDate()=" + getUpdatedDate()
				+ ", getCreatedDate()=" + getCreatedDate() + ", getContribution()=" + getContribution()
				+ ", getNetIndividualContribution()=" + getNetIndividualContribution() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}

	
	
	
}
