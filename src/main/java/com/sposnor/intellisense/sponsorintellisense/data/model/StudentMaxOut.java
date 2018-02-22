package com.sposnor.intellisense.sponsorintellisense.data.model;

import java.io.Serializable;
import java.util.Date;

public class StudentMaxOut implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long studentId;
	
	private Long enrollmentId;
	
	private Date maxOut;
	
	

	public StudentMaxOut(Long studentId, Long enrollmentId, Date maxOut) {
		super();
		this.studentId = studentId;
		this.enrollmentId = enrollmentId;
		this.maxOut = maxOut;
	}
	
	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getEnrollmentId() {
		return enrollmentId;
	}

	public void setEnrollmentId(Long enrollmentId) {
		this.enrollmentId = enrollmentId;
	}

	public Date getMaxOut() {
		return maxOut;
	}

	public void setMaxOut(Date max_out) {
		this.maxOut = max_out;
	}

	@Override
	public String toString() {
		return "StudentMaxOut [studentId=" + studentId + ", enrollmentId=" + enrollmentId + ", max_out="
				+ maxOut + "]";
	}
	

}
