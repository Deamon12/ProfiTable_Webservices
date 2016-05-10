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
		"WHERE attr_id = ? and restaurant = ?";

	public AdditionsDAO() {
		super();
	}
	
	/**
	 * TODO
	 * @param attrib
	 * @param restId
	 * @return
	 */
	public String additionDelete(int attrib, int restId) {
		
		PreparedStatement pstmt = null;
		Connection conn = null;
		
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
	        // Attempt execution, return number affected 
	        int rowCount = pstmt.executeUpdate();
	        // Validate for expected and return status
	        String insertStatus = deleteHelper(rowCount, 1, conn);
	        conn.setAutoCommit(true);
			return insertStatus;
		} catch (Exception e) {
			return "Insert failure, SQL issue";
		} finally {
			sqlCloser(pstmt,conn);
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
		PreparedStatement pstmt = null;
		Connection conn = null;
		
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
	        // Attempt execution, return number affected 
	        int rowCount = pstmt.executeUpdate();
	        // Validate for expected and return status
	        String insertStatus = insertHelper(rowCount, conn);
	        conn.setAutoCommit(true);
			return insertStatus;
		} catch (Exception e) {
			return "Insert failure, SQL issue";
		} finally {
			sqlCloser(pstmt,conn);
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
		PreparedStatement pstmt = null;
		Connection conn = null;
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
	        // Attempt execution, return number affected 
	        int rowCount = pstmt.executeUpdate();
	        // Validate for expected and return status
	        String updateStatus = updateHelper(rowCount, 1, conn);
	        conn.setAutoCommit(true);
			return updateStatus;
		} catch (Exception e) {
			return "Insert failure, SQL issue";
		} finally {
			sqlCloser(pstmt,conn);
		}
	}
}