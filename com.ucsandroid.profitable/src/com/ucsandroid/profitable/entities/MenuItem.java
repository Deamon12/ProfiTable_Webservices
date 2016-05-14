package com.ucsandroid.profitable.entities;

import java.util.List;

public class MenuItem {
	
	private int	menu_id;
	private String menu_name;
	private String description;
	private int price;
	private List<FoodAddition> defaultAdditions;
	private List<FoodAddition> optionalAdditions;
	
	public MenuItem() {
		super();
	}
	
	public MenuItem(int menu_id, String menu_name, String description, int price) {
		super();
		this.menu_id = menu_id;
		this.menu_name = menu_name;
		this.description = description;
		this.price = price;
	}
	
	public MenuItem(int menu_id, String menu_name, String description, 
			int price, List<FoodAddition> defaultAdditions,
			List<FoodAddition> optionalAdditions) {
		super();
		this.menu_id = menu_id;
		this.menu_name = menu_name;
		this.description = description;
		this.price = price;
		this.defaultAdditions = defaultAdditions;
		this.optionalAdditions = optionalAdditions;
	}

	public int getMenu_id() {return menu_id;	}
	public void setMenu_id(int menu_id) {this.menu_id = menu_id;	}
	public String getMenu_name() {return menu_name;	}
	public void setMenu_name(String menu_name) {this.menu_name = menu_name;	}
	public String getDescription() {return description;	}
	public void setDescription(String description) {this.description = description;	}
	public int getPrice() {return price;	}
	public void setPrice(int price) {this.price = price;	}
	public List<FoodAddition> getDefaultAdditions() {return defaultAdditions;	}
	public void setDefaultAdditions(List<FoodAddition> defaultAdditions) {this.defaultAdditions = defaultAdditions;	}
	public List<FoodAddition> getOptionalAdditions() {return optionalAdditions;	}
	public void setOptionalAdditions(List<FoodAddition> optionalAdditions) {this.optionalAdditions = optionalAdditions;	}
}