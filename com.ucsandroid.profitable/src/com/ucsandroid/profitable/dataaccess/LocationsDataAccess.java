package com.ucsandroid.profitable.dataaccess;

import java.sql.ResultSet;
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
