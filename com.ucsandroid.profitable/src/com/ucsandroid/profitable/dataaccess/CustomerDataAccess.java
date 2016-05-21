package com.ucsandroid.profitable.dataaccess;

public class CustomerDataAccess extends MainDataAccess {
	
	private static CustomerDataAccess customerDataAccess = 
			new CustomerDataAccess();
	
	private CustomerDataAccess() {super();}
	
	public static CustomerDataAccess getInstance() {
		return customerDataAccess;
	}

}