package com.ucsandroid.profitable.entities;

import java.util.List;

public class Category {
	
	private String categoryName;
	private int categoryId;
	private List<MenuItem> menuItems;
	
	public Category(String name, int id) {
		super();
		this.categoryName = name;
		this.categoryId = id;
	}
	
	public Category(String name, int id, List<MenuItem> menuItems) {
		super();
		this.categoryName = name;
		this.categoryId = id;
		this.menuItems = menuItems;
	}

	public String getName() {
		return categoryName;
	}
	public void setName(String name) {
		this.categoryName = name;
	}
	public int getId() {
		return categoryId;
	}
	public void setId(int id) {
		this.categoryId = id;
	}
	public List<MenuItem> getMenuItems() {
		return menuItems;
	}
	public void setMenuItems(List<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}

}