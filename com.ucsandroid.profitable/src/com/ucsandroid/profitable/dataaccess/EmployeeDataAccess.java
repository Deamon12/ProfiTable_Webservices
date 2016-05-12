package com.ucsandroid.profitable.dataaccess;

import java.sql.ResultSet;

public class EmployeeDataAccess extends MainDataAccess {

	private static String insertStatement = 
		"INSERT INTO employee "+
		"(account_name, emp_type, first_name, last_name, "+
			"password, restaurant) "+ 
		" VALUES(?,?,?,?,?,?)";
		
	private static String updateStatement =
		"UPDATE "+
			"employee "+
		"SET "+
			"account_name=?, emp_type=?, first_name=?, last_name=? "+
			"password=?, restaurant=? "+
		"WHERE "+
			"emp_id = ?";
		
	private static String deleteStatement =
		"DELETE FROM employee "+
		"WHERE emp_id = ? and restaurant = ?";
	
	private static String loginStatement =
		"SELECT emp_type "+
		"FROM employee "+
		"WHERE "+
			"account_name=? "+
			"AND password=? "+
			"AND restaurant=?";
	
	public EmployeeDataAccess() {
		super();
	}
	
	public String login(String accountName, String password, 
			int restaurant) {
		ResultSet results = null;
		try {
			// Open the connection
			conn = connUtil.getConnection();
			// Begin transaction
	        conn.setAutoCommit(false);
	        // Create the prepared statement
	        pstmt = conn.prepareStatement(loginStatement);
	        // Set the variable parameters
	        int i = 1;
	        pstmt.setString(i++, accountName);
	        pstmt.setString(i++, password);
	        pstmt.setInt(i++, restaurant);
	        
	        results = pstmt.executeQuery();
	        String loginStatus = "FAILED";
	        if (results.next()) 
	        	{ loginStatus = results.getString("emp_type"); }
	        System.out.println(loginStatus);

			return loginStatus;
		} catch (Exception e) {
			return "FAILED";
		} finally {
			sqlCleanup(pstmt,conn);
		}
	}
}
