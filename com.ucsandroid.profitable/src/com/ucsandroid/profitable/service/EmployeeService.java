package com.ucsandroid.profitable.service;

import com.ucsandroid.profitable.dataaccess.EmployeeDataAccess;
import com.ucsandroid.profitable.utilities.Converters;
import com.ucsandroid.profitable.utilities.StatementBuilder;

public class EmployeeService {
	
	private EmployeeDataAccess employeeDataAccess;
	
	public EmployeeService() {
		employeeDataAccess = new EmployeeDataAccess();
	}
	
	/**
	 * 
	 * @param restId
	 * @param accountName
	 * @return
	 */
	public String getEmployee(String accountName, String restId) {
		String query = 
				"SELECT * "+
				"FROM Employee "+
				"WHERE account_name= '"+accountName+"'";
					
		try {
			query = StatementBuilder.addInt(query, 
					" and restaurant= ", restId);
			
			//log the query so we can analyze the sql generated
			System.out.println(query);
			
			return Converters.convertToString(
					getEmployeeDataAccess().fetchData(query));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "ERROR";
		}
		
	}
	
	/**
	 * 
	 * @param restId
	 * @return
	 */
	public String getEmployees(String restId) {
		String query = 
				"SELECT * "+
				"FROM Employee "+
				"WHERE ";
					
		try {
			query = StatementBuilder.addInt(query, 
					"restaurant=", restId);
			
			//log the query so we can analyze the sql generated
			System.out.println(query);
			
			return Converters.convertToString(
					getEmployeeDataAccess().fetchData(query));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "ERROR";
		}
		
	}
	
	/** returns a valid EmployeeDataAccess object */
	private EmployeeDataAccess getEmployeeDataAccess() {
		if (employeeDataAccess==null) {employeeDataAccess = new EmployeeDataAccess();}
		return employeeDataAccess;
	}

}
