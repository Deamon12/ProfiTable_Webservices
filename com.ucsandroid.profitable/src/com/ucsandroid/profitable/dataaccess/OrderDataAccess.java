package com.ucsandroid.profitable.dataaccess;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;

import com.ucsandroid.profitable.StandardResult;
import com.ucsandroid.profitable.entities.Customer;
import com.ucsandroid.profitable.entities.Discount;
import com.ucsandroid.profitable.entities.Employee;
import com.ucsandroid.profitable.entities.FoodAddition;
import com.ucsandroid.profitable.entities.Location;
import com.ucsandroid.profitable.entities.MenuItem;
import com.ucsandroid.profitable.entities.OrderedItem;
import com.ucsandroid.profitable.entities.Tab;

public class OrderDataAccess extends MainDataAccess {
	
	private static OrderDataAccess orderDataAccess = 
			new OrderDataAccess();
	
	private OrderDataAccess() {super();}
	
	public static OrderDataAccess getInstance() {
		return orderDataAccess;
	}
	
	private static String getOrder = 
		"SELECT "+
			"t.*, l.*, e.* "+
		"FROM "+
			"tab t, location l, has_order ho, employee e "+
		"WHERE "+
			"l.curr_tab = t.tab_id and "+
			"ho.order_id=t.tab_id and "+
			"ho.emp_id=e.emp_id and "+
			"l.restaurant = ? and "+
			"l.loc_id = ? ";
	
	private static String getDiscount =
		"select * from discount where disc_id = ?";
	
	private static String getCustomersOnOrder = 
		"select * from customer where order_id = ?";
	
	private static String getCustomerOrder =
		"select "+
			"i.*, mi.*, fa.* "+
		"from  "+
			"ordered_item oi, item i, ordered_with ow,  "+
			"food_attribute fa, menu_item mi "+
		"where "+
			"oi.cust_id = ? and "+
			"oi.item_id = i.item_id and "+
			"oi.menu_id = mi.menu_id and "+
			"i.item_id = ow.item_id and "+
			"ow.attr_id = fa.attr_id "+
		"order by "+
			"item_id ASC ";
	
	private static String createOrderedItem = 
		"INSERT INTO Item (item_status, notes, bring_first) "+
		"VALUES( ? , ? , ? ) ";
	
	private static String createOrderRelation =
		"INSERT INTO Ordered_item (item_id, menu_id, cust_id) "+
		"VALUES( ? , ? , ? ) ";
	
	private static String createOrderAddition = 
		"INSERT INTO Ordered_with (item_id, attr_id) "+
		"VALUES( ? , ? ) ";
	
	public StandardResult createOrderedItem(String notes,
			String status, boolean bringFirst) {
		StandardResult sr = new StandardResult(false, null);
		ResultSet generatedKeys = null;
		try {
			// Open the connection
			conn = connUtil.getConnection();
			// Begin transaction
	        conn.setAutoCommit(false);
	        // Create the prepared statement
	        pstmt = conn.prepareStatement(createOrderedItem,
                    Statement.RETURN_GENERATED_KEYS);
	        // Set the variable parameters
	        int i = 1;
	        pstmt.setString(i++, notes);
	        pstmt.setString(i++, status);
	        pstmt.setBoolean(i++, bringFirst);
	        // Validate for expected and return status
	        int createdRows = pstmt.executeUpdate();
	        generatedKeys = pstmt.getGeneratedKeys();
	        if (generatedKeys.next()) {
	        	int newKey =  generatedKeys.getInt(1);
	        	OrderedItem o = new 
	        			OrderedItem(newKey, notes, status, bringFirst);
	        	sr.setResult(o);
	        	return createHelper(createdRows, 
	        			conn, sr);
	        } else {
	        	sr.setMessage("could not create OrderedItem");
	        	return sr;
	        }
		} catch (Exception e) {
			return catchErrorAndSetSR(sr, e);
		} finally {
			sqlCleanup(pstmt,generatedKeys,conn);
		}
	}
	
	public StandardResult createOrderedRelation(int customer,
			int orderedItem, int menuItem) {
		StandardResult sr = new StandardResult(false, null);
		try {
			// Open the connection
			conn = connUtil.getConnection();
			// Begin transaction
	        conn.setAutoCommit(false);
	        // Create the prepared statement
	        pstmt = conn.prepareStatement(createOrderRelation);
	        // Set the variable parameters
	        int i = 1;
	        pstmt.setInt(i++, orderedItem);
	        pstmt.setInt(i++, menuItem);
	        pstmt.setInt(i++, customer);
	        // Validate for expected and return status
	        return insertHelper(pstmt.executeUpdate(), 
	        		conn, sr);
		} catch (Exception e) {
			return catchErrorAndSetSR(sr, e);
		} finally {
			sqlCleanup(pstmt,conn);
		}
	}
	
	public StandardResult createOrderedAddition(
			int orderedItem, int foodAddition) {
		StandardResult sr = new StandardResult(false, null);
		try {
			// Open the connection
			conn = connUtil.getConnection();
			// Begin transaction
	        conn.setAutoCommit(false);
	        // Create the prepared statement
	        pstmt = conn.prepareStatement(createOrderAddition);
	        // Set the variable parameters
	        int i = 1;
	        pstmt.setInt(i++, orderedItem);
	        pstmt.setInt(i++, foodAddition);
	        // Validate for expected and return status
	        return insertHelper(pstmt.executeUpdate(), 
	        		conn, sr);
		} catch (Exception e) {
			return catchErrorAndSetSR(sr, e);
		} finally {
			sqlCleanup(pstmt,conn);
		}
	}
	
	public StandardResult getOrder(int loc_id, int rest_id) {
		StandardResult sr = new StandardResult(false, null);
		ResultSet results = null;
		try {
			conn = connUtil.getConnection();
	        pstmt = conn.prepareStatement(getOrder);
	        pstmt.setInt(1, rest_id);
	        pstmt.setInt(2, loc_id);
	        results = pstmt.executeQuery();
	        
	        int restId = rest_id;
	        int locId = loc_id;
	      
	        if (results.next()) { 
	        	String loc_status = results.getString("loc_status");
	        	String location = results.getString("name");
	        	Location loc = new Location(locId,loc_status,location,restId);
	        	
	        	Date time_in = results.getDate("time_in");
	        	Date time_out = results.getDate("time_out");
	        	int tabId = results.getInt("tab_id");
	        	String tab_status = results.getString("tab_status");
	        	Tab t = new Tab(tabId, tab_status, time_in, time_out);
	        	
	        	int emp_id = results.getInt("emp_id");
	        	String account_name= results.getString("account_name");
	        	String emp_type= results.getString("emp_type");
	        	String first_name= results.getString("first_name");
	        	String last_name= results.getString("last_name");
	        	Employee e = new Employee(emp_id, emp_type,
	        		account_name, first_name, last_name, rest_id);
	        	t.setServer(e);
	        	loc.setCurrentTab(t);
	        	
	        	//keep any discount id available for processing after customers
	        	int discId = results.getInt("disc_id");
	        	
	        	//Check for any customers on this tab
	        	results.close();
	        	pstmt = conn.prepareStatement(getCustomersOnOrder);
		        pstmt.setInt(1, tabId);
		        results = pstmt.executeQuery();
		        while (results.next()) {
		        	//if there are any customers on this order, loop through them
		        	//and grab all of their order 
		        	int cust_id = results.getInt("cust_id");
		        	Customer c = new Customer(cust_id, tabId);
		        	t.addCustomer(c);
		        	pstmt = conn.prepareStatement(getCustomerOrder);
			        pstmt.setInt(1, cust_id);
			        ResultSet results2 = pstmt.executeQuery();
			        OrderedItem oi = new OrderedItem();
			        MenuItem mi = new MenuItem();
			        int lastItem = -1;
			        while (results2.next()) {
			        	int item_id = results2.getInt("item_id");
			        	if (item_id!=lastItem) {
			        		lastItem=item_id;
			        		//if it does not equal last item, create new
			        		String notes = results2.getString("notes");
			        		String item_status = results2.getString("item_status");
			        		boolean bring_first = results2.getBoolean("bring_first");
			        		
			        		oi = new OrderedItem(item_id, notes, 
			        				item_status, bring_first);
			        		
			        		int menu_id = results2.getInt("menu_id");
			        		String menu_name = results2.getString("menu_name");
				        	String description = results2.getString("description");
				        	boolean available = results2.getBoolean("available");
				        	int price = results2.getInt("price");
				        	
				        	mi = new MenuItem(menu_id, menu_name, 
				        			description,price,available);
				        				        	
				        	oi.setMenuItem(mi);
			        		c.addItem(oi);
			        	} 
			        	String attribute = results2.getString("attribute");
			        	int price_mod = results2.getInt("price_mod");
			        	int attrId = results2.getInt("attr_id");
			        	
			        	FoodAddition fa = new FoodAddition(attribute,
			        			price_mod, attrId);
			        	oi.addAddition(fa);
			        	
			        }
			        results2.close();
		        }
	        	
	        	//Check for any discounts on this tab
	        	if (discId==0) {
	        		//if no discount, can return
	        		return successReturnSR(sr, loc);
	        	} else {
	        		//need to query for the discount
	        		results.close();
	        		
	        		pstmt = conn.prepareStatement(getDiscount);
	    	        pstmt.setInt(1, discId);
	    	        results = pstmt.executeQuery();
	    	        
	    	        if (results.next()) {
	    	        	//create the discount, add it to the tab
	    	        	String disc_type = results.getString("disc_type");
	    	        	double disc_percent = results.getDouble("disc_percent");
	    	        	boolean available = results.getBoolean("available");
	    	        	Discount d = new Discount(discId, disc_type, 
	    	        		disc_percent, available, restId);
	    	        	t.setDiscount(d);
	    	        	return successReturnSR(sr, loc);
	    	        } else {
	    	        	//specified discount does not exist
	    	        	sr.setMessage("Unable to find discount");
	    	        	return sr;
	    	        }
	        	}
	        } else {
	        	sr.setMessage("Unable to find location/tab");
	        	return sr;
	        }
		} catch (Exception e) {
			return catchErrorAndSetSR(sr, e);
		} finally {
			sqlCleanup(pstmt,results,conn);
		}
	}

}