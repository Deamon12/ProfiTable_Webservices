package com.ucsandroid.profitable.utilities;

public class StatementBuilder {
	
	/**
	 * Validates that the input is acceptable and if
	 * it is non null and contains characters, attempts
	 * to parse it as a boolean value.  <br>Will only update
	 * the supplied base query if the argument can be parsed
	 * without any errors.
	 * @param queryBase
	 * @param queryAddition
	 * @param boolAsString
	 */
	public static String addBool(String queryBase, 
			String queryAddition, String boolAsString) {
		
		if (boolAsString!=null && boolAsString.length()>0) {
			Boolean boolValue = Boolean.valueOf(boolAsString);
			queryBase=queryBase+queryAddition+boolValue+" ";
		}
		
		return queryBase;
		
	}
	
	/**
	 * Validates that the input is acceptable.  If
	 * it is non null and contains characters, attempts
	 * to parse it as an integer value.
	 * <br>Invalid input that cannot be parsed as an integer will
	 * not be added to the query
	 * <br>Will only update the supplied base query if the argument can be parsed
	 * without any errors.
	 * @param queryBase
	 * @param queryAddition
	 * @param boolAsString
	 */
	public static String addInt(String queryBase, 
			String queryAddition, String intAsString) {
		
		try {
			if (intAsString!=null && intAsString.length()>0) {
				Integer intValue = Integer.parseInt(intAsString);
				queryBase=queryBase+queryAddition+intValue+" ";
			}
		} catch (Exception e) {
			System.out.println("QueryBuilder.addInt() ERROR:");
			System.out.println(e.getMessage());
		}
		
		return queryBase;
		
	}

}