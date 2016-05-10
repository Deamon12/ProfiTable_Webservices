package com.ucsandroid.profitable.dataaccess;

public class CategoryDAO extends MainDAO {
	
	private static String insertStatement = 
			"INSERT INTO Category "+
			"(cat_name, restaurant) "+ 
			" VALUES(?,?)";
		
		private static String updateStatement =
			"UPDATE "+
				"Category "+
			"SET "+
				"cat_name=?, restaurant=? "+
			"WHERE "+
				"cat_id = ?";
		
		private static String deleteStatement =
			"DELETE FROM Category "+
			"WHERE cat_id = ? and restaurant = ?";

		public CategoryDAO() {
			super();
		}
		
		/**
		 * TODO
		 * @param catId
		 * @param restId
		 * @return
		 */
		public String delete(int catId, int restId) {
			try {
				// Open the connection
				conn = connUtil.getConnection();
				// Begin transaction
		        conn.setAutoCommit(false);
		        // Create the prepared statement
		        pstmt = conn.prepareStatement(deleteStatement);
		        // Set the variable parameters
		        int i = 1;
		        pstmt.setInt(i++, catId);
		        pstmt.setInt(i++, restId);
		        // Attempt execution, return number affected 
		        int rowCount = pstmt.executeUpdate();
		        // Validate for expected and return status
		        String deleteStatus = deleteHelper(rowCount, 1, conn);
				return deleteStatus;
			} catch (Exception e) {
				return "Insert failure, SQL issue";
			} finally {
				sqlCleanup(pstmt,conn);
			}
		}
		
		/**
		 * TODO
		 * @param cat_name
		 * @param restId
		 * @return
		 */
		public String insert(String catName, int restId) {
			
			try {
				// Open the connection
				conn = connUtil.getConnection();
				// Begin transaction
		        conn.setAutoCommit(false);
		        // Create the prepared statement
		        pstmt = conn.prepareStatement(insertStatement);
		        // Set the variable parameters
		        int i = 1;
		        pstmt.setString(i++, catName);
		        pstmt.setInt(i++, restId);
		        // Attempt execution, return number affected 
		        int rowCount = pstmt.executeUpdate();
		        // Validate for expected and return status
		        String insertStatus = insertHelper(rowCount, conn);
				return insertStatus;
			} catch (Exception e) {
				return "Insert failure, SQL issue";
			} finally {
				sqlCleanup(pstmt,conn);
			}
			
		}
		
		/**
		 * TODO
		 * @param catId
		 * @param catName
		 * @param restId
		 * @return
		 */
		public String update(int catId, String catName, 
				int restId) {
			try {
				// Open the connection
				conn = connUtil.getConnection();
				// Begin transaction
		        conn.setAutoCommit(false);
		        // Create the prepared statement
		        pstmt = conn.prepareStatement(updateStatement);
		        // Set the variable parameters
		        int i = 1;
		        pstmt.setString(i++, catName);
		        pstmt.setInt(i++, restId);
		        pstmt.setInt(i++, catId);
		        // Attempt execution, return number affected 
		        int rowCount = pstmt.executeUpdate();
		        // Validate for expected and return status
		        String updateStatus = updateHelper(rowCount, 1, conn);
				return updateStatus;
			} catch (Exception e) {
				return "Update failure, SQL issue";
			} finally {
				sqlCleanup(pstmt,conn);
			}
		}

}
