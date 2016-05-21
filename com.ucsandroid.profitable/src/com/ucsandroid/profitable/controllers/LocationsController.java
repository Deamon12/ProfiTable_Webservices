package com.ucsandroid.profitable.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Path ("/location")
public class LocationsController {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String fetchLocations(
			@QueryParam("rest_id") int rest_id
			) {
		return employeeService.getEmployees(rest_id); 
		
	}

}
