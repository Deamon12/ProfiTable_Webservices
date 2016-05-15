package com.ucsandroid.profitable.entities;

import java.util.ArrayList;
import java.util.List;

public class MenuItem {
	
	private int	menuItemId;
	private String menu_name;
	private String menuItemdescription;
	private int menuItemPrice;
	private List<FoodAddition> defaultAdditions;
	private List<FoodAddition> optionalAdditions;
	
	public MenuItem() {
		super();
	}
	
	public MenuItem(int menu_id, String menu_name, String description, int price) {
		super();
		this.menuItemId = menu_id;
		this.menu_name = menu_name;
		this.menuItemdescription = description;
		this.menuItemPrice = price;
		this.defaultAdditions = new ArrayList<FoodAddition>();
		this.optionalAdditions = new ArrayList<FoodAddition>();
	}
	
	public MenuItem(int menu_id, String menu_name, String description, 
			int price, List<FoodAddition> defaultAdditions,
			List<FoodAddition> optionalAdditions) {
		super();
		this.menuItemId = menu_id;
		this.menu_name = menu_name;
		this.menuItemdescription = description;
		this.menuItemPrice = price;
		this.defaultAdditions = defaultAdditions;
		this.optionalAdditions = optionalAdditions;
	}

	public int getId() {return menuItemId;	}
	public void setId(int menu_id) {this.menuItemId = menu_id;	}
	public String getName() {return menu_name;	}
	public void setName(String menu_name) {this.menu_name = menu_name;	}
	public String getDescription() {return menuItemdescription;	}
	public void setDescription(String description) {this.menuItemdescription = description;	}
	public int getPrice() {return menuItemPrice;	}
	public void setPrice(int price) {this.menuItemPrice = price;	}
	public List<FoodAddition> getDefaultAdditions() {return defaultAdditions;	}
	public void setDefaultAdditions(List<FoodAddition> defaultAdditions) {this.defaultAdditions = defaultAdditions;	}
	public List<FoodAddition> getOptionalAdditions() {return optionalAdditions;	}
	public void setOptionalAdditions(List<FoodAddition> optionalAdditions) {this.optionalAdditions = optionalAdditions;	}
}