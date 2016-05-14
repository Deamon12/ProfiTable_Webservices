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

import com.ucsandroid.profitable.utilities.SecUtilities.Encrypt;

public class ProfiTableModel{

	/*
	private Connection connection;
	private StandardResult finalResult = new StandardResult();
	private JSONArray results;
	
	public ProfiTableModel(){
		openConnection();
	}

	

	public void openConnection(){

		try
		{
			
			
			Class.forName("org.postgresql.Driver");
			String connectionUrl = "jdbc:postgresql://profitabledbproduction.c1xpcxj1mum7.us-west-2.rds.amazonaws.com:5432/profitabledbprod";
			String connectionUser = "profitable";
			String connectionPassword = "ucsandroid";
			connection = DriverManager.getConnection(connectionUrl, connectionUser, connectionPassword);

			System.out.println("Connected to DB");

		}catch (SQLException error){
			System.out.print("Error connecting to database: " + error);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}

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

	public static String createUserId(){
		SecureRandom random = new SecureRandom();
		return new BigInteger(130, random).toString(32);
	}

	public static String createPassword(){
		SecureRandom random = new SecureRandom();
		return new BigInteger(40, random).toString(32);
	}

	private void sendNotification(List<String> devices, String userId, int type, String questionId){

		GoogleCloudMessaging testing = new GoogleCloudMessaging();
		try {
			System.out.println( testing.sendMessage(questionId, type, userId, devices));
		} catch (IOException error) {
			System.out.println("Error executing query: " + error);
		}

	}*/


}