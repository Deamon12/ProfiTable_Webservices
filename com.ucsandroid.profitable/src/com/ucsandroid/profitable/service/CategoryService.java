package com.ucsandroid.profitable.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ucsandroid.profitable.StandardResult;
import com.ucsandroid.profitable.dataaccess.CategoryDataAccess;


public class CategoryService {
	
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	private CategoryDataAccess categoryDataAccess;
	
	public CategoryService() {
		categoryDataAccess = new CategoryDataAccess();
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
	
	/*
	public String CategoryGet(String menu_item, String avail,
			String rest) {
		
		String query = 
				"SELECT distinct "+
					"fa.attribute, fa.attr_id, fa.available, "+
					"fa.price_mod, ha.default_incl "+
				"FROM "+
					"has_attr ha, "+
					"food_attribute fa, "+
					"menu_item mi "+
				"WHERE "+
					"mi.menu_id=ha.menu_id "+
					"and fa.attr_id=ha.attr_id ";
					
		try {
			query = StatementBuilder.addBool(query, 
					"and fa.available=", avail);
			query = StatementBuilder.addInt(query, 
					"and mi.restaurant=", rest);
			query = StatementBuilder.addInt(query, 
					"and mi.menu_id=", menu_item);
			query=query+
					"ORDER BY ha.default_incl DESC, fa.attr_id ASC ";
			
			//log the query so we can analyze the sql generated
			System.out.println(query);
			
			return Converters.convertToString(
					getCategoryDataAccess().fetchData(query));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "ERROR";
		}
	}*/
	
	/*
	public String CategoryGetRest(String avail,
			String rest) {
		
		String query = 
				"SELECT distinct "+
					"fa.attribute, fa.attr_id, fa.available, "+
					"fa.price_mod "+
				"FROM "+
					"food_attribute fa "+
				"WHERE ";
					
		try {
			//mandatory
			query = StatementBuilder.addInt(query, 
					"fa.restaurant=", rest);
			//optional
			query = StatementBuilder.addBool(query, 
					"and fa.available=", avail);
			query=query+"ORDER BY fa.attr_id ASC ";
			
			//log the query so we can analyze the sql generated
			System.out.println(query);
			
			return Converters.convertToString(
					getCategoryDataAccess().fetchData(query));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "ERROR";
		}
	}*/
	
	/** returns a valid CategoryDataAccess object */
	private CategoryDataAccess getCategoryDataAccess() {
		if (categoryDataAccess==null) {categoryDataAccess = new CategoryDataAccess();}
		return categoryDataAccess;
	}
	

}
