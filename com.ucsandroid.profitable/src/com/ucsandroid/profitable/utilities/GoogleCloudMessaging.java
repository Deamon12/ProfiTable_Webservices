package com.ucsandroid.profitable.utilities;

import java.io.IOException;
import java.util.List;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;

public class GoogleCloudMessaging {

	//GCM API Key
	final String GCM_API_KEY = "AIzaSyBNv8oUEe3sJre3jl9J83sntAoAfuejIyw";
	final int RETRIES = 5;
	
	
	/**
	 * Create and send a message to devices
	 * 
	 * @param type - This should be used to notify the app as to which data should be updated. ie, tables, kitchen, etc
	 * @param devices - List of deviceID's that should be notified
	 * @return 
	 * @throws IOException
	 */
	public String sendMessage(int type, List<String> devices) throws IOException{
		
		String status;
		if (devices.isEmpty()) {
			status = "Message ignored as there are no devices specified!";
		} 
		else {
			
			Message message = new Message.Builder()
					.addData("type", ""+type)
					.build();
			
			MulticastResult result = new Sender(GCM_API_KEY).send(message, devices, RETRIES);
			
			status = "Sent message to "+devices.size()+ " device(s): " + result;
		}
		
		return status;
		
	}
	
}