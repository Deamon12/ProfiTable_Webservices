package com.ucsandroid.profitable.entities;

public class Discount {
	
	private int id;
	private String type;
	private float percent;
	private boolean available;
	private int restaurantId;
	
	public Discount(int id, String type, float percent, boolean available, int restaurantId) {
		super();
		this.id = id;
		this.type = type;
		this.percent = percent;
		this.available = available;
		this.restaurantId = restaurantId;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public float getPercent() {
		return percent;
	}
	public void setPercent(float percent) {
		this.percent = percent;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	public int getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(int restaurantId) {
		this.restaurantId = restaurantId;
	}
}