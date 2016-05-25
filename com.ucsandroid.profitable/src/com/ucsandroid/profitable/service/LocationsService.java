package com.ucsandroid.profitable.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ucsandroid.profitable.dataaccess.LocationsDataAccess;

public class LocationsService {
	
	private LocationsDataAccess locationsDataAccess;
	
	private static LocationsService locationsService = 
			new LocationsService();
	
	private Gson gson = new GsonBuilder().setPrettyPrinting().create();
	
	private LocationsService() {
		locationsDataAccess = LocationsDataAccess.getInstance();
	}
	
	public static LocationsService getInstance() {
		if (locationsService==null) {
			locationsService = new LocationsService(); 
		}
		return locationsService;
	}
	
	private LocationsDataAccess getLocationsDataAccess() {
		if (locationsDataAccess==null) {
			locationsDataAccess = LocationsDataAccess.getInstance();
		}
		return locationsDataAccess;
	}
	
	public String LocationsGet(int rest_id) {
		return gson.toJson(getLocationsDataAccess().
				getLocations(rest_id));
	}
	
	public String updateLocationStatus(int location_id, String status) {
		return gson.toJson(getLocationsDataAccess().
				updateLocationStatus(location_id, status));
	}

}