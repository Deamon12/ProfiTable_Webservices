package com.ucsandroid.profitable.utilities;

import java.sql.ResultSet;

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

}