package com.ucsandroid.profitable.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ucsandroid.profitable.dataaccess.OrderDataAccess;

public class OrderService {
	
	private OrderDataAccess orderDataAccess;
	
	private static OrderService orderService = 
			new OrderService();
	
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	private OrderService() {
		orderDataAccess = OrderDataAccess.getInstance();
	}
	
	public static OrderService getInstance() {
		if (orderService==null) {
			orderService = new OrderService(); 
		}
		return orderService;
	}
	
	private OrderDataAccess getOrderDataAccess() {
		if (orderDataAccess==null) {
			orderDataAccess = OrderDataAccess.getInstance();
		}
		return orderDataAccess;
	}

}