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

	public String OrderGet(int loc_id, int rest_id) {
		return gson.toJson(getOrderDataAccess().
				getOrder(loc_id, rest_id));
	}
	
	/**
	 * TODO - not yet implemented
	 * @param location_id
	 * @param employee_id
	 * @return
	 */
	public String OrderPut(int location_id, int employee_id) {
		//create a new order, insert it into db
		
		//update location
		
		//create relational entry for employee/location/order
		
		//return status
		
		return "TODO - not yet implemented";
	}
	
}