package com.sposnor.intellisense.sponsorintellisense.data.model;

import java.io.Serializable;
import java.util.List;

public class SponsorReceipts implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Long sponsorId;
	
	private Long receiptId;
	
	private int noOfRenewal;
	
	private double amount;
	
	private Long status;
	
	private Long createdBy;
	
	private Long updatedBy;
	
	private double receiptAmount;
	
	private double sponsorReceiptAmount;
	
	private String type;
	
	/** Receipt data **/
	
	private String rdate;
	
	private String transaction;
	
	private int receiptType;

	private Long parishId;

	private List<StudentExtendedMonth> months;
	
	
	public List<StudentExtendedMonth> getMonths() {
		return months;
	}

	public void setMonths(List<StudentExtendedMonth> months) {
		this.months = months;
	}

	public Long getParishId() {
		return parishId;
	}

	public void setParishId(Long parishId) {
		this.parishId = parishId;
	}

	public int getNoOfRenewal() {
		return noOfRenewal;
	}

	public void setNoOfRenewal(int noOfRenewal) {
		this.noOfRenewal = noOfRenewal;
	}

	public String getRdate() {
		return rdate;
	}

	public void setRdate(String rdate) {
		this.rdate = rdate;
	}

	public String getTransaction() {
		return transaction;
	}

	public void setTransaction(String transaction) {
		this.transaction = transaction;
	}

	public int getReceiptType() {
		return receiptType;
	}

	public void setReceiptType(int receiptType) {
		this.receiptType = receiptType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public double getReceiptAmount() {
		return receiptAmount;
	}

	public void setReceiptAmount(double receiptAmount) {
		this.receiptAmount = receiptAmount;
	}

	public double getSponsorReceiptAmount() {
		return sponsorReceiptAmount;
	}

	public void setSponsorReceiptAmount(double sponsorReceiptAmount) {
		this.sponsorReceiptAmount = sponsorReceiptAmount;
	}

	public Long getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(Long updatedBy) {
		this.updatedBy = updatedBy;
	}

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

	public SponsorReceipts() {
		// TODO Auto-generated constructor stub
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
	
	public SponsorReceipts(Long sponsorId, Long receiptId, double amount, Long createdBy, String type) {
		super();
		this.sponsorId = sponsorId;
		this.receiptId = receiptId;
		this.amount = amount;
		this.createdBy = createdBy;
		this.type = type;
	}
	
	public SponsorReceipts(Long sponsorId, Long receiptId, double amount, Long createdBy, int type, int noOfRenewal) {
		super();
		this.sponsorId = sponsorId;
		this.receiptId = receiptId;
		this.amount = amount;
		this.createdBy = createdBy;
		this.noOfRenewal = noOfRenewal;
		if(type == 2) {
			this.type = "I";
		} else if(type == 1) {
			this.type = "O";
		} else if(type == 0) {
			this.type = "P";
		} else {
			this.type = "E";
		}
	}

	@Override
	public String toString() {
		return "SponsorReceipts [id=" + id + ", sponsorId=" + sponsorId + ", receiptId=" + receiptId + ", noOfRenewal="
				+ noOfRenewal + ", amount=" + amount + ", status=" + status + ", createdBy=" + createdBy
				+ ", updatedBy=" + updatedBy + ", receiptAmount=" + receiptAmount + ", sponsorReceiptAmount="
				+ sponsorReceiptAmount + ", type=" + type + ", rdate=" + rdate + ", transaction=" + transaction
				+ ", receiptType=" + receiptType + ", parishId=" + parishId + ", months=" + months + "]";
	}

	

	 

}
