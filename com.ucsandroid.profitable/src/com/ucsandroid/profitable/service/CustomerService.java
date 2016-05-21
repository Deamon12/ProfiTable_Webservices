package com.ucsandroid.profitable.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ucsandroid.profitable.dataaccess.CustomerDataAccess;

public class CustomerService {
	
	private CustomerDataAccess customerDataAccess;
	
	private static CustomerService customerService = 
			new CustomerService();
	
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	private CustomerService() {
		customerDataAccess = CustomerDataAccess.getInstance();
	}
	
	public static CustomerService getInstance() {
		if (customerService==null) {
			customerService = new CustomerService(); 
		}
		return customerService;
	}
	
	private CustomerDataAccess getCustomerDataAccess() {
		if (customerDataAccess==null) {
			customerDataAccess = CustomerDataAccess.getInstance();
		}
		return customerDataAccess;
	}

}
