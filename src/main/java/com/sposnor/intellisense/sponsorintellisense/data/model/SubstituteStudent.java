package com.sposnor.intellisense.sponsorintellisense.data.model;

import java.io.Serializable;

public class SubstituteStudent implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long sourceStudent;
	
	private Long targetStudent;
	
	private Long enrollentId;
	
	private String reason;
	
	
	
	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Long getEnrollentId() {
		return enrollentId;
	}

	public void setEnrollentId(Long enrollentId) {
		this.enrollentId = enrollentId;
	}

	public Long getSourceStudent() {
		return sourceStudent;
	}

	public void setSourceStudent(Long sourceStudent) {
		this.sourceStudent = sourceStudent;
	}

	public Long getTargetStudent() {
		return targetStudent;
	}

	public void setTargetStudent(Long targetStudent) {
		this.targetStudent = targetStudent;
	}
	
	

}
