package com.hotel.model;
import com.hotel.config.*;
import java.sql.*;
import java.io.*;

public class UserModel{
	
	public static String authenticateUser(String email, String password) throws IOException, Exception{
		String userId = null;
		Connection conn = null; 
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String selectSQL = "SELECT id FROM users WHERE email = ? and password = ?";
		
		try{
			conn = Database.getConnection();

			stmt = conn.prepareStatement(selectSQL);  
			stmt.setString(1, email);
			stmt.setString(2, password);
			
			rs=stmt.executeQuery();  

			while(rs.next()){
				userId = Integer.toString(rs.getInt(1));  
			}

		} catch (Exception e){
			throw new Exception(e.getMessage());  
		}

		return userId;
	}

	public static int getUserRole(int userId) throws IOException{
		int role = 3;
		Connection conn = null; 
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String selectSQL = "SELECT role FROM users WHERE id = ?";
		
		try{
			conn = Database.getConnection();

			stmt = conn.prepareStatement(selectSQL);  
			stmt.setInt(1, userId);
			
			rs=stmt.executeQuery();  

			while(rs.next()){
				role = rs.getInt(1);  
			}

		} catch (Exception e){
			throw new IOException(e.getMessage());  
		}

		return role;	
	}

	public static int addUser(String email, String password, int role, String mobile) throws IOException, SQLException{
		Connection conn = null; 
		PreparedStatement stmt = null;
		int autoId = -1;

		String insertQuery = "INSERT INTO `users`(`email`, `password`, `mobile`, `role`) VALUES (?, ?, ?, ?)";
		
		try{
			conn = Database.getConnection();

			stmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);  
			stmt.setString(1, email);
			stmt.setString(2, password);
			stmt.setString(3, mobile);
			stmt.setInt(4, role);
			
			int affectedRows = stmt.executeUpdate();

			if(affectedRows > 0) {
 				ResultSet generatedKeys = stmt.getGeneratedKeys();
    			
    			if (generatedKeys.next()) {
                	autoId = generatedKeys.getInt(1);
            	}else{
            		throw new SQLException("Unable to retrieve key"); 
            	}
			}

		} catch (SQLException e){
			throw new SQLException(e.getMessage());  
		} catch (ClassNotFoundException e){
			throw new SQLException(e.getMessage());
		} 

		return autoId;
	}
}
