package com.ucsandroid.profitable.service;

import com.ucsandroid.profitable.dataaccess.MenuDAO;
import com.ucsandroid.profitable.utilities.Converters;
import com.ucsandroid.profitable.utilities.StatementBuilder;

public class MenuService {
	
	private MenuDAO menuDAO;
	
	public MenuService() {
		menuDAO = new MenuDAO();
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
					getMenuDAO().fetchData(query));
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
				"and c.cat_id=hs.cat_id ";
		
		try {
			query = StatementBuilder.addBool(query, 
					"and mi.available=", avail);
			query = StatementBuilder.addInt(query, 
					"and mi.restaurant=", restaurant);
			query=query+"ORDER BY category_name ASC";
			
			//log the query so we can analyze the sql generated
			System.out.println(query);
			
			return Converters.convertToString(
					getMenuDAO().fetchData(query));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "ERROR";
		}
		
	}

	/** returns a valid MenuDAO object */
	private MenuDAO getMenuDAO() {
		if (menuDAO==null) {menuDAO = new MenuDAO();}
		return menuDAO;
	}

}