package com.ucsandroid.profitable.dataaccess;

public class EmployeeDataAccess extends MainDataAccess {

	private static String insertStatement = 
		"INSERT INTO Food_attribute "+
		"(attribute, price_mod, Available, restaurant) "+ 
		" VALUES(?,?,?,?)";
		
	private static String updateStatement =
		"UPDATE "+
			"food_attribute "+
		"SET "+
			"attribute=?, price_mod=?, Available=?, restaurant=? "+
		"WHERE "+
			"attr_id = ?";
		
	private static String deleteStatement =
		"DELETE FROM food_attribute "+
		"WHERE attr_id = ? and restaurant = ?";
	
	public EmployeeDataAccess() {
		super();
	}
}
