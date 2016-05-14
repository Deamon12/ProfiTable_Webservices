package com.ucsandroid.profitable.entities;

public class Customer {
	
	private int id;
	private int tabId;
	
	public Customer(int id, int tabId) {
		super();
		this.id = id;
		this.tabId = tabId;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getTabId() {
		return tabId;
	}
	public void setTabId(int tabId) {
		this.tabId = tabId;
	}
}