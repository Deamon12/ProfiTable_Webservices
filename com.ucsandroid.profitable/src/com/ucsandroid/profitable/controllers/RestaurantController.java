package com.ucsandroid.profitable.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path ("/restaurant")
public class RestaurantController {
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String fetchRestaurantName(
			@QueryParam("rest_id") int rest_id
			) {
		
		String restName="";
		//quick n dirty for the demo
		if (rest_id==1){
			restName="The Carlito Diner";
		} else {
			restName="Turkish Pizza Parlour";
		}
		
		String jsonreturn = "{ \n   \"name\": \""+restName+"\" \n}";
		
		return jsonreturn; 
	}

}
