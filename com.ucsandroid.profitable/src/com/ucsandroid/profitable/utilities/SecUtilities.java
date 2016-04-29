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
	
	
}
