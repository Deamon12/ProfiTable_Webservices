package com.ucsandroid.profitable.service;

import java.sql.ResultSet;
import java.sql.Statement;

import org.json.JSONArray;

import com.ucsandroid.profitable.dataaccess.MenuDAO;

public class MenuService {
	
	
	private MenuDAO menuDAO;
	
	
	public MenuService() {
		menuDAO = new MenuDAO();
	}
	
	public JSONArray fetchMenu() {
		
	
		
		return menuDAO.fetchMenu();
	}
	
	
	
	
	
	
	
	
	/** returns a valid MenuDAO object */
	private MenuDAO getMenuDAO() {
		if (menuDAO==null) {menuDAO = new MenuDAO();}
		return menuDAO;
	}

}