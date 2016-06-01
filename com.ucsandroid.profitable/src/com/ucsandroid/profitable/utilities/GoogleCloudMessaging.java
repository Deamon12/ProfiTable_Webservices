package com.ucsandroid.profitable.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Sender;

public class GoogleCloudMessaging {

	//GCM API Key
	String senderId = "418685242715";
	final String GCM_API_KEY = "AIzaSyAhwbFKIDlDCHVHUB6D4TSCnGlXc-yeX-0";
	final int RETRIES = 5;
	
	
	/**
	 * DEPRECATED: Use sendFireBaseMessage()
	 * 
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
	

	/**
	 * Create and send push notification to given devices - uses Firebase
	 * @param messageType
	 * @param devices
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 * @throws JSONException
	 */
	public String sendFireBaseMessage(int messageType, List<String> devices) throws UnsupportedEncodingException, IOException, JSONException{

		String serverKey = "AIzaSyCOL5ThhdXu3O3Qz-oLZSwW4GhjC7_nH4E";
		
		URL url = new URL("https://fcm.googleapis.com/fcm/send");
	    HttpURLConnection con = (HttpURLConnection) url.openConnection();
	    con.setDoOutput(true);
	    
	    String jsonType = "application/json";
	    
	    // HTTP request header
	    con.setRequestProperty("project_id", senderId);
	    con.setRequestProperty("Content-Type", jsonType);
	    con.setRequestProperty("Authorization", "key="+serverKey);
	    con.setRequestMethod("POST");
	    con.connect();

	    // HTTP request
	    JSONObject jsonToSend = new JSONObject();
	    JSONObject jsonData = new JSONObject();
	    jsonData.put("type", messageType+"");
	    jsonToSend.put("data", jsonData);
	    jsonToSend.put("registration_ids", devices); //These are device tokens
	    
	    //System.out.println("jsonToSend: "+jsonToSend.toString());
	    
	    OutputStream os = con.getOutputStream();
	    os.write(jsonToSend.toString().getBytes("UTF-8"));
	    os.close();

	    // Read the response into a string
	    InputStream is = con.getInputStream();
	    String responseString = new Scanner(is, "UTF-8").useDelimiter("\\A").next();
	    is.close();
		
	    return responseString;

	}
	
}
