package com.ucsandroid.profitable;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ucsandroid.profitable.entities.Location;
import com.ucsandroid.profitable.entities.LocationCategory;
import com.ucsandroid.profitable.entities.MenuItem;
import com.ucsandroid.profitable.service.AdditionsService;
import com.ucsandroid.profitable.service.MenuService;

@Path ("/serviceclass")
public class MainController {
	
	private MenuService menuService = new MenuService();
	private AdditionsService additionService = new AdditionsService();
	
	@Path ("/test")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayPlainTextHello() {
		return "<html> " + "<title>" + "ProfiTable" + "</title>"
				+ "<body><h1>" + "This is the controller for ProfiTable by UCSanDroid" 
				+ "</body></h1>" + "</html> ";
	}
	
	@Path ("/test5")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<MenuItem> test5() {
		System.out.println("TESTING!?");
		List<MenuItem> menu = new ArrayList<MenuItem>();
		MenuItem m1 = new MenuItem(0,"this","that",56524);
		MenuItem m2 = new MenuItem(1,"this1","that1",46564);
		MenuItem m3 = new MenuItem(2,"this2","that2",562);
		menu.add(m1); menu.add(m2); menu.add(m3);
		return menu;
	}
	
	@Path ("/test6")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String test6() {
		System.out.println("TESTING!?");
		List<Location> locs = new ArrayList<Location>();
		LocationCategory lc = new LocationCategory(90845, "the bar", 49875);
		Location l4 = new Location(1, "available", "seat for 2", 1, 1);
		Location l5 = new Location(4, "occupied", "barseat 2", 1, 1);
		locs.add(l4);
		locs.add(l5);
		lc.setLocations(locs);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		
		return gson.toJson(lc);
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
	@Path ("/menuItem")
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
	 * Returns menu for a given restaurant, with categories listed
	 * for all items.  organized by category.
	 * if available is not defined or provided, returns all items
	 * if available is defined as true or false, returns only
	 * those items matching the given availability
	 * @param restaurant - integer id
	 * @param available - true or false
	 * @return String menu
	 */
	@Path ("/menuCategories")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String fetchMenuWithCats(
			@QueryParam("available") String available, 
			@QueryParam("rest_id") String rest_id
			) {
		
		return menuService.MenuCategoriesGet(rest_id, 
			available);

	}
	
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
	@Path ("/additions")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String fetchAdditions(
			@QueryParam("menu_item_id") String menu_item_id, 
			@QueryParam("available") String available, 
			@QueryParam("rest_id") String rest_id
			) {
		return additionService.AttributeGet(menu_item_id, 
				available, rest_id); 
	}
	
	@Path ("/dbstatus")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String testDB() {
		System.out.println("testing DB");
		ProfiTableModel model = new ProfiTableModel();
		String result = "";
		try{
			result = "Successfully connected via: "+model.getConnection().getMetaData().getDriverName()+"";
		}
		catch(Exception e){result = "Error connecting to DB";
		}
		return result;
	}
	
	@GET
	@Path ("/getTags")
	@Produces ("application/json")
	public String getTags(){
		
		ProfiTableModel model = new ProfiTableModel();
		return null;
	}
	
}