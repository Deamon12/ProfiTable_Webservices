package com.ucsandroid.profitable.entities;

public class Customer {
	
	private int customerId;
	private int tabId;
	
	public Customer(int id, int tabId) {
		super();
		this.customerId = id;
		this.tabId = tabId;
	}
	
	public int getId() {
		return customerId;
	}
	public void setId(int id) {
		this.customerId = id;
	}
	public int getTabId() {
		return tabId;
	}
	public void setTabId(int tabId) {
		this.tabId = tabId;
	}
}