package com.ucsandroid.profitable.dataaccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.ucsandroid.profitable.utilities.ConnUtil;

public class MainDAO {
	
	protected ConnUtil connUtil;
	
	public MainDAO() {
		connUtil = new ConnUtil();
	}
	
	/** Takes an input query and returns a resultset */
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
	 * TODO
	 * @param insertCount
	 * @param conn
	 * @return
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
		}
	}
	
	/**
	 * TODO
	 * @param deleteCount
	 * @param conn
	 * @return
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
		}
	}
	
	/**
	 * TODO
	 * @param updateCount
	 * @param expectedUpdates
	 * @param conn
	 * @return
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
		}
	}
	
	/**
	 * 
	 * @param pstmt
	 * @param conn
	 */
	protected void sqlCloser(PreparedStatement pstmt,
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
