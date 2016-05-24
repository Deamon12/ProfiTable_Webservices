package com.ucsandroid.profitable.controllers;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ucsandroid.profitable.entities.Customer;
import com.ucsandroid.profitable.entities.FoodAddition;
import com.ucsandroid.profitable.entities.MenuItem;
import com.ucsandroid.profitable.entities.OrderedItem;
import com.ucsandroid.profitable.service.OrderService;

@Path ("/orders")
public class OrderController {
	
	//TODO - instead of returning unable to find for tables without an order, 
	//return a better message
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String fetchOrder(
			@QueryParam("location_id") int location_id,
			@QueryParam("rest_id") int rest_id
			) {
		return OrderService.getInstance().OrderGet(location_id, rest_id); 
	}
	
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	public String insertOrder(
			@QueryParam("location_id") int location_id,
			@QueryParam("employee_id") int employee_id
			) {
		//TODO
		return OrderService.getInstance().OrderPut(location_id,employee_id); 
	}
	
	
	@Path ("/seat")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String postOrderSeat(
			@QueryParam("location_id") int location_id,
			@QueryParam("employee_id") int employee_id
			) {
		return OrderService.getInstance().seatTable(location_id,
				employee_id);
	}
	
	
	@Path ("/close")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String postOrderClose(
			@QueryParam("location_id") int location_id,
			@QueryParam("order_id") int order_id
			) {
		return OrderService.getInstance().closeTab(location_id,
				order_id);
	}
	
	/**
	 * Takes in a JSON string containing a list of customers
	 * those customers must have their ordered items with their
	 * associated menu items and any additions to their ordered
	 * items
	 * @param customers
	 * @return
	 */
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String postOrder(
			@QueryParam("customers") String customers
			) {
		return OrderService.getInstance().orderPost(customers); 
	}
	
	
	@Path ("/parseTest")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public String parseTest(
			@QueryParam("customers") String customers
			) {
		
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		
		Type collectionType = new TypeToken<Collection<Customer>>(){}.getType();
		
		Collection<Customer> custs = gson.fromJson(customers, collectionType);
		
		String parsed = "Attempted to parse json.  Resulted in...\n";
		
		if (custs.size()==0){
			parsed=parsed+"found nothing in json :(";
		} else {
			for (Customer c : custs) {
				parsed=parsed+"\n\nFound customer: "+c.getCustomerId()+
					" at order: "+c.getTabId()+"\n";
				for (OrderedItem o : c.getOrder()) {
					parsed=parsed+"   Ordered item: "+
						o.getOrderedItemId()+".  Notes: "+
						o.getOrderedItemNotes()+".  Bring first? "+
						o.isBringFirst();
					parsed=parsed+"   MenuItem: "+o.getMenuItem()+
						" with additions: ";
					for (FoodAddition fa : o.getAdditions()) {
						parsed=parsed+fa.getId()+" ";
					}
				}
			}
		}
		
		parsed=parsed+"\n\n\n JSON String received by endpoint was:\n"+customers;
		
		return parsed;
	}
	
	@Path ("/selfTest")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String selfTest() {
		Customer c1 = new Customer();
		Customer c2 = new Customer();
		Customer c3 = new Customer();
		c1.setTabId(1);
		c2.setTabId(1);
		c3.setTabId(1);
		OrderedItem o1 = new OrderedItem();
		OrderedItem o2 = new OrderedItem();
		OrderedItem o3 = new OrderedItem();
		OrderedItem o4 = new OrderedItem();
		o1.setBringFirst(true);
		o2.setBringFirst(false);
		o3.setBringFirst(false);
		o4.setBringFirst(false);
		o2.setOrderedItemNotes("rare");
		o1.setOrderedItemNotes("");	
		o3.setOrderedItemNotes("");	
		o4.setOrderedItemNotes("");	
		o2.setOrderedItemStatus("");
		o1.setOrderedItemStatus("");	
		o3.setOrderedItemStatus("");	
		o4.setOrderedItemStatus("");
		MenuItem m1 = new MenuItem();
		m1.setId(3);
		MenuItem m2 = new MenuItem();
		m2.setId(5);
		MenuItem m3 = new MenuItem();
		m3.setId(6);
		MenuItem m4 = new MenuItem();
		m4.setId(1);
		o1.setMenuItem(m1);
		o2.setMenuItem(m2);
		o3.setMenuItem(m3);
		o4.setMenuItem(m4);
		FoodAddition f1 = new FoodAddition(3);
		FoodAddition f2 = new FoodAddition(1);
		FoodAddition f3 = new FoodAddition(2);
		FoodAddition f4 = new FoodAddition(6);
		FoodAddition f5 = new FoodAddition(6);
		FoodAddition f6 = new FoodAddition(2);
		FoodAddition f7 = new FoodAddition(3);
		List<FoodAddition> fs1 = new ArrayList<FoodAddition>();
		List<FoodAddition> fs2 = new ArrayList<FoodAddition>();
		List<FoodAddition> fs3 = new ArrayList<FoodAddition>();
		List<FoodAddition> fs4 = new ArrayList<FoodAddition>();
		fs1.add(f1);
		fs2.add(f5);
		fs3.add(f2);
		fs4.add(f4);
		fs1.add(f3);
		fs2.add(f7);
		fs3.add(f6);
		o1.setAdditions(fs1);
		o2.setAdditions(fs2);
		o3.setAdditions(fs3);
		o4.setAdditions(fs4);
		List<OrderedItem> os1 = new ArrayList<OrderedItem>();
		List<OrderedItem> os2 = new ArrayList<OrderedItem>();
		List<OrderedItem> os3 = new ArrayList<OrderedItem>();
		os1.add(o1);
		os2.add(o2);
		os3.add(o3);
		os1.add(o4);
		c1.setOrder(os1);
		c2.setOrder(os2);
		c3.setOrder(os3);
		List<Customer> custo = new ArrayList<Customer>();
		custo.add(c1);
		custo.add(c2);
		custo.add(c3);
		Gson intoGson = new GsonBuilder().setPrettyPrinting().create();
		
		String customers = intoGson.toJson(custo);
		
		return parseTest(customers);
	}

}