package com.ucsandroid.profitable.utilities;

import java.sql.*;

import com.ucsandroid.profitable.dataaccess.MenuDAO;

public class ConnUtil {
	
	/* TEST database, alter the url for using the AWS online database */
	static String sqlDatabaseURL = "jdbc:postgresql://127.0.0.1:5432/cse190";
	static String username = "postgres";
	static String password = "postgres";
	
	private Connection connection;
	
	public ConnUtil(){
		connectToSQL();
	}
	
	/** Opens database connection. */
	private void connectToSQL() {

		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(sqlDatabaseURL, username, password);
			System.out.println("Successfully connect to database: "+sqlDatabaseURL);
		} catch (Exception e){
			System.out.println("ERROR unable to connect to: "+sqlDatabaseURL);
			System.out.print(e);
			connection = null;
		} finally {

		}
	}
	
	/** returns a valid connection */
	public Connection getConnection() {
		if (connection==null) {connectToSQL();}
		return connection;
	}
	
	/**Closes database connection.*/
	public void closeConnection(){
		try {
			if (connection!=null) {connection.close();}
			System.out.println("Connection closed");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
