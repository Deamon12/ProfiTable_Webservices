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
			"restaurant = ? ";
	
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
