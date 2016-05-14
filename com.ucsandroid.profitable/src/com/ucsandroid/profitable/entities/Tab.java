package com.ucsandroid.profitable.entities;

import java.util.Date;

public class Tab {
	
	private int id;
	private String status;
	private Date timeIn;
	private Date timeOut;
	
	public Tab(int id, String status, Date timeIn, Date timeOut) {
		super();
		this.id = id;
		this.status = status;
		this.timeIn = timeIn;
		this.timeOut = timeOut;
	}

	public Tab(int id, String status) {
		super();
		this.id = id;
		this.status = status;
	}
	public int getId() {return id;}
	public void setId(int id) {this.id = id;}
	public String getStatus() {return status;}
	public void setStatus(String status) {this.status = status;}
	public Date getTimeIn() {return timeIn;}
	public void setTimeIn(Date timeIn) {this.timeIn = timeIn;}
	public Date getTimeOut() {return timeOut;}
	public void setTimeOut(Date timeOut) {this.timeOut = timeOut;}

}