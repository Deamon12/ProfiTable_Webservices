package com.ucsandroid.profitable.entities;

import java.util.List;

public class LocationCategory {
	
	private int locationCategoryId;
	private String locationCategoryName;
	private int restaurantId;
	private List<Location> locations;
	
	public LocationCategory(int id, String name, int restaurantId, List<Location> locations) {
		super();
		this.locationCategoryId = id;
		this.locationCategoryName = name;
		this.restaurantId = restaurantId;
		this.locations = locations;
	}
	
	public LocationCategory(int id, String name, int restaurantId) {
		super();
		this.locationCategoryId = id;
		this.locationCategoryName = name;
		this.restaurantId = restaurantId;
	}

	public int getId() {
		return locationCategoryId;
	}
	public void setId(int id) {
		this.locationCategoryId = id;
	}
	public String getName() {
		return locationCategoryName;
	}
	public void setName(String name) {
		this.locationCategoryName = name;
	}
	public int getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}
	public List<Location> getLocations() {
		return locations;
	}
	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}
}