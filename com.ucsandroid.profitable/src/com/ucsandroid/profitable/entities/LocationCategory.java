package com.ucsandroid.profitable.entities;

import java.util.List;

public class LocationCategory {
	
	private int id;
	private String name;
	private int restaurantId;
	private List<Integer> locations;
	
	public LocationCategory(int id, String name, int restaurantId, List<Integer> locations) {
		super();
		this.id = id;
		this.name = name;
		this.restaurantId = restaurantId;
		this.locations = locations;
	}
	
	public LocationCategory(int id, String name, int restaurantId) {
		super();
		this.id = id;
		this.name = name;
		this.restaurantId = restaurantId;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}
	public List<Integer> getLocations() {
		return locations;
	}
	public void setLocations(List<Integer> locations) {
		this.locations = locations;
	}
}