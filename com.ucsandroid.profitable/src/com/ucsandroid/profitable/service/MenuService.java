package com.ucsandroid.profitable.service;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.json.JSONArray;

import com.ucsandroid.profitable.dataaccess.MenuDAO;
import com.ucsandroid.profitable.entities.MenuItem;
import com.ucsandroid.profitable.utilities.Converters;

public class MenuService {
	
	private MenuDAO menuDAO;
	
	public MenuService() {
		menuDAO = new MenuDAO();
	}
	
	/**
	 * Fetches all menu items for a given restaurant
	 * @param available - t/f show only available items or only unavailable items
	 * @param restaurant - the restaurant id
	 * @return
	 */
	public String fetchMenu(boolean available, int restaurant) {
		String query = 
				"select menu_id, menu_name, description, price, available "+
				"from menu_item where available="+available+" and restaurant="+restaurant;
		
		return Converters.convertToString(getMenuDAO().fetchData(query));
	}
	
	/**
	 * Fetches all menu items for a given restaurant
	 * NOTE: Returns both available and unavailable items
	 * @param restaurant - the restaurant id
	 * @return
	 */
	public String fetchMenu(int restaurant) {
		String query = 
				"select menu_id, menu_name, description, price, available "+
				"from menu_item where restaurant="+restaurant;
		
		return Converters.convertToString(getMenuDAO().fetchData(query));
	}
	
	/**
	 * Fetches a menu for the given restaurant sorted by category
	 * @param available - t/f show only available items or only unavailable items
	 * @param restaurant - the restaurant id
	 * @return
	 */
	public String fetchMenuWithCats(boolean available, int restaurant) {
		String query = 
				"SELECT "+
						"c.cat_name as category_name, c.cat_id as cat_id, "+
						"mi.menu_name as menu_name, mi.menu_id as menu_id "+
					"FROM "+
						"has_cat hs, "+
						"category c, "+
						"menu_item mi "+
					"WHERE "+
						"mi.menu_id=hs.menu_id "+
						"AND "+
						"c.cat_id=hs.cat_id "+
						"AND "+
						"mi.available="+available+" "+
						"AND "+
						"mi.restaurant="+restaurant+" "+
					"ORDER BY  "+
						"category_name ASC";
		
		return Converters.convertToString(getMenuDAO().fetchData(query));
	}
	
	/**
	 * Fetches a menu for the given restaurant sorted by category
	 * NOTE: Returns both available and unavailable items
	 * @param restaurant - the restaurant id
	 * @return
	 */
	public String fetchMenuWithCats(int restaurant) {
		String query = 
				"SELECT "+
						"c.cat_name as category_name, c.cat_id as cat_id, "+
						"mi.menu_name as menu_name, mi.menu_id as menu_id, "+
						"mi.available as available "+
					"FROM "+
						"has_cat hs, "+
						"category c, "+
						"menu_item mi "+
					"WHERE "+
						"mi.menu_id=hs.menu_id "+
						"AND "+
						"c.cat_id=hs.cat_id "+
						"AND "+
						"mi.restaurant="+restaurant+" "+
					"ORDER BY  "+
						"category_name ASC";
		
		return Converters.convertToString(getMenuDAO().fetchData(query));
	}
	
	/**
	 * Fetches a list of potential additions for a given menu item
	 * @param menu_id - the identity of the menu item to search for
	 * @param available - t/f show only available items or only unavailable items
	 * @param restaurant - the restaurant id
	 * @return
	 */
	public String fetchAdditions(int menu_id, boolean available, int restaurant) {
		String query = 
			"SELECT "+
				"fa.attribute, fa.attr_id, price_mod, ha.default_incl "+
			"FROM "+
				"has_attr ha, "+
				"food_attribute fa, "+
				"menu_item mi "+
			"WHERE "+
				"mi.menu_id="+menu_id+" AND "+
				"mi.menu_id=ha.menu_id AND "+
				"fa.attr_id=ha.attr_id AND "+
				"fa.available="+available+" AND "+
				"mi.restaurant="+restaurant+" "+
			"ORDER BY "+
				"ha.default_incl DESC ";
		
		return Converters.convertToString(getMenuDAO().fetchData(query));
	}
	
	/**
	 * Fetches a list of potential additions for a given menu item
	 * NOTE: Returns both available and unavailable items
	 * @param menu_id - the identity of the menu item to search for
	 * @param restaurant - the restaurant id
	 * @return
	 */
	public String fetchAdditions(int menu_id, int restaurant) {
		String query = 
			"SELECT "+
				"fa.attribute, fa.attr_id, price_mod, "+
				"fa.available, ha.default_incl "+
			"FROM "+
				"has_attr ha, "+
				"food_attribute fa, "+
				"menu_item mi "+
			"WHERE "+
				"mi.menu_id="+menu_id+" AND "+
				"mi.menu_id=ha.menu_id AND "+
				"fa.attr_id=ha.attr_id AND "+
				"mi.restaurant="+restaurant+" "+
			"ORDER BY "+
				"ha.default_incl DESC ";
		
		return Converters.convertToString(getMenuDAO().fetchData(query));
	}
	
	
	
	
	
	
	
	/** returns a valid MenuDAO object */
	private MenuDAO getMenuDAO() {
		if (menuDAO==null) {menuDAO = new MenuDAO();}
		return menuDAO;
	}

}