package com.sposnor.intellisense.sponsorintellisense.data.model;

import java.util.Date;

public class Substitution {

	private Long enrollmentId;
	
	private Long oldStudentId;
	
	private Long newStudentId;
	
	private Date maxOut;
	
	private String reason;
	
	private Long sponsorId;
	
	private String firstName;
	
	private String lastName;
	
	private String middleInitial;
	
	private String sponsorAddress;
	
	private String parishName;
	
	private String parishCity;

	public Long getEnrollmentId() {
		return enrollmentId;
	}

	public void setEnrollmentId(Long enrollmentId) {
		this.enrollmentId = enrollmentId;
	}

	public Long getOldStudentId() {
		return oldStudentId;
	}

	public void setOldStudentId(Long oldStudentId) {
		this.oldStudentId = oldStudentId;
	}

	public Long getNewStudentId() {
		return newStudentId;
	}

	public void setNewStudentId(Long newStudentId) {
		this.newStudentId = newStudentId;
	}

	public Date getMaxOut() {
		return maxOut;
	}

	public void setMaxOut(Date maxOut) {
		this.maxOut = maxOut;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Long getSponsorId() {
		return sponsorId;
	}

	public void setSponsorId(Long sponsorId) {
		this.sponsorId = sponsorId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMiddleInitial() {
		return middleInitial;
	}

	public void setMiddleInitial(String middleInitial) {
		this.middleInitial = middleInitial;
	}

	public String getSponsorAddress() {
		return sponsorAddress;
	}

	public void setSponsorAddress(String sponsorAddress) {
		this.sponsorAddress = sponsorAddress;
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
	
	
	
	
}
