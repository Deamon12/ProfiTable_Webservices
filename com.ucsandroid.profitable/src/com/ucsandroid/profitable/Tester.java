package com.ucsandroid.profitable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import com.ucsandroid.profitable.utilities.GoogleCloudMessaging;

public class Tester {

	public static void main(String[] args) {
		
		String tabToken = "eG36D1DRsFo:APA91bF4iOMO4ZwVBcwxQxzOxSq6DvHQ6cFxJjrP1VRP6HMPxVWxXKIlj5-smbQRyqRFQAWkawp0LCXXu6cQPF492dIL88QQmK_2Y3l7-LEJ52XsUacgYa4rSZl8GULsigW9OA6Yfuon";
	    String s7Token = "eS31oQbJmxY:APA91bFreUnAC9t8CCMCK92iji3GRVdqY_hwmDchn6y4XVRNMWadFPoksmCHY16B4oiQqERsvBRt4xwV3pDOsgHWofrPChpOf_HsAgvyeGiPZ9iGA3QFj1N7u88qYFiS5Gw2Izm7kz3y";
	    
		
		GoogleCloudMessaging test = new GoogleCloudMessaging();

		List<String> devices = new ArrayList();
		
		devices.add(tabToken);
		devices.add(s7Token);
		
		
		try {
			test.sendFireBaseMessage(1, devices);
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
		

	}


}
