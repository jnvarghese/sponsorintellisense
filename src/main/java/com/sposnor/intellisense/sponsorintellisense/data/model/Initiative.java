package com.sposnor.intellisense.sponsorintellisense.data.model;

public class Initiative {

	private Long initiativeId;
	
	private Long id;
	
	private String name;
	
	private String code;
	
	private Long parentId;

	private String initiativeName;
	
	private double amount;
	

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Long getInitiativeId() {
		return initiativeId;
	}

	public void setInitiativeId(Long initiativeId) {
		this.initiativeId = initiativeId;
	}

	public String getInitiativeName() {
		return initiativeName;
	}

	public void setInitiativeName(String initiativeName) {
		this.initiativeName = initiativeName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	
}
