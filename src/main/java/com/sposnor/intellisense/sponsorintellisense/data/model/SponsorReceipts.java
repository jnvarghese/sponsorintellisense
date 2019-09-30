package com.sposnor.intellisense.sponsorintellisense.data.model;

import java.io.Serializable;

public class SponsorReceipts implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Long sponsorId;
	
	private Long receiptId;
	
	private Long status;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSponsorId() {
		return sponsorId;
	}

	public void setSponsorId(Long sponsorId) {
		this.sponsorId = sponsorId;
	}

	public Long getReceiptId() {
		return receiptId;
	}

	public void setReceiptId(Long receiptId) {
		this.receiptId = receiptId;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public SponsorReceipts(Long sponsorId, Long receiptId) {
		super();
		this.sponsorId = sponsorId;
		this.receiptId = receiptId;
	}
	
	

}
