package com.sposnor.intellisense.sponsorintellisense.data.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.validator.constraints.NotBlank;

public class Enrollment  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	
	private Set<Sponsee> sponsees  = new HashSet<>();
	
	@NotBlank
	private String paymentDate;
	@NotBlank
	private String effectiveDate;
	@NotBlank
	private double contributionAmount;
	
	private double miscAmount;
	
	private Long sponsorId;
	
	private Date createdDate;

	private Date updatedDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Sponsee> getSponsees() {
		return sponsees;
	}

	public void setSponsees(Set<Sponsee> sponsees) {
		this.sponsees = sponsees;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public double getContributionAmount() {
		return contributionAmount;
	}

	public void setContributionAmount(double contributionAmount) {
		this.contributionAmount = contributionAmount;
	}

	public double getMiscAmount() {
		return miscAmount;
	}

	public void setMiscAmount(double miscAmount) {
		this.miscAmount = miscAmount;
	}

	public Long getSponsorId() {
		return sponsorId;
	}

	public void setSponsorId(Long sponsorId) {
		this.sponsorId = sponsorId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(Date updatedDate) {
		this.updatedDate = updatedDate;
	}

	@Override
	public String toString() {
		return "Enrollment [id=" + id + ", sponsees=" + sponsees + ", paymentDate=" + paymentDate + ", effectiveDate="
				+ effectiveDate + ", contributionAmount=" + contributionAmount + ", miscAmount=" + miscAmount
				+ ", sponsorId=" + sponsorId + "]";
	}

}
