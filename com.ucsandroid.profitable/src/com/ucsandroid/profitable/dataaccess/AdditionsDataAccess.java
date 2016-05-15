package com.ucsandroid.profitable.dataaccess;

import com.ucsandroid.profitable.StandardResult;

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

	public AdditionsDataAccess() {
		super();
	}

	public StandardResult delete(int attrib, int restId) {
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
	        pstmt.setInt(i++, attrib);
	        pstmt.setInt(i++, restId);

	        // Validate for expected and return status
	        return deleteHelper(pstmt.executeUpdate(), 
	        		1, conn, sr);
		} catch (Exception e) {
			sr.setSuccess(false);
			sr.setMessage("Error: internal database issue:  "+
				e.getMessage());
			System.out.println(e.getMessage());
			return sr;
		} finally {
			sqlCleanup(pstmt,conn);
		}
	}

	public StandardResult insert(String name, int price, boolean avail,
			int restId) {
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
	        pstmt.setString(i++, name);
	        pstmt.setInt(i++, price);
	        pstmt.setBoolean(i++, avail);
	        pstmt.setInt(i++, restId);

	        // Validate for expected and return status
	        return insertHelper(pstmt.executeUpdate(), 
	        		conn, sr);
		} catch (Exception e) {
			sr.setSuccess(false);
			sr.setMessage("Error: internal database issue:  "+
				e.getMessage());
			System.out.println(e.getMessage());
			return sr;
		} finally {
			sqlCleanup(pstmt,conn);
		}
		
	}

	public StandardResult update(int attrib, String name, 
			int price, boolean avail, int restId) {
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
	        pstmt.setString(i++, name);
	        pstmt.setInt(i++, price);
	        pstmt.setBoolean(i++, avail);
	        pstmt.setInt(i++, restId);
	        pstmt.setInt(i++, attrib);

	        // Validate for expected and return status
	        return updateHelper(pstmt.executeUpdate(), 
	        		1, conn, sr);
		} catch (Exception e) {
			sr.setSuccess(false);
			sr.setMessage("Error: internal database issue:  "+
				e.getMessage());
			System.out.println(e.getMessage());
			return sr;
		} finally {
			sqlCleanup(pstmt,conn);
		}
	}
}