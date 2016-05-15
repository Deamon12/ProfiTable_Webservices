package com.ucsandroid.profitable.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ucsandroid.profitable.StandardResult;
import com.ucsandroid.profitable.dataaccess.EmployeeDataAccess;
import com.ucsandroid.profitable.utilities.Converters;
import com.ucsandroid.profitable.utilities.StatementBuilder;

public class EmployeeService {
	
	private static EmployeeDataAccess employeeDataAccess;
	private static EmployeeService employeeService = new EmployeeService();
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	private EmployeeService() {
		employeeDataAccess = EmployeeDataAccess.getInstance();
	}
	
	public static EmployeeService getInstance() {
		return employeeService;
	}
	
	public String login(String accountName, String accountPass,
			String restId) {
		StandardResult sr = new StandardResult(false, null);
		
		try {
			int restaurant = Integer.parseInt(restId);
			sr = getEmployeeDataAccess().
					login(accountName, accountPass, restaurant);
			return gson.toJson(sr);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			sr.setMessage("Error: invalid input: "+e.getMessage());
			return gson.toJson(sr);
		}
	}
	

	public String getEmployee(String accountName, String restId) {	
		StandardResult sr = new StandardResult(false, null);
		try {
			int restaurant = Integer.parseInt(restId);
			sr = getEmployeeDataAccess().
					getEmployee(accountName, restaurant);
			return gson.toJson(sr);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			sr.setMessage("Error: invalid input: "+e.getMessage());
			return gson.toJson(sr);
		}
	}
	

	public String getEmployees(String restId) {
		StandardResult sr = new StandardResult(false, null);
		try {
			int restaurant = Integer.parseInt(restId);
			sr = getEmployeeDataAccess().
					getEmployees(restaurant);
			return gson.toJson(sr);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			sr.setMessage("Error: invalid input: "+e.getMessage());
			return gson.toJson(sr);
		}
	}
	
	/** returns a valid EmployeeDataAccess object */
	private EmployeeDataAccess getEmployeeDataAccess() {
		if (employeeDataAccess==null) {employeeDataAccess = EmployeeDataAccess.getInstance();}
		return employeeDataAccess;
	}

}
