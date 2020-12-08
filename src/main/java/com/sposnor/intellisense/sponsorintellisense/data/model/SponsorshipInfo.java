package com.sposnor.intellisense.sponsorintellisense.data.model;

public class SponsorshipInfo {

	private float totalContribution;
	
	private String spFn;
	
	private String spLn;
	
	private String spMI;
	
	private String spStatus;
	
	private Long spId;
	
	private String sponsorCode;
	
	private String parishName;

	
	
	public String getSponsorCode() {
		return sponsorCode;
	}

	public void setSponsorCode(String sponsorCode) {
		this.sponsorCode = sponsorCode;
	}

	public String getParishName() {
		return parishName;
	}

	public void setParishName(String parishName) {
		this.parishName = parishName;
	}

	public float getTotalContribution() {
		return totalContribution;
	}

	public void setTotalContribution(float totalContribution) {
		this.totalContribution = totalContribution;
	}

	public String getSpFn() {
		return spFn;
	}

	public void setSpFn(String spFn) {
		this.spFn = spFn;
	}

	public String getSpLn() {
		return spLn;
	}

	public void setSpLn(String spLn) {
		this.spLn = spLn;
	}

	public String getSpMI() {
		return spMI;
	}

	public void setSpMI(String spMI) {
		this.spMI = spMI;
	}

	public String getSpStatus() {
		return spStatus;
	}

	public void setSpStatus(String spStatus) {
		this.spStatus = spStatus;
	}

	public Long getSpId() {
		return spId;
	}

	public void setSpId(Long spId) {
		this.spId = spId;
	}
	
	
}
