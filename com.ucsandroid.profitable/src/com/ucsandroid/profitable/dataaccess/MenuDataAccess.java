package com.ucsandroid.profitable.dataaccess;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ucsandroid.profitable.StandardResult;
import com.ucsandroid.profitable.entities.Category;
import com.ucsandroid.profitable.entities.FoodAddition;
import com.ucsandroid.profitable.entities.MenuItem;

public class MenuDataAccess extends MainDataAccess {
	
	private static String getByCategoryAvail = 
		"SELECT "+
			"c.cat_name as category_name, c.cat_id as cat_id, "+
			"mi.menu_name as menu_name, mi.menu_id as menu_id, "+
			"mi.available as available, mi.price as price "+
		"FROM "+
			"has_cat hs, "+
			"category c, "+
			"menu_item mi "+
		"WHERE "+
			"mi.menu_id=hs.menu_id "+
			"and c.cat_id=hs.cat_id "+
			"and mi.restaurant = ? "+
			"and mi.available = ? "+
			"ORDER BY category_name ASC";
	
	private static String getByCategory = 
		"SELECT "+
			"c.cat_name as category_name, c.cat_id as cat_id, "+
			"mi.menu_name as menu_name, mi.menu_id as menu_id, "+
			"mi.available as available, mi.price as price "+
		"FROM "+
			"has_cat hs, "+
			"category c, "+
			"menu_item mi "+
		"WHERE "+
			"mi.menu_id=hs.menu_id "+
			"and c.cat_id=hs.cat_id "+
			"and mi.restaurant = ? "+
			"ORDER BY category_name ASC";
	
	public MenuDataAccess() {
		super();
	}
	
	public StandardResult getMenuByCategory(int restaurant, boolean avail) {
		StandardResult sr = new StandardResult(false, null);
		ResultSet results = null;
		
		try {
			// Open the connection
			conn = connUtil.getConnection();
	        // Create the prepared statement
	        pstmt = conn.prepareStatement(getByCategoryAvail);
	        // Set the variable parameters
	        int i = 1;
	        pstmt.setInt(i++, restaurant);
	        pstmt.setBoolean(i++, avail);
	        results = pstmt.executeQuery();
	        
	        List<Category> categories = new ArrayList<Category>();
	        int lastCategoryId = -1;
	        Category menuCategory = new Category();
	        while (results.next()) { 
	        	String category_name = results.getString("category_name");
	        	int cat_id = results.getInt("cat_id");
	        	if (cat_id!=lastCategoryId){
	        		menuCategory = new Category(category_name, cat_id);
	        		categories.add(menuCategory);
	        		lastCategoryId=cat_id; //update to the last cat
	        	}
	        	
	        	String menu_name = results.getString("menu_name");
	        	int menu_id = results.getInt("menu_id");
	        	int price = results.getInt("price");
	        	boolean available = results.getBoolean("available");
	        	MenuItem menuItem = new MenuItem(menu_id, 
	        			menu_name, price, available);
	        	
	        	menuCategory.addToCategory(menuItem);
	        } 
	        
	        if (categories.size()>0) {
		        sr.setResult(categories);
		        sr.setSuccess(true);
	        } else {
	        	sr.setResult(null);
	        	sr.setMessage("No categories found");
		        sr.setSuccess(true);
	        }
	        
	        return sr;
		} catch (Exception e) {
			sr.setSuccess(false);
			sr.setMessage("Error: internal database issue:  "+
				e.getMessage());
			System.out.println(e.getStackTrace());
			return sr;
		} finally {
			sqlCleanup(pstmt,results,conn);
		}
	}

	public StandardResult getMenuByCategory(int restaurant) {
		
		StandardResult sr = new StandardResult(false, null);
		ResultSet results = null;
		
		try {
			// Open the connection
			conn = connUtil.getConnection();
	        // Create the prepared statement
	        pstmt = conn.prepareStatement(getByCategory);
	        // Set the variable parameters
	        int i = 1;
	        pstmt.setInt(i++, restaurant);
	        results = pstmt.executeQuery();
	        
	        List<Category> categories = new ArrayList<Category>();
	        int lastCategoryId = -1;
	        Category menuCategory = new Category();
	        while (results.next()) { 
	        	String category_name = results.getString("category_name");
	        	int cat_id = results.getInt("cat_id");
	        	if (cat_id!=lastCategoryId){
	        		menuCategory = new Category(category_name, cat_id);
	        		categories.add(menuCategory);
	        		lastCategoryId=cat_id; //update to the last cat
	        	}
	        	
	        	String menu_name = results.getString("menu_name");
	        	int menu_id = results.getInt("menu_id");
	        	int price = results.getInt("price");
	        	boolean available = results.getBoolean("available");
	        	MenuItem menuItem = new MenuItem(menu_id, 
	        			menu_name, price, available);
	        	
	        	menuCategory.addToCategory(menuItem);
	        } 
	        
	        if (categories.size()>0) {
		        sr.setResult(categories);
		        sr.setSuccess(true);
	        } else {
	        	sr.setResult(null);
	        	sr.setMessage("No categories found");
		        sr.setSuccess(true);
	        }
	        
	        return sr;
		} catch (Exception e) {
			sr.setSuccess(false);
			sr.setMessage("Error: internal database issue:  "+
				e.getMessage());
			System.out.println(e.getMessage());
			return sr;
		} finally {
			sqlCleanup(pstmt,results,conn);
		}
	}

}