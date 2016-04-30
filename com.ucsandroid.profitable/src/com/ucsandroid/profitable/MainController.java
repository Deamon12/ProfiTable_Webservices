package com.ucsandroid.profitable;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import org.json.JSONArray;

import com.ucsandroid.profitable.dataaccess.MenuDAO;
import com.ucsandroid.profitable.entities.MenuItem;
import com.ucsandroid.profitable.service.MenuService;

import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path ("/serviceclass")
public class MainController {
	
	private MenuService menuService = new MenuService();
	
	
	@Path ("/test")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayPlainTextHello() {
		return "<html> " + "<title>" + "ProfiTable" + "</title>"
				+ "<body><h1>" + "This is the controller for ProfiTable by UCSanDroid" 
				+ "</body></h1>" + "</html> ";
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
	@Path ("/Menu")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String fetchMenu(
			@QueryParam("available") String available, 
			@QueryParam("restaurant") String restaurant
			) {
		try {
			
		int restId = Integer.parseInt(restaurant);
		
		if (available!=null && available.length()>0) {
			boolean avail = Boolean.valueOf(available);
			return menuService.fetchMenu(avail, restId); 
			}
		else {return menuService.fetchMenu(restId);}
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "ERROR";
		}
	}
	
	/**
	 * Returns menu for a given restaurant, with categories listed
	 * for all items.  organized by category.
	 * if available is not defined or provided, returns all items
	 * if available is defined as true or false, returns only
	 * those items matching the given availability
	 * @param available - true or false
	 * @param restaurant - integer id
	 * @return String menu
	 */
	@Path ("/MenuCategories")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String fetchMenuWithCats(
			@QueryParam("available") String available, 
			@QueryParam("restaurant") String restaurant
			) {
		try {
			
		int restId = Integer.parseInt(restaurant);
		
		if (available!=null && available.length()>0) {
			boolean avail = Boolean.valueOf(available);
			return menuService.fetchMenuWithCats(avail, restId); 
			}
		else {return menuService.fetchMenuWithCats(restId);}
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "ERROR";
		}
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
	@Path ("/MenuAdditions")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String fetchAdditions(
			@QueryParam("menu_id") String menu_id, 
			@QueryParam("available") String available, 
			@QueryParam("restaurant") String restaurant
			) {
		try {
			
		int restId = Integer.parseInt(restaurant);
		int mId = Integer.parseInt(menu_id);
		
		if (available!=null && available.length()>0) {
			boolean avail = Boolean.valueOf(available);
			return menuService.fetchAdditions(mId, avail, restId); 
			}
		else {return menuService.fetchAdditions(mId, restId);}
		
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "ERROR";
		}
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
	
	@GET
	@Path ("/getRateList")
	@Produces ("application/json")
	public String getRateList(
			@QueryParam("userId")String userId){
		
		ProfiTableModel model = new ProfiTableModel();
		return null;
	}
	
	@GET
	@Path("/createUser")
	@Produces("application/json")
	public String createUser(
			@QueryParam("firstName") String firstName, 
			@QueryParam("lastName") String lastName,
			@QueryParam("password") String password, 
			@QueryParam("email") String email, 
			@QueryParam("image") String image,
			@QueryParam("deviceId") String deviceId){
		
		ProfiTableModel model = new ProfiTableModel();
		return null;
	}
	

}