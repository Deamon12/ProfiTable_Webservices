package com.ucsandroid.profitable.entities;

public class Employee {
	
	private int id;
	private String type;
	private String accountName;
	private String firstName;
	private String lastName;
	private String password;
	private int restaurant;
	
	public Employee(int id, String type, String accountName, 
			String firstName, String lastName, String password,
			int restaurant) {
		super();
		this.id = id;
		this.type = type;
		this.accountName = accountName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.password = password;
		this.restaurant = restaurant;
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
	public String getAccountName() {
		return accountName;
	}
	public void setAccountName(String accountName) {
		this.accountName = accountName;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(int restaurant) {
		this.restaurant = restaurant;
	}
	
}