package com.ucsandroid.profitable.entities;

public class Discount {
	
	private int discountId;
	private String discountType;
	private float discountPercent;
	private boolean available;
	private int restaurantId;
	
	public Discount(int id, String type, float percent, boolean available, int restaurantId) {
		super();
		this.discountId = id;
		this.discountType = type;
		this.discountPercent = percent;
		this.available = available;
		this.restaurantId = restaurantId;
	}
	public int getId() {
		return discountId;
	}
	public void setId(int id) {
		this.discountId = id;
	}
	public String getType() {
		return discountType;
	}
	public void setType(String type) {
		this.discountType = type;
	}
	public float getPercent() {
		return discountPercent;
	}
	public void setPercent(float percent) {
		this.discountPercent = percent;
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