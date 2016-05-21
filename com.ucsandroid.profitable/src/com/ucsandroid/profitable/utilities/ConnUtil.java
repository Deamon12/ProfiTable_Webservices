package com.ucsandroid.profitable.utilities;

import java.sql.*;

public class ConnUtil {
	
	/* TEST database, alter the url for using the AWS online database */
	
	static String sqlDatabaseURL = "jdbc:postgresql://127.0.0.1:5432/cse190";
	static String username = "postgres";
	static String password = "postgres";
	
	
	/* AWS DB */
	/*
	static String url = "profitabledbproduction.c1xpcxj1mum7.us-west-2.rds.amazonaws.com";
	static String sqlDatabaseURL = "jdbc:postgresql://"+url+":5432/profitabledbprod";
	static String username = "profitable";
	static String password = "ucsandroid";
	*/
	
	private Connection connection;
	
	public ConnUtil(){
		connectToSQL();
	}
	
	/** Opens database connection. */
	private void connectToSQL() {

		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection(sqlDatabaseURL, username, password);
			System.out.println("Successfully connected to database: "+sqlDatabaseURL);
		} catch (Exception e){
			System.out.println("ERROR unable to connect to: "+sqlDatabaseURL);
			System.out.print(e);
			connection = null;
		} finally {

		}
	}
	
	/** returns a valid connection */
	public Connection getConnection() {
		try {
			if (connection==null) {connectToSQL();}
			else if (connection.isClosed()) {connectToSQL();}
			return connection;
		} catch (Exception e) {
			e.printStackTrace();
			connectToSQL();
			return connection;
		}
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
