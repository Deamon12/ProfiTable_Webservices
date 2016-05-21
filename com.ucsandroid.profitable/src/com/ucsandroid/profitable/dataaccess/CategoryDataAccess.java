package com.ucsandroid.profitable.dataaccess;

import com.ucsandroid.profitable.StandardResult;

public class CategoryDataAccess extends MainDataAccess {
	
	private static CategoryDataAccess categoryDataAccess = 
			new CategoryDataAccess();
	
	private CategoryDataAccess() {
		super();
	}
	
	public static CategoryDataAccess getInstance() {
		return categoryDataAccess;
	}
	
	private static String insertStatement = 
		"INSERT INTO Category "+
		"(cat_name, restaurant) "+ 
		" VALUES(?,?)";
	
	private static String updateStatement =
		"UPDATE "+
			"Category "+
		"SET "+
			"cat_name=?, restaurant=? "+
		"WHERE "+
			"cat_id = ?";
	
	private static String deleteStatement =
		"DELETE FROM Category "+
		"WHERE cat_id = ? and restaurant = ?";
		
	public StandardResult delete(int catId, int restId) {
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
	        pstmt.setInt(i++, catId);
	        pstmt.setInt(i++, restId);

			return deleteHelper(pstmt.executeUpdate(), 
	        		1, conn, sr);
		} catch (Exception e) {
			return catchErrorAndSetSR(sr, e);
		} finally {
			sqlCleanup(pstmt,conn);
		}
	}

	public StandardResult insert(String catName, int restId) {
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
	        pstmt.setString(i++, catName);
	        pstmt.setInt(i++, restId);
	        // Validate for expected and return status
	        return insertHelper(pstmt.executeUpdate(), 
	        		conn, sr);
		} catch (Exception e) {
			return catchErrorAndSetSR(sr, e);
		} finally {
			sqlCleanup(pstmt,conn);
		}
		
	}

	public StandardResult update(int catId, String catName, 
			int restId) {
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
	        pstmt.setString(i++, catName);
	        pstmt.setInt(i++, restId);
	        pstmt.setInt(i++, catId);

	        // Validate for expected and return status
	        return updateHelper(pstmt.executeUpdate(), 
	        		1, conn, sr);
		} catch (Exception e) {
			return catchErrorAndSetSR(sr, e);
		} finally {
			sqlCleanup(pstmt,conn);
		}
	}

}