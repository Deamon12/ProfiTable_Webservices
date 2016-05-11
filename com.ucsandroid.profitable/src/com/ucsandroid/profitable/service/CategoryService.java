package com.ucsandroid.profitable.service;

import com.ucsandroid.profitable.dataaccess.CategoryDataAccess;


public class CategoryService {
	
	private CategoryDataAccess categoryDataAccess;
	
	public CategoryService() {
		categoryDataAccess = new CategoryDataAccess();
	}
	
	/**
	 * TODO
	 * @param cat_id
	 * @param rest_id
	 * @return
	 */
	public String delete(String cat_id, String rest_id) {
		
		try {
			Integer catVal = Integer.parseInt(cat_id);
			Integer restVal = Integer.parseInt(rest_id);
			return getCategoryDataAccess().delete(catVal, restVal);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "DELETE FAILURE: bad input value(s)";
		}
		
	}
	
	/**
	 * TODO
	 * @param catName
	 * @param rest_id
	 * @return
	 */
	public String insert(String catName, String rest_id) {
					
		try {
			Integer restVal = Integer.parseInt(rest_id);
			if (catName.length()==0) {
				return "INSERT FAILURE: zero length category name";
			} else {
				return getCategoryDataAccess().insert(catName, restVal);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "INSERT FAILURE: bad input value(s)";
		}
		
	}
	
	/**
	 * TODO
	 * @param catId
	 * @param catName
	 * @param rest_id
	 * @return
	 */
	public String update(String catId,
			String catName, String rest_id) {
					
		try {
			Integer catVal = Integer.parseInt(catId);
			Integer restVal = Integer.parseInt(rest_id);
			if (catName.length()==0) {
				return "UPDATE FAILURE: zero length category name";
			} else {
				return getCategoryDataAccess().update(catVal, catName, 
					restVal);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "UPDATE FAILURE: bad input value(s)";
		}
		
	}
	
	/**
	 * TODO
	 * @param menu_item
	 * @param avail
	 * @param rest
	 * @return
	 */
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
	
	/**
	 * TODO
	 * @param menu_item
	 * @param avail
	 * @param rest
	 * @return
	 */
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
