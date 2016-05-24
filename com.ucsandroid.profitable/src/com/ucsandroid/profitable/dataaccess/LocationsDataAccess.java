package com.ucsandroid.profitable.dataaccess;

import java.sql.ResultSet;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.ucsandroid.profitable.StandardResult;
import com.ucsandroid.profitable.entities.Location;
import com.ucsandroid.profitable.entities.LocationCategory;

public class LocationsDataAccess extends MainDataAccess {
	
	private static LocationsDataAccess locationsDataAccess = 
			new LocationsDataAccess();
	
	private LocationsDataAccess() {super();}
	
	public static LocationsDataAccess getInstance() {
		return locationsDataAccess;
	}
	
	private static String getLocations = 
		"SELECT "+
			"l.loc_id, l.loc_status, l.name as location, "
			+ "lc.loccat_id, lc.name as loccatname "+
		"FROM "+
			"location l, loc_category lc "+
		"WHERE "+
			"l.loc_cat=lc.loccat_id and "+
			"lc.restaurant = ? "+
		"ORDER BY "+
			"lc.name ASC ";
	
	private static String insertStatement = 
		"INSERT INTO location "+
		"(loc_status, name,restaurant,loc_cat) "+ 
		" VALUES(?,?,?,?)";
	
	private static String updateStatement =
		"UPDATE "+
			"location "+
		"SET "+
			"loc_status=?, restaurant=? "+
			"name=?, loc_cat=? "+
			"curr_tab=? "+
		"WHERE "+
			"loc_id = ?";
	
	private static String updateCurrTabStatement =
		"UPDATE "+
			"location "+
		"SET "+
			"curr_tab=? "+
		"WHERE "+
			"loc_id = ?";
	
	private static String deleteStatement =
		"DELETE FROM location "+
		"WHERE loc_id = ? and restaurant = ?";
	
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
	        	
	        	String loc_status = results.getString("loc_status");
	        	String location = results.getString("location");
	        	
	        	Location loc = new Location(loc_id,loc_status,location,restId);
	        	lc.addToLocations(loc);
	        } 
	        
	        return successReturnSR(sr, locationCategories);
		} catch (Exception e) {
			return catchErrorAndSetSR(sr, e);
		} finally {
			sqlCleanup(pstmt,results,conn);
		}
	}

}
