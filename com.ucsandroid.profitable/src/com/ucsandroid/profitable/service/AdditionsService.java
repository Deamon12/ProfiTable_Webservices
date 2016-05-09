package com.ucsandroid.profitable.service;

import com.ucsandroid.profitable.dataaccess.MenuDAO;
import com.ucsandroid.profitable.utilities.Converters;
import com.ucsandroid.profitable.utilities.StatementBuilder;

public class AdditionsService {
	
private MenuDAO menuDAO;
	
	public AdditionsService() {
		menuDAO = new MenuDAO();
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
				"SELECT "+
					"fa.attribute, fa.attr_id, fa.available "+
					"price_mod, ha.default_incl "+
				"FROM "+
					"has_attr ha, "+
					"food_attribute fa, "+
					"menu_item mi "+
				"WHERE "+
					"mi.menu_id=ha.menu_id "+
					"and fa.attr_id=ha.attr_id ";
					
		try {
			query = StatementBuilder.addBool(query, "and fa.available=", avail);
			query = StatementBuilder.addInt(query, "and mi.restaurant=", rest);
			query = StatementBuilder.addInt(query, "and mi.menu_id=", menu_item);
			query=query+"ORDER BY ha.default_incl DESC ";
			
			//log the query so we can analyze the sql generated
			System.out.println(query);
			
			return Converters.convertToString(
					getMenuDAO().fetchData(query));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return "ERROR";
		}
		
	}
	
	
	/** returns a valid MenuDAO object */
	private MenuDAO getMenuDAO() {
		if (menuDAO==null) {menuDAO = new MenuDAO();}
		return menuDAO;
	}
	
}