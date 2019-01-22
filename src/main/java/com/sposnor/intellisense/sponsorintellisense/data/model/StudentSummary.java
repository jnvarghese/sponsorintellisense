package com.sposnor.intellisense.sponsorintellisense.data.model;

import java.util.Date;

public class StudentSummary {

	private Long studentId;
	
	private String studentCode;
	
	private Date maxOut;

	private String maxOutMonth;
	
	private String maxOutYear;

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

	
	
	
}
