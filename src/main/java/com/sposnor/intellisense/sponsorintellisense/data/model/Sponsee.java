package com.sposnor.intellisense.sponsorintellisense.data.model;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;

public class Sponsee implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Long enrollmentId;
	
	@NotBlank
	private int expirationMonth;
	
	@NotBlank
	private int expirationYear;
	
	private Long studentId;
	
	private Date createdDate;

	private Date updatedDate;
	
	private int status;
	
	
	
	public Sponsee() {
		// TODO Auto-generated constructor stub
	}
	
	public Sponsee(Long enrollmentId, int expirationMonth, int expirationYear, Long studentId) {
		super();
		this.enrollmentId = enrollmentId;
		this.expirationMonth = expirationMonth;
		this.expirationYear = expirationYear;
		this.studentId = studentId;
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
 
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Sponsee [id=" + id + ", enrollmentId=" + enrollmentId + ", expirationMonth=" + expirationMonth
				+ ", expirationYear=" + expirationYear + ", studentId=" + studentId  + "]";
	}
	
	
}
