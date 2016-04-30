package com.ucsandroid.profitable.utilities;

import java.sql.ResultSet;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class Converters {
	
	/**
	 * Takes a resultSet and converts it into a JSONArray
	 * 
	 * @param resultSet
	 * @return JSONArray 
	 * @throws Exception
	 */
	public static JSONArray convertToJSON(ResultSet resultSet) throws Exception {
		JSONArray jsonArray = new JSONArray();
		while (resultSet.next()) {
			int total_rows = resultSet.getMetaData().getColumnCount();
			JSONObject obj = new JSONObject();
			for (int i = 0; i < total_rows; i++) {
				obj.put(resultSet.getMetaData().getColumnLabel(i + 1)
						.toLowerCase(), resultSet.getObject(i + 1));
			}
			jsonArray.put(obj);
		}
		return jsonArray;
	}
	
	/**
	 * Takes a resultSet and converts it into a String to be parsed
	 * 
	 * @param resultSet
	 * @return String 
	 * @throws Exception
	 */
	public static String convertToString(ResultSet resultSet) {
		try {
		String formattedString = "";
		while (resultSet.next()) {
			int total_rows = resultSet.getMetaData().getColumnCount();
			String tempString = "";
			for (int i = 0; i < total_rows; i++) {
				tempString=tempString+
						resultSet.getMetaData().getColumnLabel(i + 1)+
						": "+resultSet.getObject(i + 1)+"  |  ";
			}
			tempString+="\n";
			formattedString+=tempString;
		}
		return formattedString;
		}
		catch (Exception e) {
			System.out.println("ERROR converting resultset to string");
			System.out.println(e.getMessage());
			return "";
		}
	}

}