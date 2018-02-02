package com.sposnor.intellisense.sponsorintellisense.data.model;

import java.io.Serializable;

public class ParishProject implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long parishId;
	
	private Long projectId;
	
	private String code;
	
	private String name;
	
	private boolean selected;
	
	private String status;

	
	public boolean isselected() {
		return selected;
	}

	public void setselected(int selected) {
		if(selected == 1) {
			this.selected = true;
		}else {
			this.selected = false;
		}
	}

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "ParishProject [parishId=" + parishId + ", projectId=" + projectId + ", code=" + code + ", name=" + name
				+ ", selected=" + selected + ", status=" + status + "]";
	}

}
