package com.ucsandroid.profitable.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ucsandroid.profitable.StandardResult;
import com.ucsandroid.profitable.dataaccess.MenuDataAccess;
import com.ucsandroid.profitable.utilities.Converters;
import com.ucsandroid.profitable.utilities.StatementBuilder;

public class MenuService {
	
	private MenuDataAccess menuDataAccess;
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	public MenuService() {
		menuDataAccess = new MenuDataAccess();
	}
	
	/**
	 * TODO
	 * @param restaurant
	 * @param menu_item_id
	 * @param avail
	 * @param category
	 * @return
	 */
	public String MenuItemGet(String restaurant, String menu_item_id,
			String avail, String category) {
		
		String query = 
			"select "+
				"mi.menu_id, mi.menu_name, mi.description, "+
				"mi.price, mi.available, hc.cat_id "+
			"from "+
				"menu_item mi, "+
				"has_cat hc "+
			"where "+
				"mi.menu_id=hc.menu_id ";
		
		try {
			query = StatementBuilder.addBool(query, 
					"and mi.available=", avail);
			query = StatementBuilder.addInt(query, 
					"and mi.restaurant=", restaurant);
			query = StatementBuilder.addBool(query, 
					"and mi.menu_id=", menu_item_id);
			query = StatementBuilder.addInt(query, 
					"and hc.cat_id=", category);
			
			//log the query so we can analyze the sql generated
			System.out.println(query);
			
			return Converters.convertToString(
					getMenuDataAccess().fetchData(query));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "ERROR";
		}
		
	}
	
	/**
	 * Fetches a menu for the given restaurant sorted by category
	 * @param available - t/f show only available items or only unavailable items
	 * @param restaurant - the restaurant id
	 * @return
	 */
	public String MenuCategoriesGet(String restaurant, String avail) {
		
		StandardResult sr = new StandardResult(false, null);
		try {
			Integer restVal = Integer.parseInt(restaurant);
			boolean availVal;
			if (avail!=null){
				availVal = Boolean.parseBoolean(avail);
				//run with available mod
				sr = getMenuDataAccess().getMenuByCategory(restVal,availVal);
			} else {
				//run without
				sr = getMenuDataAccess().getMenuByCategory(restVal);
			}
			return gson.toJson(sr);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			sr.setMessage("Error: invalid input: "+e.getMessage());
			return gson.toJson(sr);
		}
	}
	
	public static String menuItemDelete(String menu_id, String rest_id) {
		return "";
	}

	/** returns a valid MenuDAO object */
	private MenuDataAccess getMenuDataAccess() {
		if (menuDataAccess==null) {menuDataAccess = new MenuDataAccess();}
		return menuDataAccess;
	}

}