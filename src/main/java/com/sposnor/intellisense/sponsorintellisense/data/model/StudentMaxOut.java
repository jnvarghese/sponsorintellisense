package com.sposnor.intellisense.sponsorintellisense.data.model;

import java.io.Serializable;
import java.util.Date;

public class StudentMaxOut implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
    private int id;
    
    private int status;
    
	private Long studentId;
	
	private Long enrollmentId;
	
	private Date maxOut;
	

	
	 
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	
	public StudentMaxOut() {
		// TODO Auto-generated constructor stub
	}

	public StudentMaxOut(Long studentId, Long enrollmentId, Date maxOut) {
		super();
		this.studentId = studentId;
		this.enrollmentId = enrollmentId;
		this.maxOut = maxOut;
	}

	public StudentMaxOut(int id, int status, Long studentId, Long enrollmentId, Date maxOut) {
		super();
		this.id = id;
		this.status = status;
		this.studentId = studentId;
		this.enrollmentId = enrollmentId;
		this.maxOut = maxOut;
	}

	@Override
	public String toString() {
		return "StudentMaxOut [id=" + id + ", status=" + status + ", studentId=" + studentId + ", enrollmentId="
				+ enrollmentId + ", maxOut=" + maxOut + "]";
	}



}
