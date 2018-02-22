package com.sposnor.intellisense.sponsorintellisense.data.model;

import java.io.Serializable;
import java.util.Date;

public class SponsorMaxOut implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long sponsorId;
	
	private Long enrollmentId;
	
	private Date maxOut;
	
	

	public SponsorMaxOut(Long sponsorId, Long enrollmentId, Date maxOut) {
		super();
		this.sponsorId = sponsorId;
		this.enrollmentId = enrollmentId;
		this.maxOut = maxOut;
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

	@Override
	public String toString() {
		return "SponsorMaxOut [sponsorId=" + sponsorId + ", enrollmentId=" + enrollmentId + ", max_out="
				+ maxOut + "]";
	}
	

}
