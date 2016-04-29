package com.ucsandroid.profitable;

import javax.ws.rs.GET;
import org.json.JSONArray;

import com.ucsandroid.profitable.dataaccess.MenuDAO;
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
	 * Simple initial menu fetch test functionality
	 * @return
	 */
	@Path ("/getMenu")
	@GET
	@Produces("application/json")
	public JSONArray fetchMenu() {
		return menuService.fetchMenu();
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
		catch(Exception e){
			result = "Error connecting to DB";
		}
		
		return result;
	}
	
	@GET
	@Path ("/getTags")
	@Produces ("application/json")
	public String getTags(){
		
		ProfiTableModel model = new ProfiTableModel();
		//return new Gson().toJson(model.getTags());
		return null;
	}
	
	@GET
	@Path ("/getRateList")
	@Produces ("application/json")
	public String getRateList(
			@QueryParam("userId")String userId){
		
		ProfiTableModel model = new ProfiTableModel();
		//return new Gson().toJson(model.getRateList(userId));
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
		//return new Gson().toJson(model.createUser(firstName, lastName, password, email, image, deviceId));
		return null;
	}
	

}