package com.ucsandroid.profitable.service;

import com.ucsandroid.profitable.dataaccess.AdditionsDataAccess;
import com.ucsandroid.profitable.utilities.Converters;
import com.ucsandroid.profitable.utilities.StatementBuilder;

public class AdditionsService {
	
	private AdditionsDataAccess additionsDataAccess;
	
	public AdditionsService() {
		additionsDataAccess = new AdditionsDataAccess();
	}
	
	/**
	 * TODO
	 * @param attr_id
	 * @param rest_id
	 * @return
	 */
	public String AttributeDEL(String attr_id, String rest_id) {
					
		try {
			Integer attrVal = Integer.parseInt(attr_id);
			Integer restVal = Integer.parseInt(rest_id);
			return getAdditionsDAO().delete(attrVal, restVal);
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "DELETE FAILURE: bad input value(s)";
		}
		
	}
	
	/**
	 * TODO
	 * @param name
	 * @param price
	 * @param available
	 * @param rest_id
	 * @return
	 */
	public String AttributePOST(String name, String price, 
			String available, String rest_id) {
					
		try {
			Integer priceVal = Integer.parseInt(price);
			Integer restVal = Integer.parseInt(rest_id);
			Boolean availValue = Boolean.valueOf(available);
			if (name.length()==0) {
				return "INSERT FAILURE: zero length attribute name";
			} else {
				return getAdditionsDAO().insert(name, priceVal,
						availValue, restVal);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "INSERT FAILURE: bad input value(s)";
		}
		
	}
	
	/**
	 * TODO
	 * @param attr_id
	 * @param name
	 * @param price
	 * @param available
	 * @param rest_id
	 * @return
	 */
	public String AttributePUT(String attr_id,
			String name, String price, 
			String available, String rest_id) {
					
		try {
			Integer attrVal = Integer.parseInt(attr_id);
			Integer priceVal = Integer.parseInt(price);
			Integer restVal = Integer.parseInt(rest_id);
			Boolean availValue = Boolean.valueOf(available);
			if (name.length()==0) {
				return "UPDATE FAILURE: zero length attribute name";
			} else {
				return getAdditionsDAO().update(attrVal, name, 
						priceVal, availValue, restVal);
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
	public String AttributeGet(String menu_item, String avail,
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
					getAdditionsDAO().fetchData(query));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "ERROR";
		}
	}
	
	/**
	 * TODO
	 * @param menu_item
	 * @param avail
	 * @param rest
	 * @return
	 */
	public String AttributeGetRest(String avail,
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
					getAdditionsDAO().fetchData(query));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "ERROR";
		}
	}
	
	/** returns a valid AdditionsDataAccess object */
	private AdditionsDataAccess getAdditionsDAO() {
		if (additionsDataAccess==null) {additionsDataAccess = new AdditionsDataAccess();}
		return additionsDataAccess;
	}
	
}