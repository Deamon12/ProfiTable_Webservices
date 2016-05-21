package com.ucsandroid.profitable.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.ucsandroid.profitable.service.LocationsService;

@Path ("/orders")
public class OrderController {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String fetchOrder(
			@QueryParam("location_id") int location_id
			) {
		return LocationsService.getInstance().LocationsGet(rest_id); 
	}

}
