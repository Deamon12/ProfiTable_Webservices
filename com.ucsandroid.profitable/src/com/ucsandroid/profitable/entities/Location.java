package com.ucsandroid.profitable.entities;

public class Location {
	
	private int id;
	private String status;
	private String name;
	private int locationCategoryId;
	private int restaurantId;
	
	public Location(int id, String status, String name, int locationCategoryId, int restaurantId) {
		super();
		this.id = id;
		this.status = status;
		this.name = name;
		this.locationCategoryId = locationCategoryId;
		this.restaurantId = restaurantId;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLocationCategoryId() {
		return locationCategoryId;
	}
	public void setLocationCategoryId(int locationCategoryId) {
		this.locationCategoryId = locationCategoryId;
	}
	public int getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}
}