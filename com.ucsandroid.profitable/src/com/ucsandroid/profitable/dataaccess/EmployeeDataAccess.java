package com.ucsandroid.profitable.dataaccess;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.ucsandroid.profitable.StandardResult;
import com.ucsandroid.profitable.entities.Employee;

public class EmployeeDataAccess extends MainDataAccess {
	
	private static EmployeeDataAccess employeeDataAccess = 
			new EmployeeDataAccess();
	
	private EmployeeDataAccess() {super();}
	
	public static EmployeeDataAccess getInstance() {
		return employeeDataAccess;
	}

	@SuppressWarnings("unused")
	private static String insertStatement = 
		"INSERT INTO employee "+
		"(account_name, emp_type, first_name, last_name, "+
			"password, restaurant) "+ 
		" VALUES(?,?,?,?,?,?)";
		
	@SuppressWarnings("unused")
	private static String updateStatement =
		"UPDATE "+
			"employee "+
		"SET "+
			"account_name=?, emp_type=?, first_name=?, last_name=? "+
			"password=?, restaurant=? "+
		"WHERE "+
			"emp_id = ?";
		
	@SuppressWarnings("unused")
	private static String deleteStatement =
		"DELETE FROM employee "+
		"WHERE emp_id = ? and restaurant = ?";
	
	private static String loginStatement =
		"SELECT * "+
		"FROM employee "+
		"WHERE "+
			"account_name=? "+
			"AND password=? "+
			"AND restaurant=?";

	private static String getEmployee = 
			"SELECT * FROM employee "+
			"WHERE account_name=? and "+
			"restaurant = ? ";
	
	private static String getEmployees = 
			"SELECT * FROM employee "+
			"WHERE "+
				"restaurant = ? "+
			"ORDER BY emp_type ASC, account_name ASC";
	
	private static String getDevices =
			"select curr_device from employee "+
			"where emp_type = ? and restaurant = ? "+
			"and length(curr_device)>0";
	
	private static String updateDevice = 
			"update employee set curr_device = ? where emp_id = ? ";
	
	public StandardResult updateDevice(String deviceId, int emp_id) {
		StandardResult sr = new StandardResult(false, null);
		try {
			// Open the connection
			conn = connUtil.getConnection();
			// Begin transaction
	        conn.setAutoCommit(false);
	        // Create the prepared statement
	        pstmt = conn.prepareStatement(updateDevice);
	        // Set the variable parameters
	        int i = 1;
	        pstmt.setString(i++, deviceId);
	        pstmt.setInt(i++, emp_id);

	        // Validate for expected and return status
	        return updateHelper(pstmt.executeUpdate(), 
	        		1, conn, sr);
		} catch (Exception e) {
			return catchErrorAndSetSR(sr, e);
		} finally {
			sqlCleanup(pstmt,conn);
		}
	}
	
	public StandardResult getDevices(int restaurant, String employeeType) {
		StandardResult sr = new StandardResult(false, null);
		ResultSet results = null;
		try {
			conn = connUtil.getConnection();
	        pstmt = conn.prepareStatement(getDevices);
	        int i = 1;
	        pstmt.setString(i++, employeeType);
	        pstmt.setInt(i++, restaurant);
	        results = pstmt.executeQuery();
	        //System.out.println("Query: "+pstmt.toString());
	        List<String> devices = new ArrayList<String>();
	        
	        while (results.next()) { 
	        	String empDevice = results.getString("curr_device");
	        	devices.add(empDevice);
	        } 
	        //System.out.println(employeeType+" devices:");
	        //for (String s : devices){
	        	//System.out.println(s);
	        //}
	        	
	        return successReturnSR(sr, devices);
		} catch (Exception e) {
			return catchErrorAndSetSR(sr, e);
		} finally {
			sqlCleanup(pstmt,results,conn);
		}
	}
	
	public StandardResult login(String accountName, String password, 
			int restaurant) {
		StandardResult sr = new StandardResult(false, null);
		ResultSet results = null;
		try {
			conn = connUtil.getConnection();
	        pstmt = conn.prepareStatement(loginStatement);
	        int i = 1;
	        pstmt.setString(i++, accountName);
	        pstmt.setString(i++, password);
	        pstmt.setInt(i++, restaurant);
	        results = pstmt.executeQuery();
	        
	        if (results.next()) { 
	        	String empType = results.getString("emp_type");
	        	String empAccount = results.getString("account_name");
	        	String empFName = results.getString("first_name");
	        	String empLName = results.getString("last_name");
	        	int empId = results.getInt("emp_id");
	        	int restId = results.getInt("restaurant");
	        	Employee emp = new Employee(empId,empType,empAccount,
	        			empFName,empLName,restId );
	        	return successReturnSR(sr, emp);
	        } else {
	        	sr.setMessage("account incorrect or not found");
	        	return successReturnSR(sr, null);
	        }
		} catch (Exception e) {
			return catchErrorAndSetSR(sr, e);
		} finally {
			sqlCleanup(pstmt,results,conn);
		}
	}
	
	public StandardResult getEmployee(String accountName, int restaurant) {
		StandardResult sr = new StandardResult(false, null);
		ResultSet results = null;
		try {
			conn = connUtil.getConnection();
	        pstmt = conn.prepareStatement(getEmployee);
	        int i = 1;
	        pstmt.setString(i++, accountName);
	        pstmt.setInt(i++, restaurant);
	        results = pstmt.executeQuery();
	        
	        if (results.next()) { 
	        	String empType = results.getString("emp_type");
	        	String empAccount = results.getString("account_name");
	        	String empFName = results.getString("first_name");
	        	String empLName = results.getString("last_name");
	        	String empPass = results.getString("password");
	        	int empId = results.getInt("emp_id");
	        	int restId = results.getInt("restaurant");
	        	Employee emp = new Employee(empId,empType,empAccount,
	        			empFName,empLName,empPass,restId );
	        	return successReturnSR(sr, emp);
	        } else {
	        	sr.setMessage("account incorrect or not found");
	        	return successReturnSR(sr,null);
	        }
		} catch (Exception e) {
			return catchErrorAndSetSR(sr, e);
		} finally {
			sqlCleanup(pstmt,results,conn);
		}
	}
	
	public StandardResult getEmployees(int restaurant) {
		StandardResult sr = new StandardResult(false, null);
		ResultSet results = null;
		try {
			conn = connUtil.getConnection();
	        pstmt = conn.prepareStatement(getEmployees);
	        int i = 1;
	        pstmt.setInt(i++, restaurant);
	        results = pstmt.executeQuery();
	        
	        List<Employee> employees = new ArrayList<Employee>();
	        
	        while (results.next()) { 
	        	String empType = results.getString("emp_type");
	        	String empAccount = results.getString("account_name");
	        	String empFName = results.getString("first_name");
	        	String empLName = results.getString("last_name");
	        	String empPass = results.getString("password");
	        	int empId = results.getInt("emp_id");
	        	int restId = results.getInt("restaurant");
	        	Employee emp = new Employee(empId,empType,empAccount,
	        			empFName,empLName,empPass,restId );
	        	employees.add(emp);
	        } 
	        return successReturnSR(sr, employees);
		} catch (Exception e) {
			return catchErrorAndSetSR(sr, e);
		} finally {
			sqlCleanup(pstmt,results,conn);
		}
	}
}
