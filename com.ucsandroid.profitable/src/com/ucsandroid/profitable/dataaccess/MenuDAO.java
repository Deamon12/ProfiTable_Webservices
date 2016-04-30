package com.ucsandroid.profitable.dataaccess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import com.ucsandroid.profitable.entities.MenuItem;
import com.ucsandroid.profitable.utilities.ConnUtil;
import com.ucsandroid.profitable.utilities.Converters;

public class MenuDAO {
	
	private String fetchMenuAll = "select * from menu_item where available=true and restaurant=1";
	
	private ConnUtil connUtil;
	
	public MenuDAO() {
		connUtil = new ConnUtil();
	}
	
	/** Takes an input query and returns a resultset */
	public ResultSet fetchData(String query) {
		Statement statement = null;
        ResultSet results = null;
        
        try {
        statement = connUtil.getConnection().createStatement();
        results = statement.executeQuery(query);
        
        return results;

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
        	
        	//TODO - closing the result set and statement make it so the resultset
        	// cannot be returned out of this function... but we do want them to be terminated.  hmmm...
        	
        	
        	// Close the ResultSet
        	/*
            if (results != null) {
                try {results.close();} //only need to close if not null
                catch (Exception e) {System.out.println(e.getMessage());}
            }*/

            // Close the Statement
        	/*
            if (statement != null) {
                try {statement.close();} //only need to close if not null
                catch (Exception e) {System.out.println(e.getMessage());}
            }*/

        } //end finally
		
	}
	
	/** Takes an input query and returns a resultset */
	public ResultSet fetchPreparedData(String query, String... arguments) {
        ResultSet results = null;
        PreparedStatement pstmt = null;
        
        try {
        pstmt = connUtil.getConnection().prepareStatement(query);
        int i = 1;
        for (String a: arguments) {
        	pstmt.setString(i++, a);
        }
        results = pstmt.executeQuery();
        
        return results;

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
        	
        	//TODO - closing the result set and statement make it so the resultset
        	// cannot be returned out of this function... but we do want them to be terminated.  hmmm...
        	
        	
        	// Close the ResultSet
        	/*
            if (results != null) {
                try {results.close();} //only need to close if not null
                catch (Exception e) {System.out.println(e.getMessage());}
            }*/

            // Close the Statement
        	/*
            if (statement != null) {
                try {statement.close();} //only need to close if not null
                catch (Exception e) {System.out.println(e.getMessage());}
            }*/

        } //end finally
		
	}
	
	public String fetchMenu() {
		Statement statement = null;
        ResultSet results = null;
        
        try {
        // Create the statement
        statement = connUtil.getConnection().createStatement();

        // Use the created statement to SELECT
        // the categories from the sql database
        results = statement.executeQuery(fetchMenuAll);
        
        //hideous method, string parsing
        List<MenuItem> currentMenu = new ArrayList<MenuItem>();
        String returnString="";
        
        while (results.next()) {
            Integer id = results.getInt("menu_id");
            String name = results.getString("menu_name");
            String description = results.getString("description");
            Float price = ((float)results.getInt("price")/100);
            returnString = returnString+
            		"id: "+id+", "+
            		"name: "+name+", "+
            		"description: "+description+", "+
            		"price: "+price+", "+
            		"| \n";
        }
        
        return returnString;

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
