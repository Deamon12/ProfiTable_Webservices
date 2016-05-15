package com.ucsandroid.profitable.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.ucsandroid.profitable.service.EmployeeService;

@Path ("/employee")
public class EmployeeController {
	
	private EmployeeService employeeService = new EmployeeService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String fetchEmployee(
			@QueryParam("account_name") String account_name, 
			@QueryParam("rest_id") String rest_id
			) {
		if (account_name!=null) {
			return employeeService.getEmployee(account_name, rest_id); 
		} else {
			return employeeService.getEmployees(rest_id); 
		}
	}
	
	@Path ("/login")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String employeeLogin(
			@QueryParam("account_name") String account_name, 
			@QueryParam("account_pass") String account_pass,
			@QueryParam("rest_id") String rest_id
			) {
		if (account_name!=null && account_pass!=null && rest_id!=null) {
			return employeeService.login(account_name, account_pass, rest_id); 
		} else {
			return "FAILURE"; 
		}
	}

}
