package com.ucsandroid.profitable.dataaccess;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.ucsandroid.profitable.StandardResult;
import com.ucsandroid.profitable.entities.Location;
import com.ucsandroid.profitable.entities.LocationCategory;
import com.ucsandroid.profitable.entities.Tab;

public class LocationsDataAccess extends MainDataAccess {
	
	private static LocationsDataAccess locationsDataAccess = 
			new LocationsDataAccess();
	
	private LocationsDataAccess() {super();}
	
	public static LocationsDataAccess getInstance() {
		return locationsDataAccess;
	}
	
	private static final String getLocations = 
		"SELECT "+
			"l.loc_id, l.loc_status, l.name as location, "
			+ "lc.loccat_id, lc.name as loccatname, "+
			"l.curr_tab "+
		"FROM "+
			"location l, loc_category lc "+
		"WHERE "+
			"l.loc_cat=lc.loccat_id and "+
			"lc.restaurant = ? "+
		"ORDER BY "+
			"lc.name ASC, "+
			"l.name ASC";
	
	private static final String insertStatement = 
		"INSERT INTO location "+
		"(loc_status, name,restaurant,loc_cat) "+ 
		" VALUES(?,?,?,?)";
	
	private static final String updateStatement =
		"UPDATE "+
			"location "+
		"SET "+
			"loc_status=?, restaurant=?, "+
			"name=?, loc_cat=?, "+
			"curr_tab=? "+
		"WHERE "+
			"loc_id = ?";
	
	private static final String updateCurrTabStatement =
		"UPDATE "+
			"location "+
		"SET "+
			"curr_tab=? "+
		"WHERE "+
			"loc_id = ?";
	
	private static final String deleteStatement =
		"DELETE FROM location "+
		"WHERE loc_id = ? and restaurant = ?";
	
	private static final String updateLocStatus =
		"update location set loc_status = ? where loc_id = ? ";
	
	private static final String getLocationDetails =
		"select * from location where loc_id = ? ";
	
	private static final String getLocationFromOrderedItem = 
		"select	ho.loc_id from "+
			"ordered_item oi, customer c, has_order ho "+
		"where "+
			"oi.cust_id = c.cust_id and "+
			"c.order_id = ho.order_id and "+
			"oi.item_id = ? ";
	
	public StandardResult updateLocationStatus(int locId, String status) {
		StandardResult sr = new StandardResult(false, null);
		try {
			// Open the connection
			conn = connUtil.getConnection();
			// Begin transaction
	        conn.setAutoCommit(false);
	        // Create the prepared statement
	        pstmt = conn.prepareStatement(updateLocStatus);
	        // Set the variable parameters
	        int i = 1;
	        pstmt.setString(i++, status);
	        pstmt.setInt(i++, locId);

	        // Validate for expected and return status
	        return updateHelper(pstmt.executeUpdate(), 
	        		1, conn, sr);
		} catch (Exception e) {
			return catchErrorAndSetSR(sr, e);
		} finally {
			sqlCleanup(pstmt,conn);
		}
	}
	
	public StandardResult delete(int loc_id, int restId) {
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
	        pstmt.setInt(i++, loc_id);
	        pstmt.setInt(i++, restId);

			return deleteHelper(pstmt.executeUpdate(), 
	        		1, conn, sr);
		} catch (Exception e) {
			return catchErrorAndSetSR(sr, e);
		} finally {
			sqlCleanup(pstmt,conn);
		}
	}

	public StandardResult insert(String locStatus, String name,
			int restId, int locCat) {
		StandardResult sr = new StandardResult(false, null);
		
		try {
			// Open the connection
			conn = connUtil.getConnection();
			// Begin transaction
	        conn.setAutoCommit(false);
	        // Create the prepared statement
	        pstmt = conn.prepareStatement(insertStatement);
	        // Set the variable parameters
	        int i = 1;
	        pstmt.setString(i++, locStatus);
	        pstmt.setString(i++, name);
	        pstmt.setInt(i++, restId);
	        pstmt.setInt(i++, locCat);
	        // Validate for expected and return status
	        return insertHelper(pstmt.executeUpdate(), 
	        		conn, sr);
		} catch (Exception e) {
			return catchErrorAndSetSR(sr, e);
		} finally {
			sqlCleanup(pstmt,conn);
		}
		
	}

	public StandardResult update(String status, int restId,
			String name, int locCat, int currTab, int locId) {
		StandardResult sr = new StandardResult(false, null);
		try {
			// Open the connection
			conn = connUtil.getConnection();
			// Begin transaction
	        conn.setAutoCommit(false);
	        // Create the prepared statement
	        pstmt = conn.prepareStatement(updateStatement);
	        // Set the variable parameters
	        int i = 1;
	        pstmt.setString(i++, status);
	        pstmt.setInt(i++, restId);
	        pstmt.setString(i++, name);
	        pstmt.setInt(i++, locCat);
	        if (currTab>0){
	        	pstmt.setInt(i++, currTab);
	        } else {
	        	pstmt.setNull(i++, Types.BIGINT);
	        }
	        pstmt.setInt(i++, locId);

	        // Validate for expected and return status
	        return updateHelper(pstmt.executeUpdate(), 
	        		1, conn, sr);
		} catch (Exception e) {
			return catchErrorAndSetSR(sr, e);
		} finally {
			sqlCleanup(pstmt,conn);
		}
	}
	
	public StandardResult update(int currTab, int locId) {
		StandardResult sr = new StandardResult(false, null);
		try {
			// Open the connection
			conn = connUtil.getConnection();
			// Begin transaction
	        conn.setAutoCommit(false);
	        // Create the prepared statement
	        pstmt = conn.prepareStatement(updateCurrTabStatement);
	        // Set the variable parameters
	        int i = 1;
	        if (currTab>0){
	        	pstmt.setInt(i++, currTab);
	        } else {
	        	pstmt.setNull(i++, Types.BIGINT);
	        }
	        pstmt.setInt(i++, locId);

	        // Validate for expected and return status
	        return updateHelper(pstmt.executeUpdate(), 
	        		1, conn, sr);
		} catch (Exception e) {
			return catchErrorAndSetSR(sr, e);
		} finally {
			sqlCleanup(pstmt,conn);
		}
	}
	
	public StandardResult getLocations(int restaurant) {
		StandardResult sr = new StandardResult(false, null);
		ResultSet results = null;
		try {
			conn = connUtil.getConnection();
	        pstmt = conn.prepareStatement(getLocations);
	        pstmt.setInt(1, restaurant);
	        results = pstmt.executeQuery();
	        
	        List<LocationCategory> locationCategories = 
	        		new ArrayList<LocationCategory>();
	        LocationCategory lc = new LocationCategory();
	        int lastLocCat = -1;
	        while (results.next()) { 
	        	//extract the location category id
	        	int lcid = results.getInt("loccat_id");
	        	if (lastLocCat!=lcid) {
	        		//if it wasnt the last, then it is new
	        		String lcname = results.getString("loccatname");
	        		int restVal = restaurant;
	        		lc = new LocationCategory(lcid, 
	        				lcname, restVal);
	        		locationCategories.add(lc);
	        		//update record of last
	        		lastLocCat=lcid;
	        	}
	        	
	        	int loc_id = results.getInt("loc_id");
	        	int restId = restaurant;
	        	int curr_tab = results.getInt("curr_tab");
	        	String loc_status = results.getString("loc_status");
	        	String location = results.getString("location");
	        	Tab t = new Tab();
	        	t.setTabId(curr_tab);
	        	
	        	Location loc = new Location(loc_id,loc_status,location,
	        			t,restId);
	        	lc.addToLocations(loc);
	        } 
	        
	        return successReturnSR(sr, locationCategories);
		} catch (Exception e) {
			return catchErrorAndSetSR(sr, e);
		} finally {
			sqlCleanup(pstmt,results,conn);
		}
	}
	
	public StandardResult getLocationDetails(int location) {
		StandardResult sr = new StandardResult(false, null);
		ResultSet results = null;
		try {
			conn = connUtil.getConnection();
	        pstmt = conn.prepareStatement(getLocationDetails);
	        pstmt.setInt(1, location);
	        results = pstmt.executeQuery();
	        Location loc=null;
	        
	        if (results.next()) {
	        	int loc_id = results.getInt("loc_id");
	        	int restId = results.getInt("restaurant");
	        	int curr_tab = results.getInt("curr_tab");
	        	String loc_status = results.getString("loc_status");
	        	String name = results.getString("name");
	        	Tab t = new Tab();
	        	t.setTabId(curr_tab);
	        	
	        	loc = new Location(loc_id,loc_status,name,
	        			t,restId);
	        }
	        
	        return successReturnSR(sr, loc);
		} catch (Exception e) {
			return catchErrorAndSetSR(sr, e);
		} finally {
			sqlCleanup(pstmt,results,conn);
		}
	}
	
	public StandardResult getLocationFromOrderedItem(int orderedItem) {
		StandardResult sr = new StandardResult(false, null);
		ResultSet results = null;
		try {
			conn = connUtil.getConnection();
	        pstmt = conn.prepareStatement(getLocationFromOrderedItem);
	        pstmt.setInt(1, orderedItem);
	        results = pstmt.executeQuery();
	        
	        Location loc=null;
	        
	        if (results.next()) {
	        	int loc_id = results.getInt("loc_id");

	        	
	        	loc = new Location();
	        	loc.setId(loc_id);
	        }
	        
	        return successReturnSR(sr, loc);
		} catch (Exception e) {
			return catchErrorAndSetSR(sr, e);
		} finally {
			sqlCleanup(pstmt,results,conn);
		}
	}

}
