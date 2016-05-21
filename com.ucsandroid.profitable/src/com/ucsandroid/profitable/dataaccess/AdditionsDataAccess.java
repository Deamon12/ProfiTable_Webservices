package com.ucsandroid.profitable.dataaccess;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ucsandroid.profitable.StandardResult;
import com.ucsandroid.profitable.entities.FoodAddition;

public class AdditionsDataAccess extends MainDataAccess {
	
	private static String insertStatement = 
		"INSERT INTO Food_attribute "+
		"(attribute, price_mod, Available, restaurant) "+ 
		" VALUES(?,?,?,?)";
	
	private static String updateStatement =
		"UPDATE "+
			"food_attribute "+
		"SET "+
			"attribute=?, price_mod=?, Available=?, restaurant=? "+
		"WHERE "+
			"attr_id = ?";
	
	private static String deleteStatement =
		"DELETE FROM food_attribute "+
		"WHERE attr_id = ? and restaurant = ?";
	
	private static String getStatement = 
			"SELECT distinct "+
				" * "+
			"FROM "+
				"food_attribute fa "+
			"WHERE "+
				"fa.restaurant= ? "+
				"ORDER BY fa.attr_id ASC ";
	
	private static String getStatement2 = 
			"SELECT distinct "+
				" * "+
			"FROM "+
				"food_attribute fa "+
			"WHERE "+
				"fa.restaurant = ? "+
				"and fa.available = ? "+
				"ORDER BY fa.attr_id ASC ";

	public AdditionsDataAccess() {
		super();
	}
	
	public StandardResult getAdditions(int restaurant, boolean avail) {
		StandardResult sr = new StandardResult(false, null);
		ResultSet results = null;
		try {
			// Open the connection
			conn = connUtil.getConnection();
	        // Create the prepared statement
	        pstmt = conn.prepareStatement(getStatement2);
	        // Set the variable parameters
	        int i = 1;
	        pstmt.setInt(i++, restaurant);
	        pstmt.setBoolean(i++, avail);
	        results = pstmt.executeQuery();
	        List<FoodAddition> additions = new ArrayList<FoodAddition>();
	        while (results.next()) { 
	        	String attribute = results.getString("attribute");
	        	boolean available = results.getBoolean("Available");
	        	int price_mod = results.getInt("price_mod");
	        	int attrId = results.getInt("attr_id");
	        	int restId = results.getInt("restaurant");
	        	FoodAddition add = new FoodAddition(attribute, price_mod, 
	        			available,attrId,restId);
	        	additions.add(add);
	        } 
	        return successReturnSR(sr, additions);
		} catch (Exception e) {
			return catchErrorAndSetSR(sr, e);
		} finally {
			sqlCleanup(pstmt,results,conn);
		}
	}
	
	public StandardResult getAdditions(int restaurant) {
		StandardResult sr = new StandardResult(false, null);
		ResultSet results = null;
		try {
			conn = connUtil.getConnection();
	        pstmt = conn.prepareStatement(getStatement);
	        int i = 1;
	        pstmt.setInt(i++, restaurant);
	        results = pstmt.executeQuery();
	        List<FoodAddition> additions = new ArrayList<FoodAddition>();
	        while (results.next()) { 
	        	String attribute = results.getString("attribute");
	        	boolean available = results.getBoolean("Available");
	        	int price_mod = results.getInt("price_mod");
	        	int attrId = results.getInt("attr_id");
	        	int restId = results.getInt("restaurant");
	        	FoodAddition add = new FoodAddition(attribute, price_mod, 
	        			available,attrId,restId);
	        	additions.add(add);
	        } 
	        return successReturnSR(sr, additions);
		} catch (Exception e) {
			return catchErrorAndSetSR(sr, e);
		} finally {
			sqlCleanup(pstmt,results,conn);
		}
	}

	public StandardResult delete(int attrib, int restId) {
		StandardResult sr = new StandardResult(false, null);
		try {
			conn = connUtil.getConnection();
	        conn.setAutoCommit(false);
	        pstmt = conn.prepareStatement(deleteStatement);
	        int i = 1;
	        pstmt.setInt(i++, attrib);
	        pstmt.setInt(i++, restId);
	        return deleteHelper(pstmt.executeUpdate(), 
	        		1, conn, sr);
		} catch (Exception e) {
			return catchErrorAndSetSR(sr, e);
		} finally {
			sqlCleanup(pstmt,conn);
		}
	}

	public StandardResult insert(String name, int price, boolean avail,
			int restId) {
		StandardResult sr = new StandardResult(false, null);
		try {
			conn = connUtil.getConnection();
	        conn.setAutoCommit(false);
	        pstmt = conn.prepareStatement(insertStatement);
	        int i = 1;
	        pstmt.setString(i++, name);
	        pstmt.setInt(i++, price);
	        pstmt.setBoolean(i++, avail);
	        pstmt.setInt(i++, restId);
	        return insertHelper(pstmt.executeUpdate(), 
	        		conn, sr);
		} catch (Exception e) {
			return catchErrorAndSetSR(sr, e);
		} finally {
			sqlCleanup(pstmt,conn);
		}
		
	}

	public StandardResult update(int attrib, String name, 
			int price, boolean avail, int restId) {
		StandardResult sr = new StandardResult(false, null);
		try {
			conn = connUtil.getConnection();
	        conn.setAutoCommit(false);
	        pstmt = conn.prepareStatement(updateStatement);
	        int i = 1;
	        pstmt.setString(i++, name);
	        pstmt.setInt(i++, price);
	        pstmt.setBoolean(i++, avail);
	        pstmt.setInt(i++, restId);
	        pstmt.setInt(i++, attrib);
	        return updateHelper(pstmt.executeUpdate(), 
	        		1, conn, sr);
		} catch (Exception e) {
			return catchErrorAndSetSR(sr, e);
		} finally {
			sqlCleanup(pstmt,conn);
		}
	}
}