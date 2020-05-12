package com.sposnor.intellisense.sponsorintellisense.data.model;

public class SponsorReport {

	private String uniqueId;
	
	private String sponsorName;
	
	private String nickName;
	
	private String parishName;
	
	private String parishCity;
	
	private String centerName;
	
	private String regionName;
	
	private String appartmentNumber;
	
	private String street;
	
	private String sponsorCity;
	
	private String sponsorState;
	
	private String postalCode;
	
	private String emailAddress;
	
	private String emailAddress2;
	
	private String phone1;
	
	private String phone2;
	
	private String paymentDate;
	
	private String contribution;
	
	private String miscAmount;
	
	private String total;
	
	private String renewalDue;
	
	
	private String netContribution;
	
	private String netTotal;
	
	
	
	public String getNetContribution() {
		return netContribution;
	}

	public void setNetContribution(String netContribution) {
		this.netContribution = netContribution;
	}

	public String getNetTotal() {
		return netTotal;
	}

	public void setNetTotal(String netTotal) {
		this.netTotal = netTotal;
	}


	public String getMiscAmount() {
		return miscAmount;
	}

	public void setMiscAmount(String miscAmount) {
		this.miscAmount = miscAmount;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getRenewalDue() {
		return renewalDue;
	}

	public void setRenewalDue(String renewalDue) {
		this.renewalDue = renewalDue;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getSponsorName() {
		return sponsorName;
	}

	public void setSponsorName(String sponsorName) {
		this.sponsorName = sponsorName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getParishName() {
		return parishName;
	}

	public void setParishName(String parishName) {
		this.parishName = parishName;
	}

	public String getParishCity() {
		return parishCity;
	}

	public void setParishCity(String parishCity) {
		this.parishCity = parishCity;
	}

	public String getCenterName() {
		return centerName;
	}

	public void setCenterName(String centerName) {
		this.centerName = centerName;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getAppartmentNumber() {
		return appartmentNumber;
	}

	public void setAppartmentNumber(String appartmentNumber) {
		this.appartmentNumber = appartmentNumber;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getSponsorCity() {
		return sponsorCity;
	}

	public void setSponsorCity(String sponsorCity) {
		this.sponsorCity = sponsorCity;
	}

	public String getSponsorState() {
		return sponsorState;
	}

	public void setSponsorState(String sponsorState) {
		this.sponsorState = sponsorState;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	
	
	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getContribution() {
		return contribution;
	}

	public void setContribution(String contribution) {
		this.contribution = contribution;
	}

	public String getEmailAddress2() {
		return emailAddress2;
	}

	public void setEmailAddress2(String emailAddress2) {
		this.emailAddress2 = emailAddress2;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	

	@Override
	public String toString() {
		return "SponsorReport [uniqueId=" + uniqueId + ", sponsorName=" + sponsorName + ", nickName=" + nickName
				+ ", parishName=" + parishName + ", parishCity=" + parishCity + ", centerName=" + centerName
				+ ", regionName=" + regionName + ", appartmentNumber=" + appartmentNumber + ", street=" + street
				+ ", sponsorCity=" + sponsorCity + ", sponsorState=" + sponsorState + ", postalCode=" + postalCode
				+ ", emailAddress=" + emailAddress + ", emailAddress1=" + emailAddress2 + ", phone1=" + phone1
				+ ", phone2=" + phone2 + ", paymentDate=" + paymentDate + ", contribution=" + contribution + "]";
	}

	
	
	
}
