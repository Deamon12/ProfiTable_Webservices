package com.ucsandroid.profitable.dataaccess;

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
	
	/**
	 * TODO
	 * @param attrib
	 * @param restId
	 * @return
	 */
	public String delete(int attrib, int restId) {
		
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
	        String deleteStatus = deleteHelper(pstmt.executeUpdate(), 
	        		1, conn);
			return deleteStatus;
		} catch (Exception e) {
			return "Delete failure, SQL issue";
		} finally {
			sqlCleanup(pstmt,conn);
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
	public String insert(String name, int price, boolean avail,
			int restId) {
		
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
	        String insertStatus = insertHelper(pstmt.executeUpdate(), 
	        		conn);
			return insertStatus;
		} catch (Exception e) {
			return "Insert failure, SQL issue";
		} finally {
			sqlCleanup(pstmt,conn);
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
	public String update(int attrib, String name, 
			int price, boolean avail, int restId) {

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
	        String updateStatus = updateHelper(pstmt.executeUpdate(), 
	        		1, conn);
			return updateStatus;
		} catch (Exception e) {
			return "Update failure, SQL issue";
		} finally {
			sqlCleanup(pstmt,conn);
		}
	}
}