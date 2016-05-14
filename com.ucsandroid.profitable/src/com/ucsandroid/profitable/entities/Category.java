package com.ucsandroid.profitable.entities;

import java.util.List;

public class Category {
	
	private String name;
	private int id;
	private List<MenuItem> menuItems;
	
	public Category(String name, int id) {
		super();
		this.name = name;
		this.id = id;
	}
	
	public Category(String name, int id, List<MenuItem> menuItems) {
		super();
		this.name = name;
		this.id = id;
		this.menuItems = menuItems;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<MenuItem> getMenuItems() {
		return menuItems;
	}
	public void setMenuItems(List<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}

	

}