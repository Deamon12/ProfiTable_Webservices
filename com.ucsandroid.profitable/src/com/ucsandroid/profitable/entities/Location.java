package com.ucsandroid.profitable.entities;

public class Location {
	
	private int locationId;
	private String locationStatus;
	private String locationName;
	private int restaurantId;
	
	public Location(int id, String status, String name, int restaurantId) {
		super();
		this.locationId = id;
		this.locationStatus = status;
		this.locationName = name;
		this.restaurantId = restaurantId;
	}
	
	public int getId() {
		return locationId;
	}
	public void setId(int id) {
		this.locationId = id;
	}
	public String getStatus() {
		return locationStatus;
	}
	public void setStatus(String status) {
		this.locationStatus = status;
	}
	public String getName() {
		return locationName;
	}
	public void setName(String name) {
		this.locationName = name;
	}
	public int getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}
}