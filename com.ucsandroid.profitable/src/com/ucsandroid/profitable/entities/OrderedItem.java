package com.ucsandroid.profitable.entities;

public class OrderedItem {
	
	private int id;
	private String notes;
	private String status;
	private boolean bringFirst;
	
	public OrderedItem(int id, String notes, String status, boolean bringFirst) {
		super();
		this.id = id;
		this.notes = notes;
		this.status = status;
		this.bringFirst = bringFirst;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public boolean isBringFirst() {
		return bringFirst;
	}
	public void setBringFirst(boolean bringFirst) {
		this.bringFirst = bringFirst;
	}
}