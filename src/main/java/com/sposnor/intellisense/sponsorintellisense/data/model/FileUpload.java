package com.sposnor.intellisense.sponsorintellisense.data.model;

public class FileUpload {
	
	private Long id;
	
	private Long userId;
	
	private Long referenceId;

	private String fileName;
	
	private byte[] fileData;
	
	private String status;
	
	private String username;
	
	private String user;
	
	private String type;

	private String uploaduri;
	
	
	public String getUploaduri() {
		return uploaduri;
	}

	public void setUploaduri(String uploaduri) {
		this.uploaduri = uploaduri;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getFileData() {
		return fileData;
	}

	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getReferenceId() {
		return referenceId;
	}

	public void setReferenceId(Long referenceId) {
		this.referenceId = referenceId;
	}


	
}
