package com.ucsandroid.profitable.dataaccess;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.JSONArray;

import com.ucsandroid.profitable.utilities.ConnUtil;
import com.ucsandroid.profitable.utilities.Converters;

public class MenuDAO {
	
	private String fetchMenuAll = "select * from menu_item where available=true and restaurant=1";
	
	private ConnUtil connUtil;
	
	
	public MenuDAO() {
		connUtil = new ConnUtil();
		
	}
	
	public JSONArray fetchMenu() {
		Statement statement = null;
        ResultSet results = null;
        
        try {
        // Create the statement
        statement = connUtil.getConnection().createStatement();

        // Use the created statement to SELECT
        // the categories from the sql database
        results = statement.executeQuery(fetchMenuAll);
        
        String temp = "";
        while (results.next()) {
            Integer id = results.getInt("menu_id");
            String name = results.getString("menu_name");
            String description = results.getString("description");
            Float price = ((float)results.getInt("price")/100);
            temp = id+" | "+name+" | "+description+" | "+price;
            System.out.println(temp);
        }
        
        //return Converters.convertToJSON(results);
        return null;
        }
        catch (SQLException sqle) {
            System.out.println(sqle.getMessage());
            return null; 
            //TODO - error handling
        } //end catch sql exception
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null; 
            //TODO - error handling
        } //end catch general exception
        finally {
        	// Close the ResultSet
            if (results != null) {
                try {results.close();} //only need to close if not null
                catch (Exception e) {System.out.println(e.getMessage());}
            }

            // Close the Statement
            if (statement != null) {
                try {statement.close();} //only need to close if not null
                catch (Exception e) {System.out.println(e.getMessage());}
            }

        } //end finally
	}

}
