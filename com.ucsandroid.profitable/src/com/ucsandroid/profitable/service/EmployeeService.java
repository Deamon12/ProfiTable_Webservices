package com.ucsandroid.profitable.service;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ucsandroid.profitable.StandardResult;
import com.ucsandroid.profitable.dataaccess.EmployeeDataAccess;
import com.ucsandroid.profitable.utilities.GoogleCloudMessaging;

public class EmployeeService {
	
	private static EmployeeDataAccess employeeDataAccess;
	
	private static EmployeeService employeeService = 
			new EmployeeService();
	
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	private EmployeeService() {
		employeeDataAccess = EmployeeDataAccess.getInstance();
	}
	
	public static EmployeeService getInstance() {
		if (employeeService==null) {
			employeeService = new EmployeeService(); 
		}
		return employeeService;
	}
	
	private EmployeeDataAccess getEmployeeDataAccess() {
		if (employeeDataAccess==null) {
			employeeDataAccess = EmployeeDataAccess.getInstance();
		}
		return employeeDataAccess;
	}
	
	public String updateDeviceId(int emp_id, String device_id) {
		return gson.toJson(getEmployeeDataAccess().
				updateDevice(device_id, emp_id));
	}
	
	public void updateWaitStaff(int rest_id, int updateType){
		List<String> waitstaff = EmployeeService.
				getInstance().getWaitDevices(rest_id);
		List<String> managers = EmployeeService.
				getInstance().getManagerDevices(rest_id);
		GoogleCloudMessaging.sendMessage(updateType, waitstaff);
		GoogleCloudMessaging.sendMessage(updateType, managers);
	}
	
	public void updateFoodPrep(int rest_id, int updateType){
		List<String> foodPrep = EmployeeService.
				getInstance().getFoodDevices(rest_id);
		List<String> managers = EmployeeService.
				getInstance().getManagerDevices(rest_id);
		GoogleCloudMessaging.sendMessage(updateType, foodPrep);
		GoogleCloudMessaging.sendMessage(updateType, managers);
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getWaitDevices(int rest_id) {
		StandardResult sr = getEmployeeDataAccess().getDevices(rest_id, "Wait");
		if (sr.getSuccess()){
			return (List<String>)sr.getResult();
		} else {
			return new ArrayList<String>();
		}
			
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getManagerDevices(int rest_id) {
		StandardResult sr = getEmployeeDataAccess().getDevices(rest_id, "Manager");
		if (sr.getSuccess()){
			return (List<String>)sr.getResult();
		} else {
			return new ArrayList<String>();
		}
			
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getFoodDevices(int rest_id) {
		StandardResult sr = getEmployeeDataAccess().getDevices(rest_id, "Food");
		if (sr.getSuccess()){
			return (List<String>)sr.getResult();
		} else {
			return new ArrayList<String>();
		}
			
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

}