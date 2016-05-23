package com.ucsandroid.profitable.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ucsandroid.profitable.StandardResult;
import com.ucsandroid.profitable.dataaccess.CustomerDataAccess;
import com.ucsandroid.profitable.dataaccess.OrderDataAccess;
import com.ucsandroid.profitable.entities.Customer;
import com.ucsandroid.profitable.entities.FoodAddition;
import com.ucsandroid.profitable.entities.OrderedItem;

public class OrderService {
	
	private OrderDataAccess orderDataAccess;
	
	private static OrderService orderService = 
			new OrderService();
	
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	private OrderService() {
		orderDataAccess = OrderDataAccess.getInstance();
	}
	
	public static OrderService getInstance() {
		if (orderService==null) {
			orderService = new OrderService(); 
		}
		return orderService;
	}
	
	private OrderDataAccess getOrderDataAccess() {
		if (orderDataAccess==null) {
			orderDataAccess = OrderDataAccess.getInstance();
		}
		return orderDataAccess;
	}

	public String OrderGet(int loc_id, int rest_id) {
		return gson.toJson(getOrderDataAccess().
				getOrder(loc_id, rest_id));
	}
	
	public String orderPost(String orderJson) {
		
		//TODO TRANSACTION~!!!
		
		Type collectionType = new TypeToken<Collection<Customer>>(){}.getType();
		Collection<Customer> custs = gson.fromJson(orderJson, collectionType);
		StandardResult sr = null;
		
		List<Customer> createdCustomers = new ArrayList<Customer>();
		for (Customer c : custs) {
			//create a new customer with tab, use the return id for relations
			//given: tab id only
			sr = CustomerDataAccess.getInstance().createCustomer(c.getTabId());
			Customer cust = null;
			if (sr.getSuccess()){
				cust = (Customer) sr.getResult();
				createdCustomers.add(cust);
			} else {
				return gson.toJson(sr);
			}
			
			List<OrderedItem> custsOrder = new ArrayList<OrderedItem>();
			for (OrderedItem o : c.getOrder()) {
				//for each item ordered, extract it and add it to db, get the return id
				//given notes, maybe status, bringfirst, menuItem, additions
				
				sr = OrderDataAccess.getInstance().createOrderedItem(o.getOrderedItemNotes(),
						o.getOrderedItemStatus(), o.isBringFirst());
				OrderedItem ordered = null;
				if (sr.getSuccess()){
					ordered = (OrderedItem) sr.getResult();
					ordered.setMenuItem(o.getMenuItem());
					custsOrder.add(ordered);
				} else {
					return gson.toJson(sr);
				}
				
				//then add in relation between customer and this item and menu item
				int customer = cust.getCustomerId();
				int orderItemId = ordered.getOrderedItemId();
				int menuId = o.getMenuItem().getId();
				System.out.println(customer+" "+orderItemId+" "+menuId);
				sr = OrderDataAccess.getInstance().createOrderedRelation(customer,
						orderItemId, menuId);
				if (!sr.getSuccess()) {
					//if not successful creation relation, return
					return gson.toJson(sr);
				}
				
				List<FoodAddition> additions = new ArrayList<FoodAddition>();
				for (FoodAddition fa : o.getAdditions()) {
					//for each addition included in this ordered item, add that relation
					//given id
					
					sr = OrderDataAccess.getInstance().createOrderedAddition(ordered.getOrderedItemId(),
							fa.getId());
					
					if (sr.getSuccess()){
						additions.add(fa);
					} else {
						return gson.toJson(sr);
					}
					
				}
				ordered.setAdditions(additions);
			}
			cust.setOrder(custsOrder);
		}
		
		//return the created customers with their ids and ordered item ids in SR
		StandardResult orderReturn = new StandardResult(true, createdCustomers);
		return gson.toJson(orderReturn);
	}
	
	/**
	 * TODO - not yet implemented
	 * @param location_id
	 * @param employee_id
	 * @return
	 */
	public String OrderPut(int location_id, int employee_id) {
		//create a new order, insert it into db
		
		//update location
		
		//create relational entry for employee/location/order
		
		//return status
		
		return "TODO - not yet implemented";
	}
	
}