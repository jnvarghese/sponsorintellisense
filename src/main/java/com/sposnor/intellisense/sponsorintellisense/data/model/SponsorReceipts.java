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
	
	private double amount;
	
	private Long status;
	
	private Long createdBy;
	
	
	
	public Long getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(Long createdBy) {
		this.createdBy = createdBy;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

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

	public SponsorReceipts(Long sponsorId, Long receiptId, Long createdBy) {
		super();
		this.sponsorId = sponsorId;
		this.receiptId = receiptId;
		this.createdBy = createdBy;
	}

	public SponsorReceipts(Long sponsorId, Long receiptId, double amount, Long createdBy) {
		super();
		this.sponsorId = sponsorId;
		this.receiptId = receiptId;
		this.amount = amount;
		this.createdBy = createdBy;
	}
	
	
	
	

}
