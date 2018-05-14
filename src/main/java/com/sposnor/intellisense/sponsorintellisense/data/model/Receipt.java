package com.sposnor.intellisense.sponsorintellisense.data.model;

import java.util.Date;

public class Receipt {

	private Long id;
	
	private String receivedfrom;
	
	private String address;
	
	private String parish;
	
	private String missionname;
	
	private String total;
	
	private String paymentmethod;
	
	private String secretary;
	
	private String treasurer;
	
	private Long createdby;
	
	private String createddate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getReceivedfrom() {
		return receivedfrom;
	}

	public void setReceivedfrom(String receivedfrom) {
		this.receivedfrom = receivedfrom;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getParish() {
		return parish;
	}

	public void setParish(String parish) {
		this.parish = parish;
	}

	public String getMissionname() {
		return missionname;
	}

	public void setMissionname(String missionname) {
		this.missionname = missionname;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getPaymentmethod() {
		return paymentmethod;
	}

	public void setPaymentmethod(String paymentmethod) {
		this.paymentmethod = paymentmethod;
	}

	public String getSecretary() {
		return secretary;
	}

	public void setSecretary(String secretary) {
		this.secretary = secretary;
	}

	public String getTreasurer() {
		return treasurer;
	}

	public void setTreasurer(String treasurer) {
		this.treasurer = treasurer;
	}

	public Long getCreatedby() {
		return createdby;
	}

	public void setCreatedby(Long createdby) {
		this.createdby = createdby;
	}

	public String getCreateddate() {
		return createddate;
	}

	public void setCreateddate(String createddate) {
		this.createddate = createddate;
	}
	
	
	
}
