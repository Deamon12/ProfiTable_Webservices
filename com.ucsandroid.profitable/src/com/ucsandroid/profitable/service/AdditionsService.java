package com.ucsandroid.profitable.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ucsandroid.profitable.StandardResult;
import com.ucsandroid.profitable.dataaccess.AdditionsDataAccess;
import com.ucsandroid.profitable.utilities.Converters;
import com.ucsandroid.profitable.utilities.StatementBuilder;

public class AdditionsService {
	
	private AdditionsDataAccess additionsDataAccess;
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	public AdditionsService() {
		additionsDataAccess = new AdditionsDataAccess();
	}

	public String attributeDelete(String attr_id, String rest_id) {
		StandardResult sr = new StandardResult(false, null);
		try {
			Integer attrVal = Integer.parseInt(attr_id);
			Integer restVal = Integer.parseInt(rest_id);
			return gson.toJson(getAdditionsDataAccess().delete(attrVal, restVal));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			sr.setMessage("Error: invalid input: "+e.getMessage());
			return gson.toJson(sr);
		}			
	}

	public String attributeInsert(String name, String price, 
			String available, String rest_id) {
		StandardResult sr = new StandardResult(false, null);	
		try {
			Integer priceVal = Integer.parseInt(price);
			Integer restVal = Integer.parseInt(rest_id);
			Boolean availValue = Boolean.valueOf(available);
			return gson.toJson(getAdditionsDataAccess().insert(name, priceVal,
					availValue, restVal));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			sr.setMessage("Error: invalid input: "+e.getMessage());
			return gson.toJson(sr);
		}
	}

	public String attributeUpdate(String attr_id,
			String name, String price, 
			String available, String rest_id) {
		StandardResult sr = new StandardResult(false, null);	
		try {
			Integer attrVal = Integer.parseInt(attr_id);
			Integer priceVal = Integer.parseInt(price);
			Integer restVal = Integer.parseInt(rest_id);
			Boolean availValue = Boolean.valueOf(available);
			return gson.toJson(getAdditionsDataAccess().update(attrVal, name, 
					priceVal, availValue, restVal));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			sr.setMessage("Error: invalid input: "+e.getMessage());
			return gson.toJson(sr);
		}
	}

	@Deprecated
	//Getting rid of this in favor of including this data in the menu item return
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
					getAdditionsDataAccess().fetchData(query));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "ERROR";
		}
	}

	public String AttributeGetRest(String avail,
			String rest_id) {
		StandardResult sr = new StandardResult(false, null);
		try {
			Integer restVal = Integer.parseInt(rest_id);
			boolean availVal;
			if (avail!=null){
				availVal = Boolean.parseBoolean(avail);
				//run with available mod
				sr = getAdditionsDataAccess().getAdditions(restVal,availVal);
			} else {
				//run without
				sr = getAdditionsDataAccess().getAdditions(restVal);
			}
			return gson.toJson(sr);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			sr.setMessage("Error: invalid input: "+e.getMessage());
			return gson.toJson(sr);
		}
	}
	
	/** returns a valid AdditionsDataAccess object */
	private AdditionsDataAccess getAdditionsDataAccess() {
		if (additionsDataAccess==null) {additionsDataAccess = new AdditionsDataAccess();}
		return additionsDataAccess;
	}
	
}