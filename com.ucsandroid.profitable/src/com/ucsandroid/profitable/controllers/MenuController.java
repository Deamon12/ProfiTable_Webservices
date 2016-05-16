package com.ucsandroid.profitable.controllers;

import java.util.Arrays;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ucsandroid.profitable.StandardResult;
import com.ucsandroid.profitable.entities.FoodAddition;
import com.ucsandroid.profitable.entities.MenuItem;
import com.ucsandroid.profitable.service.MenuService;

@Path ("/menu")
public class MenuController {
	
	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private MenuService menuService = new MenuService();
	
	@Path ("/test2")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getanObj() {
		System.out.println("test2");
		MenuItem mi = new MenuItem(13454,"Cheezeburgarz", "nom nom nom", 199);
		FoodAddition f1 = new FoodAddition("onions", 0, true, 17, 1);
		FoodAddition f2 = new FoodAddition("guacamole", 50, true, 73, 1);
		FoodAddition f3 = new FoodAddition("lettuce", 0, true, 7, 1);
		mi.setDefaultAdditions(Arrays.asList(f1,f3));
		mi.setOptionalAdditions(Arrays.asList(f2));
		System.out.println("made an item");
		StandardResult sr = new StandardResult(true, mi);
		return gson.toJson(sr);
	}
	
	/**
	 * Returns menu for a given restaurant
	 * if available is not defined or provided, returns all items
	 * if available is defined as true or false, returns only
	 * those items matching the given availability
	 * @param available - true or false
	 * @param restaurant - integer id
	 * @return String menu
	 */
	@Path ("/item")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String fetchMenu(
			@QueryParam("available") String available, 
			@QueryParam("restaurant") String rest_id,
			@QueryParam("menu_item") String menu_item_id,
			@QueryParam("category") String cat_id
			) {
		return menuService.MenuItemGet(rest_id, 
				menu_item_id, available, cat_id);
	}
	
	/**
	 * TODO
	 * @param attr_id
	 * @param rest_id
	 * @return
	 */
	@Path ("/item")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public String delete(
			@QueryParam("menu_id") String menu_id,
			@QueryParam("rest_id") String rest_id
			) {

		if (menu_id!=null && rest_id!=null) {
			return MenuService.menuItemDelete(menu_id,rest_id); 
		} else {
			StandardResult sr = new StandardResult(false, null);
			sr.setMessage("Error: not all parameters set");
			return gson.toJson(sr); 
		}
	}
	
	/**
	 * Returns menu for a given restaurant, with categories listed
	 * for all items.  organized by category.
	 * if available is not defined or provided, returns all items
	 * if available is defined as true or false, returns only
	 * those items matching the given availability
	 * @param restaurant - integer id
	 * @param available - true or false
	 * @return String menu
	 */
	@Path ("/categories")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String fetchMenuWithCats(
			@QueryParam("available") String available, 
			@QueryParam("rest_id") String rest_id
			) {
		return menuService.MenuCategoriesGet(rest_id, 
			available);
	}

}
