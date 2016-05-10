package com.ucsandroid.profitable.dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class AdditionsDAO extends MainDAO {
	
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
		"WHERE attr_id = ?";

	public AdditionsDAO() {
		super();
	}
	
	
	public String additionDelete(int attrib) {
		
		try {
			Connection conn = connUtil.getConnection();
			
			// Begin transaction
	        conn.setAutoCommit(false);
	        
	        // Create the prepared statement and use it to
	        // INSERT the Course attributes INTO the Course table.
	        PreparedStatement pstmt = conn.prepareStatement(deleteStatement);
	        
	        pstmt.setInt(1, attrib);
	        int rowCount = pstmt.executeUpdate();
	
	        String insertStatus = deleteHelper(rowCount, 1, conn);
	        conn.setAutoCommit(true);
			return insertStatus;
		} catch (Exception e) {
			return "Insert failure, SQL issue";
		}
		
	}
	
	/**
	 * TODO
	 * @param name
	 * @param price
	 * @param avail
	 * @param restId
	 * @return
	 */
	public String additionInsert(String name, int price, boolean avail,
			int restId) {
		
		try {
			Connection conn = connUtil.getConnection();
			
			// Begin transaction
	        conn.setAutoCommit(false);
	        
	        // Create the prepared statement and use it to
	        // INSERT the Course attributes INTO the Course table.
	        PreparedStatement pstmt = conn.prepareStatement(insertStatement);
	        
	        int i = 1;
	        pstmt.setString(i++, name);
	        pstmt.setInt(i++, price);
	        pstmt.setBoolean(i++, avail);
	        pstmt.setInt(i++, restId);
	        int rowCount = pstmt.executeUpdate();
	
	        String insertStatus = insertHelper(rowCount, conn);
	        conn.setAutoCommit(true);
			return insertStatus;
		} catch (Exception e) {
			return "Insert failure, SQL issue";
		}
		
	}
	
	/**
	 * TODO
	 * @param attrib
	 * @param name
	 * @param price
	 * @param avail
	 * @param restId
	 * @return
	 */
	public String additionUpdate(int attrib, String name, 
			int price, boolean avail, int restId) {
		
		try {
			Connection conn = connUtil.getConnection();
			
			// Begin transaction
	        conn.setAutoCommit(false);
	        
	        // Create the prepared statement and use it to
	        // INSERT the Course attributes INTO the Course table.
	        PreparedStatement pstmt = conn.prepareStatement(updateStatement);
	        
	        int i = 1;
	        pstmt.setString(i++, name);
	        pstmt.setInt(i++, price);
	        pstmt.setBoolean(i++, avail);
	        pstmt.setInt(i++, restId);
	        pstmt.setInt(i++, attrib);
	        int rowCount = pstmt.executeUpdate();
	
	        String updateStatus = updateHelper(rowCount, 1, conn);
	        conn.setAutoCommit(true);
			return updateStatus;
		} catch (Exception e) {
			return "Insert failure, SQL issue";
		}
		
	}
	
}