package com.sposnor.intellisense.sponsorintellisense.data.model;

import java.io.Serializable;
import java.util.Date;

public class SponsorMaxOut implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private int id;
	
	private int status;
	
	private Long sponsorId;
	
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

	

	public Long getSponsorId() {
		return sponsorId;
	}

	public void setSponsorId(Long sponsorId) {
		this.sponsorId = sponsorId;
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
	
	public SponsorMaxOut() {
		// TODO Auto-generated constructor stub
	}
	
	public SponsorMaxOut(Long sponsorId, Long enrollmentId, Date maxOut) {
		super();
		this.sponsorId = sponsorId;
		this.enrollmentId = enrollmentId;
		this.maxOut = maxOut;
	}

	public SponsorMaxOut(int id, int status, Long sponsorId, Long enrollmentId, Date maxOut) {
		super();
		this.id = id;
		this.status = status;
		this.sponsorId = sponsorId;
		this.enrollmentId = enrollmentId;
		this.maxOut = maxOut;
	}

	@Override
	public String toString() {
		return "SponsorMaxOut [id=" + id + ", status=" + status + ", sponsorId=" + sponsorId + ", enrollmentId="
				+ enrollmentId + ", maxOut=" + maxOut + "]";
	}

	

}
