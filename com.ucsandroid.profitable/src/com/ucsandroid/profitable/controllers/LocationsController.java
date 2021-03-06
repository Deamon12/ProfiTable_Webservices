package com.ucsandroid.profitable.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ucsandroid.profitable.StandardResult;
import com.ucsandroid.profitable.dataaccess.LocationsDataAccess;
import com.ucsandroid.profitable.entities.Location;
import com.ucsandroid.profitable.service.EmployeeService;
import com.ucsandroid.profitable.service.LocationsService;
import com.ucsandroid.profitable.service.OrderService;

@Path ("/location")
public class LocationsController {
	
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
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
		Location l = new Location();
		l.setId(location_id);
		l.setStatus("occupied");
		EmployeeService.getInstance().updateWaitStaff(1, 1, gson.toJson(l));
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
		StandardResult sr = LocationsDataAccess.getInstance().getLocationDetails(location_id);
		if (sr.getSuccess()){
			Location temp = (Location) sr.getResult();
			try {
				int order_id = temp.getCurrentTab().getTabId();
				
				if (order_id>0){
					System.out.println("Closing order: "+order_id+"\n"+
							OrderService.getInstance().
							closeTab(location_id, order_id));
				}
				
			}catch (Exception e) { 
				//swallow exception, if not correct shouldn't halt other progress}
				System.out.println(e.getMessage());
			}
		}
		Location l = new Location();
		l.setId(location_id);
		l.setStatus("available");
		EmployeeService.getInstance().updateWaitStaff(1, 1, gson.toJson(l));
		return status;
	}

}