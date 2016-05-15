package com.ucsandroid.profitable.controllers;

import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.ucsandroid.profitable.service.CategoryService;
import com.ucsandroid.profitable.utilities.SecUtilities;

@Path ("/category")
public class CategoryController {
	
	private CategoryService categoryService = new CategoryService();
	
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteCategory(
			@QueryParam("cat_id") String cat_id,
			@QueryParam("rest_id") String rest_id
			) {
		
		String psw = SecUtilities.passwordHashSHA256("password");
		System.out.println("password hashes to: "+psw);
		
		if (cat_id!=null && rest_id!=null) {
			return categoryService.delete(cat_id, rest_id); 
		} else {
			return "FAILURE: DELETE requires attrib and rest ids";
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
			return "FAILURE: UPDATE requires all attributes assigned";
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
			return "FAILURE: Insert requires all attributes assigned";
		}
	}

}
