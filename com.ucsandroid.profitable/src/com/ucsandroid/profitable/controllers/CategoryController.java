package com.ucsandroid.profitable.controllers;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ucsandroid.profitable.StandardResult;
import com.ucsandroid.profitable.service.CategoryService;
import com.ucsandroid.profitable.utilities.SecUtilities;

@Path ("/category")
public class CategoryController {
	
	private CategoryService categoryService = new CategoryService();
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteCategory(
			@QueryParam("cat_id") String cat_id,
			@QueryParam("rest_id") String rest_id
			) {
		if (cat_id!=null && rest_id!=null) {
			return categoryService.delete(cat_id, rest_id); 
		} else {
			StandardResult sr = new StandardResult(false, null);
			sr.setMessage("Error: not all parameters set");
			return gson.toJson(sr); 
		}
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public String updateCategory(
			@QueryParam("cat_id") String cat_id, 
			@QueryParam("cat_name") String cat_name, 
			@QueryParam("rest_id") String rest_id
			) {
		if (cat_name!=null &&  cat_id!=null && rest_id!=null) {
			return categoryService.update(cat_id, cat_name, rest_id); 
		} else {
			StandardResult sr = new StandardResult(false, null);
			sr.setMessage("Error: not all parameters set");
			return gson.toJson(sr); 
		}
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String addCategory(
			@QueryParam("cat_name") String cat_name, 
			@QueryParam("rest_id") String rest_id
			) {
		if (cat_name!=null && rest_id!=null) {
			return categoryService.insert(cat_name, rest_id); 
		} else {
			StandardResult sr = new StandardResult(false, null);
			sr.setMessage("Error: not all parameters set");
			return gson.toJson(sr); 
		}
	}
}