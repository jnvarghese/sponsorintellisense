package com.sposnor.intellisense.sponsorintellisense.data.model;

public class MaxOutOverview {

	private Long regionId;

	private String regionName;

	private Long centerId;

	private String centerName;

	private Long parishid;

	private String parishName;

	private Long sponsorId;

	private String sponsorName;

	private double miscAmount;

	private String maxOut;

	private Long enrollmentId;

	private int maxoutYear;

	private String maxoutMonth;

	
	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	public Long getCenterId() {
		return centerId;
	}

	public void setCenterId(Long centerId) {
		this.centerId = centerId;
	}

	public Long getParishid() {
		return parishid;
	}

	public void setParishid(Long parishid) {
		this.parishid = parishid;
	}

	public Long getSponsorId() {
		return sponsorId;
	}

	public void setSponsorId(Long sponsorId) {
		this.sponsorId = sponsorId;
	}

	public Long getEnrollmentId() {
		return enrollmentId;
	}

	public void setEnrollmentId(Long enrollmentId) {
		this.enrollmentId = enrollmentId;
	}

	public int getMaxoutYear() {
		return maxoutYear;
	}

	public void setMaxoutYear(int maxoutYear) {
		this.maxoutYear = maxoutYear;
	}

	public String getMaxoutMonth() {
		return maxoutMonth;
	}

	public void setMaxoutMonth(String maxoutMonth) {
		this.maxoutMonth = maxoutMonth;
	}

	public double getMiscAmount() {
		return miscAmount;
	}

	public void setMiscAmount(double miscAmount) {
		this.miscAmount = miscAmount;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getCenterName() {
		return centerName;
	}

	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}

	public String getParishName() {
		return parishName;
	}

	public void setParishName(String parishName) {
		this.parishName = parishName;
	}

	public String getSponsorName() {
		return sponsorName;
	}

	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}

	public String getMaxOut() {
		return maxOut;
	}

	public void setMaxOut(String maxOut) {
		this.maxOut = maxOut;
	}

}
