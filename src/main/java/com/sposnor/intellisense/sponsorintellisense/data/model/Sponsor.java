package com.sposnor.intellisense.sponsorintellisense.data.model;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

public class Sponsor implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

	@NotBlank
	private String firstName;
	private String middleInitial;
	@NotBlank
	private String lastName;	
	//@NotBlank
	private String sponsorCode;
	private String nickName;
	private String dayOfBirth;
	private String monthOfBirth;
	private int sponsorStatus;
	private String emailAddress;
	private String appartmentNumber;
	private String street;
	private String city;
	private String state;
	private String postalCode;
	private boolean hasAnyCoSponser;
	private String coSponserName;
	private Long parishId;
	private String parishName;
	private String parishCity;
	private Long centerId;
	private Date createdDate;
	private Date updatedDate;
	private Long createdBy;
	private Long updatedBy;
	
	private String phone1;
	
	private String phone2;
	
	private Long entId;
	private int noOfStudents;
	private int expirationMonth;
	private int expirationYear;
	private String effectiveDate;
	
	private double miscAmount;
	
	private String promoterEmail;
	
	public double getMiscAmount() {
		return miscAmount;
	}

	public void setMiscAmount(double miscAmount) {
		this.miscAmount = miscAmount;
	}


	public Long getEntId() {
		return entId;
	}

	public void setEntId(Long entId) {
		this.entId = entId;
	}

	public int getNoOfStudents() {
		return noOfStudents;
	}

	public void setNoOfStudents(int noOfStudents) {
		this.noOfStudents = noOfStudents;
	}

	public int getExpirationMonth() {
		return expirationMonth;
	}

	public void setExpirationMonth(int expirationMonth) {
		this.expirationMonth = expirationMonth;
	}

	public int getExpirationYear() {
		return expirationYear;
	}

	public void setExpirationYear(int expirationYear) {
		this.expirationYear = expirationYear;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
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

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Long getCenterId() {
		return centerId;
	}

	public void setCenterId(Long centerId) {
		this.centerId = centerId;
	}

	public String getSponsorCode() {
		return sponsorCode;
	}

	public void setSponsorCode(String sponsorCode) {
		this.sponsorCode = sponsorCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleInitial() {
		return middleInitial;
	}

	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDayOfBirth() {
		return dayOfBirth;
	}

	public void setDayOfBirth(String dayOfBirth) {
		this.dayOfBirth = dayOfBirth;
	}

	public String getMonthOfBirth() {
		return monthOfBirth;
	}

	public void setMonthOfBirth(String monthOfBirth) {
		this.monthOfBirth = monthOfBirth;
	}

	
	public int getSponsorStatus() {
		return sponsorStatus;
	}

	public void setSponsorStatus(int sponsorStatus) {
		this.sponsorStatus = sponsorStatus;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
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

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public boolean isHasAnyCoSponser() {
		return hasAnyCoSponser;
	}

	public void setHasAnyCoSponser(boolean hasAnyCoSponser) {
		this.hasAnyCoSponser = hasAnyCoSponser;
	}

	public String getCoSponserName() {
		return coSponserName;
	}

	public void setCoSponserName(String coSponserName) {
		this.coSponserName = coSponserName;
	}

	public Long getParishId() {
		return parishId;
	}

	public void setParishId(Long parishId) {
		this.parishId = parishId;
	}

	public String getParishName() {
		return parishName;
	}

	public void setParishName(String parishName) {
		this.parishName = parishName;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) throws ParseException {
		java.util.Date dt = new java.util.Date();

		java.text.SimpleDateFormat sdf = 
		     new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String currentTime = sdf.format(dt);
		
		this.createdDate = sdf.parse(currentTime);
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	public String getParishCity() {
		return parishCity;
	}

	public void setParishCity(String parishCity) {
		this.parishCity = parishCity;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getPromoterEmail() {
		return promoterEmail;
	}

	public void setPromoterEmail(String promoterEmail) {
		this.promoterEmail = promoterEmail;
	}

	@Override
	public String toString() {
		return "Sponsor [id=" + id + ", firstName=" + firstName + ", middleInitial=" + middleInitial + ", lastName="
				+ lastName + ", sponsorCode=" + sponsorCode + ", nickName=" + nickName + ", dayOfBirth=" + dayOfBirth
				+ ", monthOfBirth=" + monthOfBirth + ", sponsorStatus=" + sponsorStatus + ", emailAddress="
				+ emailAddress + ", appartmentNumber=" + appartmentNumber + ", street=" + street + ", city=" + city
				+ ", state=" + state + ", postalCode=" + postalCode + ", hasAnyCoSponser=" + hasAnyCoSponser
				+ ", coSponserName=" + coSponserName + ", parishId=" + parishId + ", parishName=" + parishName
				+ ", parishCity=" + parishCity + ", centerId=" + centerId + ", createdDate=" + createdDate
				+ ", updatedDate=" + updatedDate + ", createdBy=" + createdBy + ", updatedBy=" + updatedBy + ", phone1="
				+ phone1 + ", phone2=" + phone2 + ", entId=" + entId + ", noOfStudents=" + noOfStudents
				+ ", expirationMonth=" + expirationMonth + ", expirationYear=" + expirationYear + ", effectiveDate="
				+ effectiveDate + ", miscAmount=" + miscAmount + ", promoterEmail=" + promoterEmail + "]";
	}

	
	
	
	
}
