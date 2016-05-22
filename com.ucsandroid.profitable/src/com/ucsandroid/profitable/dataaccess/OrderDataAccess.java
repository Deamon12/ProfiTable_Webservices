package com.ucsandroid.profitable.dataaccess;

import java.sql.Date;
import java.sql.ResultSet;

import com.ucsandroid.profitable.StandardResult;
import com.ucsandroid.profitable.entities.Discount;
import com.ucsandroid.profitable.entities.Location;
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
			"* "+
		"FROM "+
			"tab t, location l "+
		"WHERE "+
			"l.curr_tab = t.tab_id and "+
			"l.restaurant = ? and "+
			"l.loc_id = ? ";
	
	private static String getDiscount =
		"select * from discount where disc_id = ?";
	
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
	        	loc.setCurrentTab(t);
	        	
	        	//Check for any customers on this tab
	        	
	        	//Check for any discounts on this tab
	        	int discId = results.getInt("disc_id");
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