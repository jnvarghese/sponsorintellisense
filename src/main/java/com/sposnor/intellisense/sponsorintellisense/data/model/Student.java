package com.sposnor.intellisense.sponsorintellisense.data.model;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

public class Student implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Long id;	
	private Long projectId;	
	private String projectName;	
	private Long agencyId;	
	private String agencyName;
	@NotBlank
	private String firstName;
	private String middleName;	
	@NotBlank
	private String lastName;
	@NotBlank
	private String gender;
	@NotBlank
	private String dateOfBirth;
	private String address;
	@NotBlank
	private String status;	
	private String hobbies;
	private String contributions;
	private String talent;
	private String recentAchivements;
	private byte[] profilePicture;
	private boolean softlocked;	
	private Date maxOut;
	
	private Date createdDate;
	private Date updatedDate;
	private int expirationMonth;
	private int expirationYear;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public Long getAgencyId() {
		return agencyId;
	}
	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}
	public String getAgencyName() {
		return agencyName;
	}
	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
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
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getHobbies() {
		return hobbies;
	}
	public void setHobbies(String hobbies) {
		this.hobbies = hobbies;
	}
	public String getContributions() {
		return contributions;
	}
	public void setContributions(String contributions) {
		this.contributions = contributions;
	}
	public String getTalent() {
		return talent;
	}
	public void setTalent(String talent) {
		this.talent = talent;
	}
	public String getRecentAchivements() {
		return recentAchivements;
	}
	public void setRecentAchivements(String recentAchivements) {
		this.recentAchivements = recentAchivements;
	}
	
	public byte[] getProfilePicture() {
		return profilePicture;
	}
	public void setProfilePicture(byte[] profilePicture) {
		this.profilePicture = profilePicture;
	}
	public boolean isSoftlocked() {
		return softlocked;
	}
	public void setSoftlocked(boolean softlocked) {
		this.softlocked = softlocked;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getUpdatedDate() {
		return updatedDate;
	}
	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
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
	
	public Date getMaxOut() {
		return maxOut;
	}
	public void setMaxOut(Date maxOut) {
		this.maxOut = maxOut;
	}
	@Override
	public String toString() {
		return "Student [id=" + id + ", projectId=" + projectId + ", projectName=" + projectName + ", agencyId="
				+ agencyId + ", agencyName=" + agencyName + ", firstName=" + firstName + ", middleName=" + middleName
				+ ", lastName=" + lastName + ", gender=" + gender + ", dateOfBirth=" + dateOfBirth + ", address="
				+ address + ", status=" + status + ", hobbies=" + hobbies + ", contributions=" + contributions
				+ ", talent=" + talent + ", recentAchivements=" + recentAchivements + ", profilePicture="
				+ profilePicture + ", softlocked=" + softlocked + ", maxOut=" + maxOut + ", createdDate=" + createdDate
				+ ", updatedDate=" + updatedDate + ", expirationMonth=" + expirationMonth + ", expirationYear="
				+ expirationYear + "]";
	}
}
