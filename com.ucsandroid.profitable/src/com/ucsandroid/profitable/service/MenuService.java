package com.ucsandroid.profitable.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ucsandroid.profitable.StandardResult;
import com.ucsandroid.profitable.dataaccess.MenuDataAccess;

public class MenuService {
	
	private MenuDataAccess menuDataAccess;
	
	private static MenuService menuService = 
			new MenuService();
	
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	private MenuService() {
		menuDataAccess = MenuDataAccess.getInstance();
	}
	
	public static MenuService getInstance() {
		if (menuService==null) {
			menuService = new MenuService(); 
		}
		return menuService;
	}
	
	private MenuDataAccess getMenuDataAccess() {
		if (menuDataAccess==null) {
			menuDataAccess = MenuDataAccess.getInstance();
		}
		return menuDataAccess;
	}
	
	
	public String MenuGetEntire(int restaurant) {
		StandardResult sr = new StandardResult(false, null);
		try {
			sr = getMenuDataAccess().getEntire(restaurant);
			return gson.toJson(sr);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			sr.setMessage("Error: invalid input: "+e.getMessage());
			return gson.toJson(sr);
		}
	}
	
	public String MenuItemGet(String restaurant, String menu_item_id,
			String avail, String category) {
		
		StandardResult sr = new StandardResult(false, null);
		try {
			Integer restVal = Integer.parseInt(restaurant);
			boolean availVal;
			Integer catVal;
			Integer menuIdVal;
			//CASE 1 - category is set, return all menu items from this category
			if (category!=null){
				catVal = Integer.parseInt(category);
				//if available is not null, attempt to use it to filter results
				if (avail!=null){
					availVal = Boolean.parseBoolean(avail);
					sr = getMenuDataAccess().getMenuItems(restVal, catVal, 
							availVal);
				} 
				//otherwise fetch all matching results
				else {
					sr = getMenuDataAccess().getMenuItems(restVal, catVal);
				}
			} 
			//CASE 2 - category not set, menu item id is set.  returns one menu item
			else if (menu_item_id!=null){
				menuIdVal = Integer.parseInt(menu_item_id);
				sr = getMenuDataAccess().getMenuItem(restVal, menuIdVal);
			} 
			//CASE 3 - no optional params set, return all menu items for restaurant
			else {
				//if available is not null, attempt to use it to filter results
				if (avail!=null){
					availVal = Boolean.parseBoolean(avail);
					sr = getMenuDataAccess().getMenuItems(restVal, availVal);
				}
				//otherwise fetch all matching results
				else {
					sr = getMenuDataAccess().getMenuItems(restVal);
				}
				
			}
			return gson.toJson(sr);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			sr.setMessage("Error: invalid input: "+e.getMessage());
			return gson.toJson(sr);
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
	
	//TODO
	public static String menuItemDelete(String menu_id, String rest_id) {
		return "";
	}

}