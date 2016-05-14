package com.ucsandroid.profitable.entities;

public class FoodAddition {
	
	private String name;
	private int price;
	private boolean available;
	private int id;
	private int restaurant;
	
	public FoodAddition(String name, int price, boolean available, int id, int restaurant) {
		super();
		this.name = name;
		this.price = price;
		this.available = available;
		this.id = id;
		this.restaurant = restaurant;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(int restaurant) {
		this.restaurant = restaurant;
	}

}