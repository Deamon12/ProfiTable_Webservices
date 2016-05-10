package com.ucsandroid.profitable.dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ucsandroid.profitable.utilities.ConnUtil;

public class MainDAO {
	
	protected ConnUtil connUtil;
	protected PreparedStatement pstmt;
	protected Connection conn;
	
	public MainDAO() {
		connUtil = new ConnUtil();
		pstmt = null;
		conn = null;
	}
	
	/**
	 * TODO: NEED to perform better cleanup
	 * <br>
	 * Takes an input query and returns a result set
	 * @param query - String the query to perform
	 * @return - ResultSet
	 */
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
        } //end catch sql exception
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null; 
        } //end catch general exception
        finally {
        	
        	//TODO - closing the result set and statement make 
        	//it so the resultset
        	//cannot be returned out of this function... 
        	//but we do want them to be terminated.  hmmm...

        } //end finally
		
	}

	/**
	 * Encapsulates code for performing inserts across any sort
	 * of sql insert.
	 * @param insertCount - int number of inserts executed
	 * @param conn - Connection for either committing or
	 * rolling back transaction
	 * @return - String status message
	 */
	protected String insertHelper(int insertCount, Connection conn) {
		// If insert went through correctly, should only create 1 record
		try {
			if (insertCount!=1) {
				//abandon the commit
				conn.rollback();
	            return "Insert Failure";
	        } else {
	            // Commit transaction
	        	conn.commit();
	            return "Insert successful";
	        }
		} catch (Exception e) {
			//catch any sql errors
			return "Insert Failure";
		} finally {
			try {conn.setAutoCommit(true); }
			catch (Exception e) { 
				System.out.println(e.getMessage());
			}
		}
	}
	
	/**
	 * Encapsulates code for performing deletes across any sort
	 * of sql delete.
	 * @param deleteCount - int number of deletes executed
	 * @param expectedDeletes - int number expected
	 * @param conn - Connection for either committing or
	 * rolling back transaction
	 * @return - String status message
	 */
	protected String deleteHelper(int deleteCount, 
			int expectedDeletes, Connection conn) {
		// If insert went through correctly, should only create 1 record
		try {
			if (deleteCount!=expectedDeletes) {
				//abandon the commit
				conn.rollback();
	            return "Delete Failure";
	        } else {
	            // Commit transaction
	        	conn.commit();
	            return "Delete successful";
	        }
		} catch (Exception e) {
			//catch any sql errors
			return "Delete Failure";
		} finally {
			try {conn.setAutoCommit(true); }
			catch (Exception e) { 
				System.out.println(e.getMessage());
			}
		}
	}
	
	/**
	 * Encapsulates code for performing updates across any sort
	 * of sql update.
	 * @param updateCount - int number of updates executed
	 * @param expectedUpdates - int number expected
	 * @param conn - Connection for either committing or
	 * rolling back transaction
	 * @return - String status message
	 */
	protected String updateHelper(int updateCount, 
			int expectedUpdates, Connection conn) {
		// If insert went through correctly, should only create 1 record
		try {
			if (updateCount!=expectedUpdates) {
				//abandon the commit
				conn.rollback();
	            return "Update Failure";
	        } else {
	            // Commit transaction
	        	conn.commit();
	            return "Update successful";
	        }
		} catch (Exception e) {
			//catch any sql errors
			return "Update Failure";
		} finally {
			try {conn.setAutoCommit(true); }
			catch (Exception e) { 
				System.out.println(e.getMessage());
			}
		}
	}
	
	/**
	 * Closes the prepared statement and the connection
	 * so that the SQL connection and server is left in a clean
	 * state.
	 * <br>Will catch any exceptions and swallow them, producing
	 * error messages but otherwise allowing continuation of 
	 * program.
	 * @param pstmt - the PreparedStatement to close
	 * @param conn - the Connection to close
	 */
	protected void sqlCleanup(PreparedStatement pstmt,
			Connection conn) {
		// Close the Statement
        if (pstmt != null) {
            try {pstmt.close();} //only need to close if not null
            catch (Exception e) {System.out.println(e.getMessage());}
        }
        // Close the Connection
        if (conn != null) {
            try {conn.close();} //only need to close if not null
            catch (Exception e) {System.out.println(e.getMessage());}
        }
	}
}
