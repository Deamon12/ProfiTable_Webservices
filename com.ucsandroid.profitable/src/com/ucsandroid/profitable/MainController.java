package com.ucsandroid.profitable;

import java.util.Arrays;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ucsandroid.profitable.entities.FoodAddition;
import com.ucsandroid.profitable.entities.MenuItem;
import com.ucsandroid.profitable.utilities.ConnUtil;

@Path ("/serviceclass")
public class MainController {
	
	Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	@Path ("/test")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String sayPlainTextHello() {
		return "<html> " + "<title>" + "ProfiTable" + "</title>"
				+ "<body><h1>" + "This is the controller for ProfiTable by UCSanDroid" 
				+ "</body></h1>" + "</html> ";
	}
	
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
	
	@Path ("/dbstatus")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public String testDB() {
		System.out.println("testing DB");
		String result = "";
		ConnUtil testConn = new ConnUtil();
		try{
			result = "Successfully connected via: "+testConn.getConnection().getMetaData().getDriverName()+"";
		}
		catch(Exception e){result = "Error connecting to DB";
		}
		return result;
	}
	
}