package com.ucsandroid.profitable;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

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
	public MenuItem test5() {
		System.out.println("TESTING!?");
		return new MenuItem(0,"this","that",0);
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