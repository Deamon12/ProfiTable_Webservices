package com.ucsandroid.profitable.entities;

public class MenuItem {
	
	private 	int 		menu_id;
	private   	String		menu_name;
	private   	String    	description;
	private   	int			price;
	
	/** Default empty constructor, does not set any fields */
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
	
	public int getMenu_id() {
		return menu_id;
	}
	public void setMenu_id(int menu_id) {
		this.menu_id = menu_id;
	}
	public String getMenu_name() {
		return menu_name;
	}
	public void setMenu_name(String menu_name) {
		this.menu_name = menu_name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}

}
