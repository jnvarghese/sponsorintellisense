package com.sposnor.intellisense.sponsorintellisense.data.model;

import java.util.ArrayList;
import java.util.List;

public class EnrollmentSummary {

	private Long sponsorId;
	
	private String sponsorCode;
	
	private Long studentId;
	
	private Long enrollmentId;
	
	private String sponsorFirstName;
	
	private String sponsorLastName;
	
	private String sponsorMi;
	
	private String sponsorNickName;
	
	private String parishName;
	
	private String parishCity;
	
	private float contribution;
	
	private int numberOfStudents;
	
	private List<StudentSummary> students = new ArrayList<StudentSummary>();
	
	public int getNumberOfStudents() {
		return numberOfStudents;
	}

	public void setNumberOfStudents(int numberOfStudents) {
		this.numberOfStudents = numberOfStudents;
	}

	

	public Long getEnrollmentId() {
		return enrollmentId;
	}

	public void setEnrollmentId(Long enrollmentId) {
		this.enrollmentId = enrollmentId;
	}

	public Long getSponsorId() {
		return sponsorId;
	}

	public void setSponsorId(Long sponsorId) {
		this.sponsorId = sponsorId;
	}

	public String getSponsorCode() {
		return sponsorCode;
	}

	public void setSponsorCode(String sponsorCode) {
		this.sponsorCode = sponsorCode;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public String getSponsorFirstName() {
		return sponsorFirstName;
	}

	public void setSponsorFirstName(String sponsorFirstName) {
		this.sponsorFirstName = sponsorFirstName;
	}

	public String getSponsorLastName() {
		return sponsorLastName;
	}

	public void setSponsorLastName(String sponsorLastName) {
		this.sponsorLastName = sponsorLastName;
	}

	public String getSponsorMi() {
		return sponsorMi;
	}

	public void setSponsorMi(String sponsorMi) {
		this.sponsorMi = sponsorMi;
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

	public String getParishCity() {
		return parishCity;
	}

	public void setParishCity(String parishCity) {
		this.parishCity = parishCity;
	}

	public float getContribution() {
		return contribution;
	}

	public void setContribution(float contribution) {
		this.contribution = contribution;
	}

	public List<StudentSummary> getStudents() {
		return students;
	}

	public void setStudents(List<StudentSummary> students) {
		this.students = students;
	}


	

	
}
