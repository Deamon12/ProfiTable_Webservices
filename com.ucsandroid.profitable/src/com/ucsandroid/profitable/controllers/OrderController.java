package com.ucsandroid.profitable.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.ucsandroid.profitable.service.OrderService;

@Path ("/orders")
public class OrderController {
	
	//TODO - instead of returning unable to find for tables without an order, 
	//return a better message
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String fetchOrder(
			@QueryParam("location_id") int location_id,
			@QueryParam("rest_id") int rest_id
			) {
		return OrderService.getInstance().OrderGet(location_id, rest_id); 
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public String insertOrder(
			@QueryParam("location_id") int location_id,
			@QueryParam("employee_id") int employee_id
			) {
		return OrderService.getInstance().OrderPut(location_id,employee_id); 
	}

}