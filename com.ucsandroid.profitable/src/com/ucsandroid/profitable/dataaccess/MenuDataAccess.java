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
	
	private static String getMenuItemsByCategory = 
		"SELECT "+
			"mi.menu_id, mi.menu_name, mi.description, "+
			"mi.price, mi.available "+
		"FROM "+
			"menu_item mi, "+
			"has_cat hc "+
		"WHERE "+
			"mi.menu_id=hc.menu_id "+
			"and mi.restaurant = ? "+
			"and hc.cat_id= ? ";
	
	private static String getMenuItemsByCategoryAvailable =
		"SELECT "+
			"mi.menu_id, mi.menu_name, mi.description, "+
			"mi.price, mi.available "+
		"FROM "+
			"menu_item mi, "+
			"has_cat hc "+
		"WHERE "+
			"mi.menu_id=hc.menu_id "+
			"and mi.restaurant = ? "+
			"and hc.cat_id = ? "+
			"and mi.available = ? ";
	
	private static String getMenuItemSingle = 
		"SELECT "+
			"mi.menu_id, mi.menu_name, mi.description, "+
			"mi.price, mi.available "+
		"FROM "+
			"menu_item mi "+
		"WHERE "+
			"mi.restaurant = ? "+
			"and mi.menu_id = ? ";
	
	private static String getMenuItems = 
		"SELECT "+
			"mi.menu_id, mi.menu_name, mi.description, "+
			"mi.price, mi.available "+
		"FROM "+
			"menu_item mi "+
		"WHERE "+
			"mi.restaurant = ? ";
	
	private static String getMenuItemsAvailable = 
		"SELECT "+
			"mi.menu_id, mi.menu_name, mi.description, "+
			"mi.price, mi.available "+
		"FROM "+
			"menu_item mi "+
		"WHERE "+
			"mi.restaurant = ? "+
			"and mi.available = ? ";
	
	private static String getAdditionsForAnItem =
		"SELECT fa.attr_id, fa.attribute, fa.price_mod, "
		+ "fa.available, ha.default_incl "+
		"FROM food_attribute fa, has_attr ha "+
		"WHERE ha.menu_id = ? and ha.attr_id=fa.attr_id";
	
	public MenuDataAccess() {
		super();
	}
	
	public StandardResult getMenuItem(int restaurant, int menuItem) {
		StandardResult sr = new StandardResult(false, null);
		ResultSet results = null;
		
		try {
			// Open the connection
			conn = connUtil.getConnection();
	        // Create the prepared statement
	        pstmt = conn.prepareStatement(getMenuItemSingle);
	        // Set the variable parameters
	        int i = 1;
	        pstmt.setInt(i++, restaurant);
	        pstmt.setInt(i++, menuItem);
	        results = pstmt.executeQuery();
	        int menuItemId = -1;
	        MenuItem food = new MenuItem();
	        
	        if (results.next()) { 
	        	String menu_name = results.getString("menu_name");
	        	String description = results.getString("description");
	        	boolean available = results.getBoolean("available");
	        	int price = results.getInt("price");
	        	int menuId = results.getInt("menu_id");
	        	food = new MenuItem(menuId, menu_name, 
	        			description,price,available);
	        	menuItemId=menuId;
	        } 
	        
	        List<FoodAddition> optional = new ArrayList<FoodAddition>();
	        List<FoodAddition> included = new ArrayList<FoodAddition>();
	        pstmt = conn.prepareStatement(getAdditionsForAnItem);
	        pstmt.setInt(1, menuItemId);
	        results.close();
	        results = pstmt.executeQuery();
	        
	        while (results.next()) { 
	        	String attribute = results.getString("attribute");
	        	boolean available = results.getBoolean("available");
	        	int price_mod = results.getInt("price_mod");
	        	int attrId = results.getInt("attr_id");
	        	boolean defaultIncl = results.getBoolean("default_incl");
	        	FoodAddition fa = new FoodAddition(attribute, price_mod, 
	        			available,attrId);
	        	if (defaultIncl){
	        		//included on item by default
	        		included.add(fa);
	        	}else {
	        		//not included by default, but available
	        		optional.add(fa);
	        	}
	        } 
	        
	        food.setDefaultAdditions(included);
	        food.setOptionalAdditions(optional);
	        sr.setSuccess(true);
	        sr.setResult(food);
	        return sr;
		} catch (Exception e) {
			return catchErrorAndSetSR(sr, e);
		} finally {
			sqlCleanup(pstmt,results,conn);
		}
	}
	
	public StandardResult getMenuItems(int restaurant, int category) {
		StandardResult sr = new StandardResult(false, null);
		ResultSet results = null;
		
		try {
			// Open the connection
			conn = connUtil.getConnection();
	        // Create the prepared statement
	        pstmt = conn.prepareStatement(getMenuItemsByCategory);
	        // Set the variable parameters
	        int i = 1;
	        pstmt.setInt(i++, restaurant);
	        pstmt.setInt(i++, category);
	        results = pstmt.executeQuery();
	        
	        List<MenuItem> menu = new ArrayList<MenuItem>();
	        
	        while (results.next()) { 
	        	String menu_name = results.getString("menu_name");
	        	String description = results.getString("description");
	        	boolean available = results.getBoolean("available");
	        	int price = results.getInt("price");
	        	int menuId = results.getInt("menu_id");
	        	MenuItem food = new MenuItem(menuId, menu_name, 
	        			description,price,available);
	        	menu.add(food);
	        } 
	        
	        sr.setSuccess(true);
	        sr.setResult(menu);
	        return sr;
		} catch (Exception e) {
			return catchErrorAndSetSR(sr, e);
		} finally {
			sqlCleanup(pstmt,results,conn);
		}
	}
	
	public StandardResult getMenuItems(int restaurant, int category,
			boolean avail) {
		StandardResult sr = new StandardResult(false, null);
		ResultSet results = null;
		
		try {
			// Open the connection
			conn = connUtil.getConnection();
	        // Create the prepared statement
	        pstmt = conn.prepareStatement(getMenuItemsByCategoryAvailable);
	        // Set the variable parameters
	        int i = 1;
	        pstmt.setInt(i++, restaurant);
	        pstmt.setInt(i++, category);
	        pstmt.setBoolean(i++, avail);
	        results = pstmt.executeQuery();
	        
	        List<MenuItem> menu = new ArrayList<MenuItem>();
	        
	        while (results.next()) { 
	        	String menu_name = results.getString("menu_name");
	        	String description = results.getString("description");
	        	boolean available = results.getBoolean("available");
	        	int price = results.getInt("price");
	        	int menuId = results.getInt("menu_id");
	        	MenuItem food = new MenuItem(menuId, menu_name, 
	        			description,price,available);
	        	menu.add(food);
	        } 
	        
	        sr.setSuccess(true);
	        sr.setResult(menu);
	        return sr;
		} catch (Exception e) {
			return catchErrorAndSetSR(sr, e);
		} finally {
			sqlCleanup(pstmt,results,conn);
		}
	}
	
	public StandardResult getMenuItems(int restaurant, boolean avail) {
		StandardResult sr = new StandardResult(false, null);
		ResultSet results = null;
		
		try {
			// Open the connection
			conn = connUtil.getConnection();
	        // Create the prepared statement
	        pstmt = conn.prepareStatement(getMenuItemsAvailable);
	        // Set the variable parameters
	        int i = 1;
	        pstmt.setInt(i++, restaurant);
	        pstmt.setBoolean(i++, avail);
	        results = pstmt.executeQuery();
	        
	        List<MenuItem> menu = new ArrayList<MenuItem>();
	        
	        while (results.next()) { 
	        	String menu_name = results.getString("menu_name");
	        	String description = results.getString("description");
	        	boolean available = results.getBoolean("available");
	        	int price = results.getInt("price");
	        	int menuId = results.getInt("menu_id");
	        	MenuItem food = new MenuItem(menuId, menu_name, 
	        			description,price,available);
	        	menu.add(food);
	        } 
	        
	        sr.setSuccess(true);
	        sr.setResult(menu);
	        return sr;
		} catch (Exception e) {
			return catchErrorAndSetSR(sr, e);
		} finally {
			sqlCleanup(pstmt,results,conn);
		}
	}
	
	public StandardResult getMenuItems(int restaurant) {
		StandardResult sr = new StandardResult(false, null);
		ResultSet results = null;
		
		try {
			// Open the connection
			conn = connUtil.getConnection();
	        // Create the prepared statement
	        pstmt = conn.prepareStatement(getMenuItems);
	        // Set the variable parameters
	        int i = 1;
	        pstmt.setInt(i++, restaurant);
	        results = pstmt.executeQuery();
	        
	        List<MenuItem> menu = new ArrayList<MenuItem>();
	        
	        while (results.next()) { 
	        	String menu_name = results.getString("menu_name");
	        	String description = results.getString("description");
	        	boolean available = results.getBoolean("available");
	        	int price = results.getInt("price");
	        	int menuId = results.getInt("menu_id");
	        	MenuItem food = new MenuItem(menuId, menu_name, 
	        			description,price,available);
	        	menu.add(food);
	        } 
	        
	        sr.setSuccess(true);
	        sr.setResult(menu);
	        return sr;
		} catch (Exception e) {
			return catchErrorAndSetSR(sr, e);
		} finally {
			sqlCleanup(pstmt,results,conn);
		}
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
	        
	        sr.setResult(categories);
	        sr.setSuccess(true);
	        return sr;
		} catch (Exception e) {
			return catchErrorAndSetSR(sr, e);
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
	        
	        sr.setSuccess(true);
	        sr.setResult(categories);
	        return sr;
		} catch (Exception e) {
			return catchErrorAndSetSR(sr, e);
		} finally {
			sqlCleanup(pstmt,results,conn);
		}
	}

}