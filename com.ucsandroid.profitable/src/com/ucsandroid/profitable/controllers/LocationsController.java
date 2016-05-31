package com.ucsandroid.profitable.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.ucsandroid.profitable.service.EmployeeService;
import com.ucsandroid.profitable.service.LocationsService;

@Path ("/location")
public class LocationsController {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String fetchLocations(
			@QueryParam("rest_id") int rest_id
			) {
		return LocationsService.getInstance().LocationsGet(rest_id); 
	}
	
	@Path ("/occupy")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public String occupyLocation(
			@QueryParam("location_id") int location_id
			) {
		String status = LocationsService.getInstance().updateLocationStatus(
				location_id, "occupied");
		EmployeeService.getInstance().updateWaitStaff(1, 1);
		return status;
	}
	
	@Path ("/free")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public String freeLocation(
			@QueryParam("location_id") int location_id
			) {
		String status = LocationsService.getInstance().updateLocationStatus(
				location_id, "available");
		EmployeeService.getInstance().updateWaitStaff(1, 1);
		return status;
	}

}