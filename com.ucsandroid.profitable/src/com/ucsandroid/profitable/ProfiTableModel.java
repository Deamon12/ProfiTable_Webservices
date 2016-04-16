package com.ucsandroid.profitable;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.json.JSONArray;
import org.json.JSONObject;

import com.ucsandroid.profitable.Utilities.Encrypt;

public class ProfiTableModel{

	private Connection connection;
	private StandardResult finalResult = new StandardResult();
	private JSONArray results;
	
	public ProfiTableModel(){
		openConnection();
	}

	
	/** Opens database connection. */
	public void openConnection(){

		try
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			String connectionUrl = "jdbc:mysql://localhost:3306/ucsandroiddb";
			String connectionUser = "ucsandroidadmin";
			String connectionPassword = "password";
			connection = DriverManager.getConnection(connectionUrl, connectionUser, connectionPassword);

			System.out.println("Connected to DB");

		}catch (SQLException error){
			System.out.print("Error connecting to database: " + error);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

	/**Closes database connection.*/
	public void closeConnection(){

		try {
			connection.close();
			System.out.println("Connection closed");

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}


	public Connection getConnection(){
		
		return connection;
	}



	/**
	 * If successful creation return user information.
	 * If unsuccessful creation return what the error is. ie account exists, email exists..
	 * 
	 * @param firstName
	 * @param lastName
	 * @param password
	 * @param email
	 * @param deviceId - Push notifications
	 * @return JSONArray containing only success or failure.
	 */
	public StandardResult createUser(String firstName, String lastName, String password, 
			String email, String image, String deviceId){

		//create unique user ID
		String userId = createUserId();
		String query = "";
		//query to insert into users table with image
		if (image!=null){
			query = 
					"INSERT INTO users (user_id, first_name, last_name, password, email, active, school, major, image)"
							+ " VALUES ('"+userId+"', '"+firstName+"', '"+lastName+"', '"+password+"', '"+email+"', True, "
							+ " '' , '', '" +image+"')";
		}
		//query to insert into user table without image
		else{
			query = 
					"INSERT INTO users (user_id, first_name, last_name, password, email, active, school, major)"
							+ " VALUES ('"+userId+"', '"+firstName+"', '"+lastName+"', '"+password+"', '"+email+"', True, "
							+ " '' , '')";
		}
		//query to insert into device for push notifications
		String device= 
				"INSERT INTO device (device_id, user_id)"
						+ "VALUES ('"+deviceId+"', '"+userId+"')";

		try{
			//execute both queries to update database
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(query);
			stmt.executeUpdate(device);
			finalResult.setSuccess(1);
			finalResult.setResult("successfully created account");

		}
		catch (SQLException error){
			System.out.println("Error executing query, "+ error.getErrorCode()+" : " + error.getMessage());

			finalResult.setResult(error.getMessage());

		} catch (Exception e) {
			e.printStackTrace();
		}

		closeConnection();
		return finalResult;
	}

	/**
	 * Retrieve all information about a user denoted by userId
	 * @param userId
	 * @return JSONArray containing user_id, first_name, last_name, email, school, major
	 */
	public StandardResult getUserById(String userId){

		//search by userId
		String query = "SELECT user_id, first_name, last_name, email, school, major, description, rating, image "
				+ "FROM users WHERE user_id='"+userId+"'";

		try{

			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			results = convertToJSON(rs);
			finalResult.setSuccess(1);
			finalResult.setResult(results);
			finalResult.setExpectResults(results.length());

		}
		catch (SQLException error){
			System.out.println("Error executing query: " + error);
			finalResult.setResult(error.getMessage());
		} catch (Exception e) {

			e.printStackTrace();
		}

		closeConnection();
		return finalResult;

	}

	
	public StandardResult updateProfile(String userId, String firstName, String lastName, String school, String major, 
			String description, String image){

		String query = "UPDATE users SET first_name = '"+firstName+"', last_name ='"+lastName+"', school ='"+school+"', "
				+ "major= '"+major+"', description='"+description+"', image = '"+image+"' WHERE user_id = '"+userId+"'";

		try{

			Statement stmt = connection.createStatement();
			stmt.executeUpdate(query);
			finalResult.setSuccess(1);

		}
		catch (SQLException error){
			System.out.println("Error executing query: " + error);
			finalResult.setResult(error.getMessage());
		} catch (Exception e) {

			e.printStackTrace();
		}

		closeConnection();
		return finalResult;

	}

	/**
	 * Checks for correct credentials to allow for login
	 * 
	 * @param email, password
	 * @return JSONArray containing single row or empty array if no row exists
	 */
	public StandardResult doLogin(String email, String password){

		//retrieve information if email, password combination exists
		String query = "SELECT user_id, first_name, last_name, date, image, email, active, rating, "
				+ "description, school, major FROM users WHERE email= '" +email+"' and password= '"+password+"'";

		//reformat results
		try{
			Statement stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			results = convertToJSON(rs);

			finalResult.setSuccess(1);
			finalResult.setResult(results);
			finalResult.setExpectResults(results.length());


		}
		catch (SQLException error){
			System.out.println("Error executing query: " + error);
			finalResult.setResult(error.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		closeConnection();

		return finalResult;

	}

	/**
	 * Resets password for identified email, and sends a email with new password to that email
	 * 
	 * @param email 
	 * @return JSONArray containing success or failure
	 */
	public StandardResult forgotPassword(String email){

		//create new password, and hash the password for security
		String newpass = createPassword();
		String securePassword = Encrypt.get_SHA_1_SecurePassword(newpass);

		//get if user exists and then reset password queries
		String getUser = "SELECT user_id, first_name, last_name FROM users WHERE email ='"+email+"'";
		String updatePassword = "UPDATE users SET password = '"+securePassword+"' WHERE email= '"+email+"'";

		try{
			Statement stmt = connection.createStatement();
			//returns user's information for client side use
			ResultSet rs = stmt.executeQuery(getUser);
			results = convertToJSON(rs);

			//if email does not exist, executing this query will not have any affect on database
			stmt.executeUpdate(updatePassword);


			finalResult.setSuccess(1);
			finalResult.setResult(results);
			finalResult.setExpectResults(results.length());

		}
		catch (SQLException error){
			System.out.println("Error executing query: " + error);
			finalResult.setResult(error.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		//send email to email
		//Utilities.sendEmail(email, newpass);

		closeConnection();

		return finalResult;

	}

	/**
	 * Resets password with desired password.
	 * 
	 * @param userId, password
	 * @return JSONArray containing success or failure
	 */
	public StandardResult resetPassword(String userId, String password){

		String query = "UPDATE users SET password = '"+password+"' WHERE user_id= '"+userId+"'";
		
		//convert query results
		try{
			Statement stmt = connection.createStatement();
			stmt.executeUpdate(query);

			finalResult.setSuccess(1);

		}
		catch (SQLException error){
			System.out.println("Error executing query: " + error);
			finalResult.setResult(error.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
		}

		closeConnection();

		return finalResult;

	}


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
	 * Randomly creates userID
	 * @return string
	 */
	public static String createUserId(){
		SecureRandom random = new SecureRandom();
		return new BigInteger(130, random).toString(32);
	}

	/**
	 * randomly creates a password
	 * @return string
	 */
	public static String createPassword(){
		SecureRandom random = new SecureRandom();
		return new BigInteger(40, random).toString(32);
	}


	/**
	 * Send a notification to a list of devices via GoogleCloudMessaging
	 * @param devices
	 */
	/*
	private void sendNotification(List<String> devices, String userId, int type, String questionId){

		GoogleCloudMessaging testing = new GoogleCloudMessaging();
		try {
			System.out.println( testing.sendMessage(questionId, type, userId, devices));
		} catch (IOException error) {
			System.out.println("Error executing query: " + error);
		}

	}*/


}