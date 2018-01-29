package com.hotel.model;
import com.hotel.config.*;
import java.sql.*;
import java.io.*;

public class PriceModel{
	
	public static double getPrice(int priceId) throws SQLException{
		double price = 0;
		Connection conn = null; 
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String selectSQL = "SELECT price FROM hotel_price WHERE id = ? ";
		
		try{
			conn = Database.getConnection();

			stmt = conn.prepareStatement(selectSQL);  
			stmt.setInt(1, priceId);
			
			rs=stmt.executeQuery();  

			while(rs.next()){
				price = rs.getDouble(1);  
			}

		} catch (SQLException e){
			throw new SQLException(e.getMessage());  
		} catch (ClassNotFoundException e){
			throw new SQLException(e.getMessage());
		} 

		return price;
	}

}
