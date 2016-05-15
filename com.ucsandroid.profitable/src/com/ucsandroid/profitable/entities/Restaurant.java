package com.ucsandroid.profitable.entities;

public class Restaurant {
	
	private int restaurantId;
	private int restaurantName;
	
	public Restaurant(int id, int name) {
		super();
		this.restaurantId = id;
		this.restaurantName = name;
	}
	
	public int getId() {
		return restaurantId;
	}
	public void setId(int id) {
		this.restaurantId = id;
	}
	public int getName() {
		return restaurantName;
	}
	public void setName(int name) {
		this.restaurantName = name;
	}

}