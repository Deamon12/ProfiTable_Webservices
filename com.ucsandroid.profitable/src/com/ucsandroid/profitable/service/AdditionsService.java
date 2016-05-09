package com.ucsandroid.profitable.service;

import com.ucsandroid.profitable.dataaccess.MenuDAO;
import com.ucsandroid.profitable.utilities.Converters;

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
			boolean available = false;
			int restId = 0;
			String menuId = "";
			
			if (avail!=null && avail.length()>0) {
				available = Boolean.valueOf(avail);
				query=query+"and fa.available="+available+" ";
			}
			
			if (rest!=null && rest.length()>0) {
				restId = Integer.parseInt(rest);
				query=query+"and mi.restaurant="+restId+" ";
			}
			
			if (menu_item!=null && menu_item.length()>0) {
				menuId = ""+Integer.parseInt(menu_item);
				query=query+"and mi.menu_id="+menuId+" ";
			}
			
			query=query+"ORDER BY ha.default_incl DESC ";
			
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