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
			boolean available = false;
			int restId = 0;
			String menuId = "";
			String catId = "";
			
			if (avail!=null && avail.length()>0) {
				available = Boolean.valueOf(avail);
				query=query+"and mi.available="+available+" ";
			}
			
			if (restaurant!=null && restaurant.length()>0) {
				restId = Integer.parseInt(restaurant);
				query=query+"and mi.restaurant="+restId+" ";
			}
			
			if (menu_item_id!=null && menu_item_id.length()>0) {
				menuId = ""+Integer.parseInt(menu_item_id);
				query=query+"and mi.menu_id="+menuId+" ";
			}
			
			if (category!=null && category.length()>0) {
				catId = ""+Integer.parseInt(category);
				query=query+"and hc.cat_id="+catId+" ";
			}
			
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
			boolean available = false;
			int restId = 0;
			
			if (avail!=null && avail.length()>0) {
				available = Boolean.valueOf(avail);
				query=query+"and mi.available="+available+" ";
			}
			
			if (restaurant!=null && restaurant.length()>0) {
				restId = Integer.parseInt(restaurant);
				query=query+"and mi.restaurant="+restId+" ";
			}
			
			query=query+"ORDER BY category_name ASC";
			
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