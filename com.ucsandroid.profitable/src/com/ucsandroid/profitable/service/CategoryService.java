package com.ucsandroid.profitable.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ucsandroid.profitable.StandardResult;
import com.ucsandroid.profitable.dataaccess.CategoryDataAccess;


public class CategoryService {
	
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	private CategoryDataAccess categoryDataAccess;
	
	private static CategoryService categoryService = 
			new CategoryService();
	
	private CategoryService() {
		categoryDataAccess = CategoryDataAccess.getInstance();
	}
	
	public static CategoryService getInstance() {
		if (categoryService==null) {
			categoryService = new CategoryService(); 
		}
		return categoryService;
	}
	
	private CategoryDataAccess getCategoryDataAccess() {
		if (categoryDataAccess==null) {
			categoryDataAccess = CategoryDataAccess.getInstance();
		}
		return categoryDataAccess;
	}
	
	public String delete(String cat_id, String rest_id) {
		StandardResult sr = new StandardResult(false, null);
		try {
			Integer catVal = Integer.parseInt(cat_id);
			Integer restVal = Integer.parseInt(rest_id);
			return gson.toJson(getCategoryDataAccess().delete(catVal, restVal));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			sr.setMessage("Error: invalid input: "+e.getMessage());
			return gson.toJson(sr);
		}
	}
	
	public String insert(String catName, String rest_id) {
		StandardResult sr = new StandardResult(false, null);	
		try {
			Integer restVal = Integer.parseInt(rest_id);
			return gson.toJson(getCategoryDataAccess().insert(catName, restVal));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			sr.setMessage("Error: invalid input: "+e.getMessage());
			return gson.toJson(sr);
		}
	}
	
	public String update(String catId,
			String catName, String rest_id) {
		StandardResult sr = new StandardResult(false, null);	
		try {
			Integer catVal = Integer.parseInt(catId);
			Integer restVal = Integer.parseInt(rest_id);
			return gson.toJson(getCategoryDataAccess().update(catVal, catName, 
					restVal));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			sr.setMessage("Error: invalid input: "+e.getMessage());
			return gson.toJson(sr);
		}
	}
		
}