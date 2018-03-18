package com.sposnor.intellisense.sponsorintellisense.data.model;

import java.io.IOException;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class SponseeReport {

	private String uniqueId;
	
	private String studentName;
	
	private String dateOfBirth;
	
	private String gender;
	
	private String hobby;
	
	private String projectName;
	
	private String agencyName;
	
	private byte[] profilePicture;
	
	
	public String getProfilePicture() throws IOException {
		String encodedImage = Base64.encode(this.profilePicture);
		return encodedImage;
	}

	public void setProfilePicture(byte[] profilePicture) {
		this.profilePicture = profilePicture;
	}	

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}
	
}
