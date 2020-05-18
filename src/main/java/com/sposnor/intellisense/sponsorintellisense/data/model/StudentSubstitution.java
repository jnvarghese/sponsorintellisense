package com.sposnor.intellisense.sponsorintellisense.data.model;

import java.util.Date;

public class StudentSubstitution {
	
	private Long id;
	
	private Long enrollmentId;
	
	private int expirationMonth;
	
	private int expirationYear;
	
	private Long substitutedStudentId;
	
	private Long studentId;
	
	private Long createdBy;
	
	private Date createdDate;
	
	private String reason;
	
	private Date maxOut;

	
	public Date getMaxOut() {
		return maxOut;
	}

	public void setMaxOut(Date maxOut) {
		this.maxOut = maxOut;
	}

	public Long getSubstitutedStudentId() {
		return substitutedStudentId;
	}

	public void setSubstitutedStudentId(Long substitutedStudentId) {
		this.substitutedStudentId = substitutedStudentId;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getEnrollmentId() {
		return enrollmentId;
	}

	public void setEnrollmentId(Long enrollmentId) {
		this.enrollmentId = enrollmentId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	@Override
	public String toString() {
		return "SponseeSoftDelete [id=" + id + ", enrollmentId=" + enrollmentId + ", expirationMonth=" + expirationMonth
				+ ", expirationYear=" + expirationYear + ", studentId=" + studentId + ", createdBy=" + createdBy
				+ ", createdDate=" + createdDate + "]";
	}

	public StudentSubstitution(Long id, Long enrollmentId, int expirationMonth, int expirationYear, Long studentId, 
			Long substitutedStudentId, String reason, Date maxOut, Long createdBy) {
		super();
		this.id = id;
		this.enrollmentId = enrollmentId;
		this.expirationMonth = expirationMonth;
		this.expirationYear = expirationYear;
		this.studentId = studentId;
		this.substitutedStudentId = substitutedStudentId;
		this.reason = reason;
		this.maxOut = maxOut;
		this.createdBy = createdBy;
	}
	
}
