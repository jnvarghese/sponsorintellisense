package com.sposnor.intellisense.sponsorintellisense.data.model;

import java.util.List;

public class Receipts {
	
	private String waterMark;// = "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";
	
	private Long receiptId;
	
	private Long sponsorId;
	
	private String sponsorCode;
	
	private String rdate;
	
	private int noOfRenewal;
	
	private String firstName;
	
	private String middleName;
	
	private String lastName;
	
	private String fullName;
	
	private String transaction;
	
	private double amount;
	
	private String amountInWords;
	
	private Long initiativeId;
	
	private Long subInitiativeId;
	
	private String initiative;
	
	private String streetAddress;
	
	private String city;
	
	private String state;
	
	private String zipCode;
	
	private Long centerId;
	
	private Long referenceId;
	
	private String email1;
	
	private String email2;
	
	private String phone1;
	
	private String phone2;
	
	private int type;
	
	private String createddate;
	
	private String createdbyName;
	
	private Long createdby;

	private int status;
	
	private int receiptType;
	
	private Long updatedBy;
	
	private String orgName;
	
	private String parishName;
	
	private String parishCity;
	
	private String initiativeName;
	
	private String subInitiativeName;
	
	private String coSponsor;
	
	private double sponsorReceiptAmount;
	
	private List<Initiative> initiatives;
	
	
	public int getNoOfRenewal() {
		return noOfRenewal;
	}

	public void setNoOfRenewal(int noOfRenewal) {
		this.noOfRenewal = noOfRenewal;
	}

	public List<Initiative> getInitiatives() {
		return initiatives;
	}

	public void setInitiatives(List<Initiative> initiatives) {
		this.initiatives = initiatives;
	}

	public double getSponsorReceiptAmount() {
		return sponsorReceiptAmount;
	}

	public void setSponsorReceiptAmount(double sponsorReceiptAmount) {
		this.sponsorReceiptAmount = sponsorReceiptAmount;
	}

	public String getCoSponsor() {
		return coSponsor;
	}

	public void setCoSponsor(String coSponsor) {
		this.coSponsor = coSponsor;
	}

	public String getSubInitiativeName() {
		return subInitiativeName;
	}

	public void setSubInitiativeName(String subInitiativeName) {
		this.subInitiativeName = subInitiativeName;
	}

	public Long getSubInitiativeId() {
		return subInitiativeId;
	}

	public void setSubInitiativeId(Long subInitiativeId) {
		this.subInitiativeId = subInitiativeId;
	}

	public String getAmountInWords() {
		return amountInWords;
	}

	public void setAmountInWords(String amountInWords) {
		this.amountInWords = amountInWords;
	}

	public String getSponsorCode() {
		return sponsorCode;
	}

	public void setSponsorCode(String sponsorCode) {
		this.sponsorCode = sponsorCode;
	}

	public Long getSponsorId() {
		return sponsorId;
	}

	public void setSponsorId(Long sponsorId) {
		this.sponsorId = sponsorId;
	}

	public String getParishCity() {
		return parishCity;
	}

	public void setParishCity(String parishCity) {
		this.parishCity = parishCity;
	}

	public int getReceiptType() {
		return receiptType;
	}

	public void setReceiptType(int receiptType) {
		this.receiptType = receiptType;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getParishName() {
		return parishName;
	}

	public void setParishName(String parishName) {
		this.parishName = parishName;
	}

	public String getInitiativeName() {
		return initiativeName;
	}

	public void setInitiativeName(String initiativeName) {
		this.initiativeName = initiativeName;
	}

	public Long getCenterId() {
		return centerId;
	}

	public void setCenterId(Long centerId) {
		this.centerId = centerId;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getCreatedbyName() {
		return createdbyName;
	}

	public void setCreatedbyName(String createdbyName) {
		this.createdbyName = createdbyName;
	}

	public Long getCreatedby() {
		return createdby;
	}

	public void setCreatedby(Long createdby) {
		this.createdby = createdby;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(Long receiptId) {
		this.receiptId = receiptId;
	}

	public String getRdate() {
		return rdate;
	}

	public void setRdate(String rdate) {
		this.rdate = rdate;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getTransaction() {
		return transaction;
	}

	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Long getInitiativeId() {
		return initiativeId;
	}

	public void setInitiativeId(Long initiativeId) {
		this.initiativeId = initiativeId;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public Long getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(Long referenceId) {
		this.referenceId = referenceId;
	}

	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
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

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getInitiative() {
		return initiative;
	}

	public void setInitiative(String initiative) {
		this.initiative = initiative;
	}

	public String getCreateddate() {
		return createddate;
	}

	public void setCreateddate(String createddate) {
		this.createddate = createddate;
	}
	
	public String getWaterMark() {
		return waterMark;
	}

}
