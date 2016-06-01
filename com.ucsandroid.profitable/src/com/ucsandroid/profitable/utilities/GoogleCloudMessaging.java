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
	private static final String senderId = "418685242715";
	private static final String GCM_API_KEY = "AIzaSyAhwbFKIDlDCHVHUB6D4TSCnGlXc-yeX-0";
	private static final int RETRIES = 5;
	private static final String serverKey = "AIzaSyCOL5ThhdXu3O3Qz-oLZSwW4GhjC7_nH4E";
	private static final String jsonType = "application/json";
	private static final String fcmUrl = "https://fcm.googleapis.com/fcm/send";
	
	/**
	 * DEPRECATED: Use sendFireBaseMessage()
	 * 
	 * Create and send a message to devices
	 * 
	 * @param type - This should be used to notify the app as 
	 * to which data should be updated. ie, tables, kitchen, etc
	 * <br><b>Types</b><br>
	 * 1: table status changed <br> 
	 * 2: items submit to kitchen <br> 
	 * 3: item status changed <br>
	 * @param devices - List of deviceID's that should be notified
	 * @return 
	 * @throws IOException
	 */
	@Deprecated
	public static String sendMessage(int type, List<String> devices){
		
		String status;
		if (devices.isEmpty()) {
			status = "Message ignored as there are no devices specified!";
		} 
		else {
			
			Message message = new Message.Builder()
					.addData("type", ""+type)
					.build();
			try {
				MulticastResult result = 
						new Sender(GCM_API_KEY).send(message, devices, RETRIES);
				status = "Sent message to "+devices.size()+ " device(s): " + result;
			}
			catch (Exception e){
				status=e.getMessage();
			}
			
		}
		System.out.println(status);
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
	public static String sendFireBaseMessage(int messageType, List<String> devices){
		
		String responseString = "";
		InputStream is=null;
		OutputStream os=null;
		HttpURLConnection con=null;
		Scanner scan=null;
		//System.out.println("Sending message type: "+messageType+" to:");
		//for (String s : devices){
			//System.out.println(s);
		//}
		
		try {
			URL url = new URL(fcmUrl);
		    con = (HttpURLConnection) url.openConnection();
		    con.setDoOutput(true);
		    
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
		    
		    // Write the message to the output stream
		    os = con.getOutputStream();
		    os.write(jsonToSend.toString().getBytes("UTF-8"));
	
		    // Read the response into a string
		    is = con.getInputStream();
		    scan = new Scanner(is, "UTF-8");
		    responseString = scan.useDelimiter("\\A").next();
		    
		} catch (Exception e){
			//for this simple app, don't worry about exceptions - just log them.
			System.out.println(e.getMessage());
		} finally {
			try { if (is!=null) {is.close();}
			} catch (Exception e) {System.out.println(e.getMessage());}
			try { if (os!=null) {os.close();}
			} catch (Exception e) {System.out.println(e.getMessage());}
			try { if (con!=null) {con.disconnect();}
			} catch (Exception e) {System.out.println(e.getMessage());}
			try { if (scan!=null) {scan.close();}
			} catch (Exception e) {System.out.println(e.getMessage());}
		}
	    return responseString;
	}
	
}