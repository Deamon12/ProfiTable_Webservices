package com.ucsandroid.profitable;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.ucsandroid.profitable.service.AdditionsService;
import com.ucsandroid.profitable.service.CategoryService;
import com.ucsandroid.profitable.service.EmployeeService;
import com.ucsandroid.profitable.service.MenuService;
import com.ucsandroid.profitable.utilities.SecUtilities;

@Path ("/serviceclass")
public class MainController {
	
	private MenuService menuService = new MenuService();
	private AdditionsService additionService = new AdditionsService();
	private CategoryService categoryService = new CategoryService();
	private EmployeeService employeeService = new EmployeeService();
	
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
	
	@Path ("/employee")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String fetchEmployee(
			@QueryParam("account_name") String account_name, 
			@QueryParam("rest_id") String rest_id
			) {
		if (account_name!=null) {
			return employeeService.getEmployee(account_name, rest_id); 
		} else {
			return employeeService.getEmployees(rest_id); 
		}
	}
	
	@Path ("/login")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String employeeLogin(
			@QueryParam("account_name") String account_name, 
			@QueryParam("account_pass") String account_pass,
			@QueryParam("rest_id") String rest_id
			) {
		if (account_name!=null && account_pass!=null && rest_id!=null) {
			return employeeService.login(account_name, account_pass, rest_id); 
		} else {
			return "FAILURE"; 
		}
	}
	
	@Path ("/category")
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
	
	@Path ("/category")
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
	
	@Path ("/category")
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
		if (menu_item_id!=null) {
			return additionService.AttributeGet(menu_item_id, 
				available, rest_id); 
		} else {
			return additionService.AttributeGetRest( 
				available, rest_id); 
		}
	}

	
	@Path ("/additions")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	public String deleteAdditions(
			@QueryParam("attr_id") String attr_id,
			@QueryParam("rest_id") String rest_id
			) {

		if (attr_id!=null && rest_id!=null) {
			return additionService.AttributeDEL(attr_id,rest_id); 
		} else {
			return "FAILURE: DELETE requires attrib and rest ids";
		}
	}
	
	@Path ("/additions")
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
			return additionService.AttributePUT(attr_id, 
					name, price, available, rest_id); 
		} else {
			return "FAILURE: UPDATE requires all attributes assigned";
		}
	}
	
	@Path ("/additions")
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
			return additionService.AttributePOST(name, price, 
					available, rest_id); 
		} else {
			return "FAILURE: Insert requires all attributes assigned";
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
		
		//ProfiTableModel model = new ProfiTableModel();
		return null;
	}
	
}