package com.sposnor.intellisense.sponsorintellisense.data.model;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class StudentSummary implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long enrollmentId;
	
	private String effectiveDate;
	
	private double actualAmount;
	
	private double contributionAmount;
	
	private double miscAmount;
	
	private String createdBy;
	
	private String createdDate;
	
	private Long sponseeId;
	
	private Long stuMaxoutId;
	
	private Long agencyId;
	
	private String agencyName;
	
	private Long projectId;
	
	private String projectName;
	
	private String uniqueId; // UNIQUEID
	
	private Long studentId;
	
	private String studentName;
	
	private String gender;
	
	private String grade;
	
	private String nameOfGuardian;
	
	private String occupationOfGuardian;
	
	private String dateOfBirth;
	
	private String maxout;
	
	private String days;
	
	private String sponsorfirstName;
	
	private String sponsorMiddleInitial;
	
	private String sponsorLastName;
	
	private String sponsorCode;
	
	private Long sponsorId;
	
	private Long parishId;
	
	private String parishName;
	
	private String parishCity;
	
	private String studentCode;
	
	private Date maxOut;

	private String maxOutMonth;
	
	private String maxOutYear;
	
	private int status;
	
	
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Long getParishId() {
		return parishId;
	}

	public void setParishId(Long parishId) {
		this.parishId = parishId;
	}

	public Long getEnrollmentId() {
		return enrollmentId;
	}

	public void setEnrollmentId(Long enrollmentId) {
		this.enrollmentId = enrollmentId;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public double getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(double actualAmount) {
		this.actualAmount = actualAmount;
	}

	public double getContributionAmount() {
		return contributionAmount;
	}

	public void setContributionAmount(double contributionAmount) {
		this.contributionAmount = contributionAmount;
	}

	public double getMiscAmount() {
		return miscAmount;
	}

	public void setMiscAmount(double miscAmount) {
		this.miscAmount = miscAmount;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}

	public Long getSponseeId() {
		return sponseeId;
	}

	public void setSponseeId(Long sponseeId) {
		this.sponseeId = sponseeId;
	}

	public Long getStuMaxoutId() {
		return stuMaxoutId;
	}

	public void setStuMaxoutId(Long stuMaxoutId) {
		this.stuMaxoutId = stuMaxoutId;
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

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public Long getSponsorId() {
		return sponsorId;
	}

	public void setSponsorId(Long sponsorId) {
		this.sponsorId = sponsorId;
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

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
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

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getMaxout() {
		return maxout;
	}

	public void setMaxout(String maxout) {
		this.maxout = maxout;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getSponsorfirstName() {
		return sponsorfirstName;
	}

	public void setSponsorfirstName(String sponsorfirstName) {
		this.sponsorfirstName = sponsorfirstName;
	}

	public String getSponsorMiddleInitial() {
		return sponsorMiddleInitial;
	}

	public void setSponsorMiddleInitial(String sponsorMiddleInitial) {
		this.sponsorMiddleInitial = sponsorMiddleInitial;
	}

	public String getSponsorLastName() {
		return sponsorLastName;
	}

	public void setSponsorLastName(String sponsorLastName) {
		this.sponsorLastName = sponsorLastName;
	}

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

	public String getParishCity() {
		return parishCity;
	}

	public void setParishCity(String parishCity) {
		this.parishCity = parishCity;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public String getStudentCode() {
		return studentCode;
	}

	public void setStudentCode(String studentCode) {
		this.studentCode = studentCode;
	}

	public Date getMaxOut() {
		return maxOut;
	}

	public void setMaxOut(Date maxOut) {
		this.maxOut = maxOut;
	}

	public String getMaxOutMonth() {
		return maxOutMonth;
	}

	public void setMaxOutMonth(String maxOutMonth) {
		this.maxOutMonth = maxOutMonth;
	}

	public String getMaxOutYear() {
		return maxOutYear;
	}

	public void setMaxOutYear(String maxOutYear) {
		this.maxOutYear = maxOutYear;
	}
	public StudentSummary() {
		// TODO Auto-generated constructor stub
	}
	
	public StudentSummary(Long enrollmentId, String studentCode, Date maxOut, String maxOutMonth, String maxOutYear) {
		super();
		this.enrollmentId = enrollmentId;
		this.studentCode = studentCode;
		this.maxOut = maxOut;
		this.maxOutMonth = maxOutMonth;
		this.maxOutYear = maxOutYear;
	}

	@Override
	public String toString() {
		return "StudentSummary [agencyId=" + agencyId + ", agencyName=" + agencyName + ", projectId=" + projectId
				+ ", projectName=" + projectName + ", uniqueId=" + uniqueId + ", studentId=" + studentId
				+ ", studentName=" + studentName + ", gender=" + gender + ", grade=" + grade + ", nameOfGuardian="
				+ nameOfGuardian + ", occupationOfGuardian=" + occupationOfGuardian + ", dateOfBirth=" + dateOfBirth
				+ ", maxout=" + maxout + ", days=" + days + ", sponsorfirstName=" + sponsorfirstName
				+ ", sponsorMiddleInitial=" + sponsorMiddleInitial + ", sponsorLastName=" + sponsorLastName
				+ ", sponsorCode=" + sponsorCode + ", sponsorId=" + sponsorId + ", parishName=" + parishName
				+ ", parishCity=" + parishCity + ", studentCode=" + studentCode + ", maxOut=" + maxOut
				+ ", maxOutMonth=" + maxOutMonth + ", maxOutYear=" + maxOutYear + ", getAgencyId()=" + getAgencyId()
				+ ", getAgencyName()=" + getAgencyName() + ", getProjectId()=" + getProjectId() + ", getProjectName()="
				+ getProjectName() + ", getUniqueId()=" + getUniqueId() + ", getSponsorId()=" + getSponsorId()
				+ ", getStudentName()=" + getStudentName() + ", getGender()=" + getGender() + ", getGrade()="
				+ getGrade() + ", getNameOfGuardian()=" + getNameOfGuardian() + ", getOccupationOfGuardian()="
				+ getOccupationOfGuardian() + ", getDateOfBirth()=" + getDateOfBirth() + ", getMaxout()=" + getMaxout()
				+ ", getDays()=" + getDays() + ", getSponsorfirstName()=" + getSponsorfirstName()
				+ ", getSponsorMiddleInitial()=" + getSponsorMiddleInitial() + ", getSponsorLastName()="
				+ getSponsorLastName() + ", getSponsorCode()=" + getSponsorCode() + ", getParishName()="
				+ getParishName() + ", getParishCity()=" + getParishCity() + ", getStudentId()=" + getStudentId()
				+ ", getStudentCode()=" + getStudentCode() + ", getMaxOut()=" + getMaxOut() + ", getMaxOutMonth()="
				+ getMaxOutMonth() + ", getMaxOutYear()=" + getMaxOutYear() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}	
}
