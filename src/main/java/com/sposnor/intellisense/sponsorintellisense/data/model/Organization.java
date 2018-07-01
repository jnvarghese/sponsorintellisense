package com.sposnor.intellisense.sponsorintellisense.data.model;

import java.io.Serializable;
import java.text.ParseException;
import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;


public class Organization implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private Long id;

	@NotBlank
	private String code;

	@NotBlank
	private String name;

	@NotBlank
	private String status;

	private Date createdDate;

	private Date updatedDate;
	
	private Long createdBy;
	
	private Long updatedBy;
	
	

	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code.toUpperCase();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	@Override
	public String toString() {
		return "Agency [id=" + id + ", code=" + code + ", name=" + name + ", status=" + status + ", getId()=" + getId()
				+ ", getCode()=" + getCode() + ", getName()=" + getName() + ", getStatus()=" + getStatus() + "]";
	}


}
