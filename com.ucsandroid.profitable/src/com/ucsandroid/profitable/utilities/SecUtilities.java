package com.ucsandroid.profitable.utilities;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SecUtilities {

	/**
	 * A basic algorithm to convert strings to SHA1
	 */
	public static class Encrypt {
		
	public static String get_SHA_1_SecurePassword(String passwordToHash)
		{
			String generatedPassword = null;
			try {
				MessageDigest md = MessageDigest.getInstance("SHA-1");
				//md.update(salt.getBytes()); //No salt....
				byte[] bytes = md.digest(passwordToHash.getBytes());
				StringBuilder sb = new StringBuilder();
				for(int i=0; i< bytes.length ;i++)
				{
					sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
				}
				generatedPassword = sb.toString();
			}
			catch (NoSuchAlgorithmException e)
			{
				e.printStackTrace();
			}
			return generatedPassword;
		}
	}
	
	/**
	 * Basic SHA256 hash for password management
	 * copied from mkyong.com
	 * @param password
	 * @return
	 */
	public static String passwordHashSHA256(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
	        md.update(password.getBytes());
	        byte byteData[] = md.digest();
	        
	        StringBuffer sb = new StringBuffer();
	        for (int i = 0; i < byteData.length; i++) {
	         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
	        }
			
			return sb.toString();
		} catch (Exception e) {
			System.out.println("Failed to hash password\n"+
					e.getMessage());
			return "";
		}
		
		
	}
	
}
