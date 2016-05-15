package com.ucsandroid.profitable.controllers;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ucsandroid.profitable.StandardResult;
import com.ucsandroid.profitable.service.AdditionsService;

@Path ("/additions")
public class AdditionsController {
	
	private AdditionsService additionService = new AdditionsService();
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	/**
	 * Returns all potential additions for a given menu item at a given restaurant
	 * if available is not defined or provided, returns all items
	 * if available is defined as true or false, returns only
	 * those items matching the given availability
	 * @param menu_id - the menu_id of the item to check for additions on
	 * @param available - true or false
	 * @param restaurant - integer id
	 * @return
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String fetchAdditions(
			@QueryParam("menu_item_id") String menu_item_id, 
			@QueryParam("available") String available, 
			@QueryParam("rest_id") String rest_id
			) {
		if (menu_item_id!=null) {
			return additionService.AttributeGet(menu_item_id, 
				available, rest_id); 
		} else {
			return additionService.AttributeGetRest( 
				available, rest_id); 
		}
	}

	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteAdditions(
			@QueryParam("attr_id") String attr_id,
			@QueryParam("rest_id") String rest_id
			) {

		if (attr_id!=null && rest_id!=null) {
			return additionService.attributeDelete(attr_id,rest_id); 
		} else {
			StandardResult sr = new StandardResult(false, null);
			sr.setMessage("Error: not all parameters set");
			return gson.toJson(sr); 
		}
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public String updateAdditions(
			@QueryParam("attr_id") String attr_id, 
			@QueryParam("name") String name, 
			@QueryParam("available") String available, 
			@QueryParam("price") String price,
			@QueryParam("rest_id") String rest_id
			) {

		if (name!=null && available!=null && attr_id!=null
				&& price!=null && rest_id!=null) {
			return additionService.attributeUpdate(attr_id, 
					name, price, available, rest_id); 
		} else {
			StandardResult sr = new StandardResult(false, null);
			sr.setMessage("Error: not all parameters set");
			return gson.toJson(sr); 
		}
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String addAdditions(
			@QueryParam("name") String name, 
			@QueryParam("available") String available, 
			@QueryParam("price") String price,
			@QueryParam("rest_id") String rest_id
			) {

		if (name!=null && available!=null && 
				price!=null && rest_id!=null) {
			return additionService.attributeInsert(name, price, 
					available, rest_id); 
		} else {
			StandardResult sr = new StandardResult(false, null);
			sr.setMessage("Error: not all parameters set");
			return gson.toJson(sr); 
		}
	}

}