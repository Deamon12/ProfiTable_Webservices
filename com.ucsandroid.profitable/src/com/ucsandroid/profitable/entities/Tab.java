package com.ucsandroid.profitable.entities;

import java.util.Date;

public class Tab {
	
	private int tabId;
	private String tabStatus;
	private Date timeIn;
	private Date timeOut;
	
	public Tab(int id, String status, Date timeIn, Date timeOut) {
		super();
		this.tabId = id;
		this.tabStatus = status;
		this.timeIn = timeIn;
		this.timeOut = timeOut;
	}

	public Tab(int id, String status) {
		super();
		this.tabId = id;
		this.tabStatus = status;
	}
	public int getId() {return tabId;}
	public void setId(int id) {this.tabId = id;}
	public String getStatus() {return tabStatus;}
	public void setStatus(String status) {this.tabStatus = status;}
	public Date getTimeIn() {return timeIn;}
	public void setTimeIn(Date timeIn) {this.timeIn = timeIn;}
	public Date getTimeOut() {return timeOut;}
	public void setTimeOut(Date timeOut) {this.timeOut = timeOut;}

}