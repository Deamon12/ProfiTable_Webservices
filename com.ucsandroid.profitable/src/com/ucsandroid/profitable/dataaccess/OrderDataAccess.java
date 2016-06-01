package com.ucsandroid.profitable.dataaccess;

import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

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
	
	private static String getActiveOrders =
		"SELECT "+
			"t.* "+
		"FROM "+
			"tab t, location l, has_order ho "+
		"WHERE "+
			"l.curr_tab = t.tab_id and "+
			"ho.order_id=t.tab_id and "+
			"l.restaurant = ? "+
			"order by time_in asc";
	
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
		"select d.* "+
		"from has_disc hd, discount d "+
		"where hd.order_id=? and hd.disc_id=d.disc_id "+
		"order by disc_type ASC";
	
	private static String getCustomersOnOrder = 
		"select * from customer where order_id = ? "+
		"order by cust_id asc";
	
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
			"ow.attr_id = fa.attr_id "+
		"order by "+
			"item_id ASC ";
	
	private static String createOrderedItem = 
		"INSERT INTO Item (notes, item_status, bring_first) "+
		"VALUES( ? , ? , ? ) ";
	
	private static String createOrderRelation =
		"INSERT INTO Ordered_item (item_id, menu_id, cust_id) "+
		"VALUES( ? , ? , ? ) ";
	
	private static String createOrderAddition = 
		"INSERT INTO Ordered_with (item_id, attr_id) "+
		"VALUES( ? , ? ) ";
	
	private static String createOrder = 
		"INSERT INTO Tab (tab_status, time_in) "+
		"VALUES( ? , ? ) ";
	
	private static String createOrderLocationRelation = 
		"INSERT INTO Has_order (loc_id, order_id, emp_id) "+
		"VALUES( ? , ? , ? ) ";
	
	private static String updateStatementFull =
		"UPDATE "+
			"tab "+
		"SET "+
			"tab_status=?, time_in=? "+
			"time_out=? "+
		"WHERE "+
			"tab_id = ?";
	
	private static String updateTabClose =
		"UPDATE "+
			"tab "+
		"SET "+
			"tab_status=?, time_out=? "+
		"WHERE "+
			"tab_id = ?";
	
	private static String deleteStatement =
			"DELETE FROM tab "+
			"WHERE tab_id = ? ";
	
	private static String updateOrderedItemStatus =
		"update item set item_status = ? where item_id = ? ";
	
	public StandardResult updateOrderedItemStatus(int itemId, String status) {
		StandardResult sr = new StandardResult(false, null);
		try {
			// Open the connection
			conn = connUtil.getConnection();
			// Begin transaction
	        conn.setAutoCommit(false);
	        // Create the prepared statement
	        pstmt = conn.prepareStatement(updateOrderedItemStatus);
	        // Set the variable parameters
	        int i = 1;
	        pstmt.setString(i++, status);
	        pstmt.setInt(i++, itemId);

	        // Validate for expected and return status
	        return updateHelper(pstmt.executeUpdate(), 
	        		1, conn, sr);
		} catch (Exception e) {
			return catchErrorAndSetSR(sr, e);
		} finally {
			sqlCleanup(pstmt,conn);
		}
	}
	
	public StandardResult update(String status, Timestamp time_in,
			Timestamp time_out, int tabId) {
		StandardResult sr = new StandardResult(false, null);
		try {
			// Open the connection
			conn = connUtil.getConnection();
			// Begin transaction
	        conn.setAutoCommit(false);
	        // Create the prepared statement
	        pstmt = conn.prepareStatement(updateStatementFull);
	        // Set the variable parameters
	        int i = 1;
	        pstmt.setString(i++, status);
	        pstmt.setTimestamp(i++, time_in);
	        pstmt.setTimestamp(i++, time_out);
	        pstmt.setInt(i++, tabId);

	        // Validate for expected and return status
	        return updateHelper(pstmt.executeUpdate(), 
	        		1, conn, sr);
		} catch (Exception e) {
			return catchErrorAndSetSR(sr, e);
		} finally {
			sqlCleanup(pstmt,conn);
		}
	}
	
	public StandardResult update(String status, Timestamp time_out,
			int tabId) {
		StandardResult sr = new StandardResult(false, null);
		try {
			// Open the connection
			conn = connUtil.getConnection();
			// Begin transaction
	        conn.setAutoCommit(false);
	        // Create the prepared statement
	        pstmt = conn.prepareStatement(updateTabClose);
	        // Set the variable parameters
	        int i = 1;
	        pstmt.setString(i++, status);
	        pstmt.setTimestamp(i++, time_out);
	        pstmt.setInt(i++, tabId);

	        // Validate for expected and return status
	        return updateHelper(pstmt.executeUpdate(), 
	        		1, conn, sr);
		} catch (Exception e) {
			return catchErrorAndSetSR(sr, e);
		} finally {
			sqlCleanup(pstmt,conn);
		}
	}
	
	public StandardResult delete(int tab_id) {
		StandardResult sr = new StandardResult(false, null);
		try {
			// Open the connection
			conn = connUtil.getConnection();
			// Begin transaction
	        conn.setAutoCommit(false);
	        // Create the prepared statement
	        pstmt = conn.prepareStatement(deleteStatement);
	        // Set the variable parameters
	        int i = 1;
	        pstmt.setInt(i++, tab_id);

			return deleteHelper(pstmt.executeUpdate(), 
	        		1, conn, sr);
		} catch (Exception e) {
			return catchErrorAndSetSR(sr, e);
		} finally {
			sqlCleanup(pstmt,conn);
		}
	}	
		
	public StandardResult createOrder(String status, 
			Timestamp currentDate){
		StandardResult sr = new StandardResult(false, null);
		ResultSet generatedKeys = null;
		try {
			// Open the connection
			conn = connUtil.getConnection();
			// Begin transaction
	        conn.setAutoCommit(false);
	        // Create the prepared statement
	        pstmt = conn.prepareStatement(createOrder,
                    Statement.RETURN_GENERATED_KEYS);
	        // Set the variable parameters
	        int i = 1;
	        pstmt.setString(i++, status);
	        pstmt.setTimestamp(i++, currentDate);
	        // Validate for expected and return status
	        int createdRows = pstmt.executeUpdate();
	        generatedKeys = pstmt.getGeneratedKeys();
	        if (generatedKeys.next()) {
	        	int newKey =  generatedKeys.getInt(1);
	        	Tab t = new Tab();
	        	t.setTabId(newKey);
	        	t.setTabStatus(status);
	        	t.setTimeIn(currentDate);
	        	sr.setResult(t);
	        	return createHelper(createdRows, 
	        			conn, sr);
	        } else {
	        	sr.setMessage("could not create order");
	        	return sr;
	        }
		} catch (Exception e) {
			return catchErrorAndSetSR(sr, e);
		} finally {
			sqlCleanup(pstmt,generatedKeys,conn);
		}
		
	}
	
	public StandardResult createOrderLocRelation(int order_id,
			int location_id, int employee_id) {
		StandardResult sr = new StandardResult(false, null);
		try {
			// Open the connection
			conn = connUtil.getConnection();
			// Begin transaction
	        conn.setAutoCommit(false);
	        // Create the prepared statement
	        pstmt = conn.prepareStatement(
	        		createOrderLocationRelation);
	        // Set the variable parameters
	        int i = 1;
	        pstmt.setInt(i++, location_id);
	        pstmt.setInt(i++, order_id);
	        pstmt.setInt(i++, employee_id);
	        // Validate for expected and return status
	        return insertHelper(pstmt.executeUpdate(), 
	        		conn, sr);
		} catch (Exception e) {
			return catchErrorAndSetSR(sr, e);
		} finally {
			sqlCleanup(pstmt,conn);
		}
	}
	
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
	        	
	        	Timestamp time_in = results.getTimestamp("time_in");
	        	Timestamp time_out = results.getTimestamp("time_out");
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
		        results.close();
        		pstmt = conn.prepareStatement(getDiscount);
    	        pstmt.setInt(1, tabId);
    	        results = pstmt.executeQuery();
	        	
	        	//Check for any discounts on this tab
	        	if (results.next()) {
	        		//create the discount, add it to the tab
    	        	String disc_type = results.getString("disc_type");
    	        	double disc_percent = results.getDouble("disc_percent");
    	        	boolean available = results.getBoolean("available");
    	        	int discId = results.getInt("disc_id");
    	        	Discount d = new Discount(discId, disc_type, 
    	        		disc_percent, available, restId);
    	        	t.setDiscount(d);
	        	} 	
        		return successReturnSR(sr, loc);
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

	public StandardResult getActiveOrder(int rest_id) {
		StandardResult sr = new StandardResult(false, null);
		ResultSet results = null;
		try {
			conn = connUtil.getConnection();
	        pstmt = conn.prepareStatement(getActiveOrders);
	        pstmt.setInt(1, rest_id);
	        results = pstmt.executeQuery();
	      
	        List<Tab> activeTabs = new ArrayList<Tab>();
	        
	        while (results.next()) { 
	        	
	        	Timestamp time_in = results.getTimestamp("time_in");
	        	Timestamp time_out = results.getTimestamp("time_out");
	        	int tabId = results.getInt("tab_id");
	        	String tab_status = results.getString("tab_status");
	        	Tab t = new Tab(tabId, tab_status, time_in, time_out);
	        	
	        	activeTabs.add(t);
	        	
	        	//Check for any customers on this tab
	        	pstmt = conn.prepareStatement(getCustomersOnOrder);
		        pstmt.setInt(1, tabId);
		        ResultSet results1 = null;
		        results1 = pstmt.executeQuery();
		        while (results1.next()) {
		        	//if there are any customers on this order, loop through them
		        	//and grab all of their order 
		        	int cust_id = results1.getInt("cust_id");
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
			        		//if item delivered, ignore it
			        		if (item_status.equalsIgnoreCase("delivered")){
			        			//donothing
			        		}
			        		else {
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
		        results1.close();
        	} 
	        results.close();
	        return successReturnSR(sr, activeTabs);
		} catch (Exception e) {
			return catchErrorAndSetSR(sr, e);
		} finally {
			sqlCleanup(pstmt,results,conn);
		}
	}
	
}