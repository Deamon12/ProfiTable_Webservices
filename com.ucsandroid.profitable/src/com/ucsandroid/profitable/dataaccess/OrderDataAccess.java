package com.ucsandroid.profitable.dataaccess;

public class OrderDataAccess extends MainDataAccess {
	
	private static OrderDataAccess orderDataAccess = 
			new OrderDataAccess();
	
	private OrderDataAccess() {super();}
	
	public static OrderDataAccess getInstance() {
		return orderDataAccess;
	}

}