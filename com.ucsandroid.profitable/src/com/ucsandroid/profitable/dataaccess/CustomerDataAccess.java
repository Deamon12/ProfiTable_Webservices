package com.ucsandroid.profitable.dataaccess;

import java.sql.ResultSet;
import java.sql.Statement;

import com.ucsandroid.profitable.StandardResult;
import com.ucsandroid.profitable.entities.Customer;

public class CustomerDataAccess extends MainDataAccess {
	
	private static CustomerDataAccess customerDataAccess = 
			new CustomerDataAccess();
	
	private CustomerDataAccess() {super();}
	
	public static CustomerDataAccess getInstance() {
		return customerDataAccess;
	}
	
	private static String insertNewCustomer = 
		"INSERT INTO Customer (order_id) VALUES( ? )";
	
	public StandardResult createCustomer(int tabId) {
		StandardResult sr = new StandardResult(false, null);
		ResultSet generatedKeys = null;
		try {
			// Open the connection
			conn = connUtil.getConnection();
			// Begin transaction
	        conn.setAutoCommit(false);
	        // Create the prepared statement
	        pstmt = conn.prepareStatement(insertNewCustomer,
                    Statement.RETURN_GENERATED_KEYS);
	        // Set the variable parameters
	        int i = 1;
	        pstmt.setInt(i++, tabId);
	        // Validate for expected and return status
	        int createdRows = pstmt.executeUpdate();
	        generatedKeys = pstmt.getGeneratedKeys();
	        if (generatedKeys.next()) {
	        	int newKey =  generatedKeys.getInt(1);
	        	Customer c = new Customer(newKey,tabId);
	        	sr.setResult(c);
	        	return createHelper(createdRows, 
	        			conn, sr);
	        } else {
	        	sr.setMessage("could not create customer");
	        	return sr;
	        }
		} catch (Exception e) {
			return catchErrorAndSetSR(sr, e);
		} finally {
			sqlCleanup(pstmt,generatedKeys,conn);
		}
	}

}