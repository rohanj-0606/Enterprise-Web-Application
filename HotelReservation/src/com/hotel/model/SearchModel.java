package com.hotel.model;
import com.hotel.config.*;
import com.hotel.libraries.*;
import java.sql.*;
import java.io.*;

public class SearchModel{
	
	public static SearchLib getResult(int searchId) throws SQLException{
		Connection conn = null; 
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String selectSQL = "SELECT `id`, `check_in`, `check_out`, `guest_count` FROM user_query where id = "+searchId+" ";
		
		SearchLib searchList = null;

		
		try{
			conn = Database.getConnection();
			stmt = conn.prepareStatement(selectSQL);  
			
			rs = stmt.executeQuery();  

			while(rs.next()){
				searchList = new SearchLib(rs.getInt(1), rs.getString(2), rs.getString(3), Integer.parseInt(rs.getString(4))); 
				break;
			}

		} catch (SQLException e){
			throw new SQLException(e.getMessage());  
		} catch (ClassNotFoundException e){
			throw new SQLException(e.getMessage());
		} 

		return searchList;
	}


	public static int addSearch(String checkIn, String checkOut, String guestCount) throws Exception{
		Connection conn = null; 
		PreparedStatement stmt = null;
		int autoId = -1;

		String insertQuery = " insert into user_query (check_in, check_out, guest_count) values (?, ?, ?)";
		
		try{
			conn = Database.getConnection();

			stmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);  
			stmt.setString(1, checkIn);
			stmt.setString(2, checkOut);
			stmt.setString(3, guestCount);
			
			int affectedRows = stmt.executeUpdate();

			if(affectedRows > 0) {
 				ResultSet generatedKeys = stmt.getGeneratedKeys();
    			
    			if (generatedKeys.next()) {
                	autoId = generatedKeys.getInt(1);
            	}else{
            		throw new Exception("Unable to retrieve key"); 
            	}
			}

		} catch (Exception e){
			throw new Exception(e.getMessage());  
		}

		return autoId;
	}
}
