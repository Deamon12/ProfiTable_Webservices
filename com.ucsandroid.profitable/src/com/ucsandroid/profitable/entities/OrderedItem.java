package com.ucsandroid.profitable.entities;

public class OrderedItem {
	
	private int orderedItemId;
	private String orderedItemNotes;
	private String orderedItemStatus;
	private boolean bringFirst;
	
	public OrderedItem(int id, String notes, String status, boolean bringFirst) {
		super();
		this.orderedItemId = id;
		this.orderedItemNotes = notes;
		this.orderedItemStatus = status;
		this.bringFirst = bringFirst;
	}

	public int getId() {
		return orderedItemId;
	}
	public void setId(int id) {
		this.orderedItemId = id;
	}
	public String getNotes() {
		return orderedItemNotes;
	}
	public void setNotes(String notes) {
		this.orderedItemNotes = notes;
	}
	public String getStatus() {
		return orderedItemStatus;
	}
	public void setStatus(String status) {
		this.orderedItemStatus = status;
	}
	public boolean isBringFirst() {
		return bringFirst;
	}
	public void setBringFirst(boolean bringFirst) {
		this.bringFirst = bringFirst;
	}
}