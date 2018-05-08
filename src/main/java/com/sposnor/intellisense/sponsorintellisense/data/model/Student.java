package com.sposnor.intellisense.sponsorintellisense.data.model;

import java.io.Serializable;
import java.text.ParseException;
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
	private String studentName;
	
	//@NotBlank
	private String studentCode;
	
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
	
	private String grade;
	private String favColor;
	private String favGame;
	private String nameOfGuardian;
	private String occupationOfGuardian;
	private String baseLanguage;
	
	private String imageLinkRef;
	
	private Date createdDate;
	private Date updatedDate;
	private int expirationMonth;
	private int expirationYear;
	
	private Long createdBy;
	
	private Long updatedBy;	

	
	public String getImageLinkRef() {
		return imageLinkRef;
	}
	public void setImageLinkRef(String imageLinkRef) {
		this.imageLinkRef = imageLinkRef;
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
	public String getStudentCode() {
		return studentCode;
	}
	public void setStudentCode(String studentCode) {
		this.studentCode = studentCode;
	}
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
	
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
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
	
	
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getFavColor() {
		return favColor;
	}
	public void setFavColor(String favColor) {
		this.favColor = favColor;
	}
	public String getFavGame() {
		return favGame;
	}
	public void setFavGame(String favGame) {
		this.favGame = favGame;
	}
	public String getNameOfGuardian() {
		return nameOfGuardian;
	}
	public void setNameOfGuardian(String nameOfGuardian) {
		this.nameOfGuardian = nameOfGuardian;
	}
	public String getOccupationOfGuardian() {
		return occupationOfGuardian;
	}
	public void setOccupationOfGuardian(String occupationOfGuardian) {
		this.occupationOfGuardian = occupationOfGuardian;
	}
	public String getBaseLanguage() {
		return baseLanguage;
	}
	public void setBaseLanguage(String baseLanguage) {
		this.baseLanguage = baseLanguage;
	}
	@Override
	public String toString() {
		return "Student [id=" + id + ", projectId=" + projectId + ", projectName=" + projectName + ", agencyId="
				+ agencyId + ", agencyName=" + agencyName 
				+ ", studentName=" + studentName + ", gender=" + gender + ", dateOfBirth=" + dateOfBirth + ", address="
				+ address + ", status=" + status + ", hobbies=" + hobbies + ", contributions=" + contributions
				+ ", talent=" + talent + ", recentAchivements=" + recentAchivements + ", profilePicture="
				+ profilePicture + ", softlocked=" + softlocked + ", maxOut=" + maxOut + ", createdDate=" + createdDate
				+ ", updatedDate=" + updatedDate + ", expirationMonth=" + expirationMonth + ", expirationYear="
				+ expirationYear + "]";
	}
}
