package com.ucsandroid.profitable.entities;

import java.util.Date;

public class Tab {
	
	private int tabId;
	private String tabStatus;
	private Date timeIn;
	private Date timeOut;
	private Discount discount;
	
	public Tab(int id, String status, Date timeIn, Date timeOut) {
		super();
		this.tabId = id;
		this.tabStatus = status;
		this.timeIn = timeIn;
		this.timeOut = timeOut;
	}
	
	public Tab(int tabId, String tabStatus, Discount discount) {
		super();
		this.tabId = tabId;
		this.tabStatus = tabStatus;
		this.discount = discount;
	}

	public Tab(int tabId, String tabStatus, Date timeIn, Date timeOut, 
Discount discount) {
		super();
		this.tabId = tabId;
		this.tabStatus = tabStatus;
		this.timeIn = timeIn;
		this.timeOut = timeOut;
		this.discount = discount;
	}

	public Tab(int id, String status) {
		super();
		this.tabId = id;
		this.tabStatus = status;
	}

	public Tab() {
		super();
	}

	public Date getTimeIn() {return timeIn;}
	public void setTimeIn(Date timeIn) {this.timeIn = timeIn;}
	public Date getTimeOut() {return timeOut;}
	public void setTimeOut(Date timeOut) {this.timeOut = timeOut;}

	public int getTabId() {
		return tabId;
	}

	public void setTabId(int tabId) {
		this.tabId = tabId;
	}

	public String getTabStatus() {
		return tabStatus;
	}

	public void setTabStatus(String tabStatus) {
		this.tabStatus = tabStatus;
	}

	public Discount getDiscount() {
		return discount;
	}

	public void setDiscount(Discount discount) {
		this.discount = discount;
	}
	

}