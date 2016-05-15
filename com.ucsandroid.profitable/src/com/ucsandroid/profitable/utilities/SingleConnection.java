package com.ucsandroid.profitable.utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SingleConnection {
	
	private static Connection connection;
	private static SingleConnection singleConnection = new SingleConnection();
	private SingleConnection() {
		connection = null;
	}
	
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
	
	/** Opens database connection. */
	private static void connectToSQL() {

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
	public static Connection getConnection() {
		if (connection==null) {connectToSQL();}
		return connection;
	}
	
	/**Closes database connection.*/
	public static void closeConnection(){
		try {
			if (connection!=null) {connection.close();}
			System.out.println("Connection closed");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
