package com.ucsandroid.profitable.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ucsandroid.profitable.StandardResult;
import com.ucsandroid.profitable.dataaccess.AdditionsDataAccess;

public class AdditionsService {
	
	private AdditionsDataAccess additionsDataAccess;
	
	private static AdditionsService additionsService = 
			new AdditionsService();
	
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	private AdditionsService() {
		additionsDataAccess = AdditionsDataAccess.getInstance();
	}
	
	public static AdditionsService getInstance() {
		if (additionsService==null) {
			additionsService = new AdditionsService(); 
		}
		return additionsService;
	}
	
	private AdditionsDataAccess getAdditionsDataAccess() {
		if (additionsDataAccess==null) {
			additionsDataAccess = AdditionsDataAccess.getInstance();
		}
		return additionsDataAccess;
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
	
}