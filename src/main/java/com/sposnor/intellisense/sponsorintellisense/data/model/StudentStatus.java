package com.sposnor.intellisense.sponsorintellisense.data.model;

public class StudentStatus {

	private String status;
	
	private int count;
	
	private int unAssignedStudents;
	
	private int activeActiveStudents;
	
	private int activeInActiveStudents;
	
	private int inActiveActiveStudents;
	
	private int inActiveInActiveStudents;

	public int getUnAssignedStudents() {
		return unAssignedStudents;
	}

	public void setUnAssignedStudents(int unAssignedStudents) {
		this.unAssignedStudents = unAssignedStudents;
	}

	public int getActiveActiveStudents() {
		return activeActiveStudents;
	}

	public void setActiveActiveStudents(int activeActiveStudents) {
		this.activeActiveStudents = activeActiveStudents;
	}

	public int getActiveInActiveStudents() {
		return activeInActiveStudents;
	}

	public void setActiveInActiveStudents(int activeInActiveStudents) {
		this.activeInActiveStudents = activeInActiveStudents;
	}
	

	public int getInActiveActiveStudents() {
		return inActiveActiveStudents;
	}

	public void setInActiveActiveStudents(int inActiveActiveStudents) {
		this.inActiveActiveStudents = inActiveActiveStudents;
	}

	public int getInActiveInActiveStudents() {
		return inActiveInActiveStudents;
	}

	public void setInActiveInActiveStudents(int inActiveInActiveStudents) {
		this.inActiveInActiveStudents = inActiveInActiveStudents;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public StudentStatus(String status, int count) {
		super();
		this.status = status;
		this.count = count;
	}

	public StudentStatus(int unAssignedStudents, int activeActiveStudents, int activeInActiveStudents, int inActiveActiveStudents, int inActiveInActiveStudents) {
		super();
		this.unAssignedStudents = unAssignedStudents;
		this.activeActiveStudents = activeActiveStudents;
		this.activeInActiveStudents = activeInActiveStudents;
		this.inActiveActiveStudents = inActiveActiveStudents;
		this.inActiveInActiveStudents= inActiveInActiveStudents;
	}
	
	
	
}
