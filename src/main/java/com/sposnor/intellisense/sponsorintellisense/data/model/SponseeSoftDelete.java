package com.sposnor.intellisense.sponsorintellisense.data.model;

import java.util.Date;

public class SponseeSoftDelete{
	
	private Long id;
	
	private Long enrollmentId;
	
	private int expirationMonth;
	
	private int expirationYear;
	
	private Long studentId;
	
	private Long createdBy;
	
	private Date createdDate;

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

	public SponseeSoftDelete(Long id, Long enrollmentId, int expirationMonth, int expirationYear, Long studentId,
			Long createdBy) {
		super();
		this.id = id;
		this.enrollmentId = enrollmentId;
		this.expirationMonth = expirationMonth;
		this.expirationYear = expirationYear;
		this.studentId = studentId;
		this.createdBy = createdBy;
	}

	
	/*
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
*/
	
	
}
