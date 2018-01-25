package com.sposnor.intellisense.sponsorintellisense.data.model;

import java.io.Serializable;

public class ParishProject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long parishId;
	
	private Long projectId;
	
	private String status;

	public Long getParishId() {
		return parishId;
	}

	public void setParishId(Long parishId) {
		this.parishId = parishId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
