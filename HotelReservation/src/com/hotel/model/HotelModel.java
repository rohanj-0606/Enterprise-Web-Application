package com.hotel.model;
import com.hotel.config.*;
import com.hotel.libraries.*;
import java.sql.*;
import java.io.*;
import java.util.*;  

public class HotelModel{
	public static HotelLib getHotel(int hotelId) throws IOException{
		ArrayList<HotelLib> hotel = null;

		try{
			hotel = getHotel("", -1, -1, 999999, hotelId);
		}catch (ClassNotFoundException e){
			throw new IOException(e.getMessage());
		}
		if(hotel.isEmpty()){
			throw new IOException("Hotel not available");
		}

		return hotel.get(0);
	}

	public static ArrayList<HotelLib> getHotel(String search, double rating, double priceStart, double priceEnd) throws IOException, ClassNotFoundException{
		return HotelModel.getHotel(search, rating, priceStart, priceEnd, 0);
	}

	public static void deleteHotel(int hotelId) throws IOException {
		try{
			Connection conn = null; 
			PreparedStatement stmt = null;
			int autoId = -1;

			String insertQuery = "delete from `hotels` where id = ?";
			
			conn = Database.getConnection();

			stmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);  
			stmt.setInt(1, hotelId);
				
			int affectedRows = stmt.executeUpdate();

			if(affectedRows <= 0) {
	            	throw new IOException("Not deleted"); 
			}
		} catch (SQLException e){
			throw new IOException(e.getMessage());  
		} catch (ClassNotFoundException e){
			throw new IOException(e.getMessage());
		} 
	}

	public static int updateRating(int hotelId, double rating) throws IOException {
		Connection conn = null; 
		PreparedStatement stmt = null;
		int autoId = -1;

		String insertQuery = "update `hotels` set total_rater = (total_rater + 1), avg_rating = ((avg_rating * total_rater) + ?) / (total_rater + 1) where id = ?";
		
		try{
			conn = Database.getConnection();

			stmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);  
			stmt.setDouble(1, rating);
			stmt.setInt(2, hotelId);
			
			int affectedRows = stmt.executeUpdate();

			if(affectedRows <= 0) {
            	throw new SQLException("Unable to retrieve key"); 
			}

		} catch (SQLException e){
			throw new IOException(e.getMessage());  
		} catch (ClassNotFoundException e){
			throw new IOException(e.getMessage());
		} 

		return autoId;
	}

	public static ArrayList<HotelLib> getHotel(String search, double rating, double priceStart, double priceEnd, int hotelId) throws IOException, ClassNotFoundException{
		String userId = null;
		Connection conn = null; 
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String selectSQL = "SELECT `id`, `name`, `description`, `overview`, `avg_rating`, `price`, `address`, `area`, `city`, `country`, `image_src`, `checkin`, `checkout` FROM hotels ";
		String whereClause = "";

		ArrayList<HotelLib> hotelList = new ArrayList<HotelLib>();

		if(!search.isEmpty()){
			search = search.toUpperCase();
			whereClause = "where (city like '%"+ search +"%' or name like '%"+ search +"%' or area like '%"+ search +"%') ";
		}

		if(hotelId > 0){
			if(!whereClause.isEmpty()){
				whereClause += " and id  " + hotelId + " ";
			} else {
				whereClause += " where id = " + hotelId + " ";
			}
		}

		if(rating > 0){
			if(!whereClause.isEmpty()){
				whereClause += " and avg_rating > " + rating + " ";
			} else {
				whereClause += " where avg_rating > " + rating + " ";
			}
		}

		if(priceStart > 0){
			if(!whereClause.isEmpty()){
				whereClause += " and price >= " + priceStart + " and price <= " + priceEnd + " ";
			} else {
				whereClause += " where price >= " + priceStart + " and price <= " + priceEnd + " ";
			}
		}

		if(!whereClause.isEmpty()){
			selectSQL +=  whereClause;
		}
	
		try{
			conn = Database.getConnection();

			stmt = conn.prepareStatement(selectSQL);  
			
			rs = stmt.executeQuery();  

			while(rs.next()){
				int tempId = rs.getInt(1);
				HotelLib oneHotel = new HotelLib(tempId, rs.getString(2), rs.getString(3), rs.getString(4), rs.getDouble(5), rs.getDouble(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13)); 
				
				PreparedStatement stmt2 = conn.prepareStatement("select img_src from hotel_image where id_hotel = "+tempId);
				ResultSet rs2 = stmt2.executeQuery();

				while(rs2.next()){
					oneHotel.addImage(rs2.getString(1));
				}

				PreparedStatement stmt3 = conn.prepareStatement("select id, price, name from hotel_price where id_hotel = "+tempId);
				ResultSet rs3 = stmt3.executeQuery();

				while(rs3.next()){
					oneHotel.addPrice(rs3.getInt(1), rs3.getDouble(2), rs3.getString(3));
				}

				hotelList.add(oneHotel);
			}

		} catch (SQLException e){
			throw new IOException(stmt.toString() + e.getMessage());  
		}

		return hotelList;
	}

	public static ArrayList<HotelLib> getAllHotel() throws IOException, SQLException, ClassNotFoundException{
		String userId = null;
		Connection conn = null; 
		PreparedStatement stmt = null;
		ResultSet rs = null;
		String selectSQL = "SELECT `id`, `name`, `description`, `overview`, `avg_rating`, `price`, `address`, `area`, `city`, `country`, `image_src`, `checkin`, `checkout` FROM hotels ";

		ArrayList<HotelLib> hotelList = new ArrayList<HotelLib>();

		try{
			conn = Database.getConnection();

			stmt = conn.prepareStatement(selectSQL);  
			
			rs = stmt.executeQuery();  

			while(rs.next()){
				int tempId = rs.getInt(1);
				HotelLib oneHotel = new HotelLib(tempId, rs.getString(2), rs.getString(3), rs.getString(4), rs.getDouble(5), rs.getDouble(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13)); 
				
				PreparedStatement stmt2 = conn.prepareStatement("select img_src from hotel_image where id_hotel = "+tempId);
				ResultSet rs2 = stmt2.executeQuery();

				while(rs2.next()){
					oneHotel.addImage(rs2.getString(1));
				}

				PreparedStatement stmt3 = conn.prepareStatement("select id, price, name from hotel_price where id_hotel = "+tempId);
				ResultSet rs3 = stmt3.executeQuery();

				while(rs3.next()){
					oneHotel.addPrice(rs3.getInt(1), rs3.getDouble(2), rs3.getString(3));
				}

				hotelList.add(oneHotel);
			}

		} catch (SQLException e){
			throw new SQLException(stmt.toString() + e.getMessage());  
		}

		return hotelList;
	}



	public static int addHotel(HotelLib htl) throws IOException{
		Connection conn = null; 
		PreparedStatement stmt = null;
		int autoId = -1;

		String insertQuery = "INSERT INTO `hotels`(`name`, `description`, `overview`, `price`, `address`, `area`, `city`, `country`, `checkin`, `checkout`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
		try{
			conn = Database.getConnection();

			stmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);  
			stmt.setString(1, htl.getName());
			stmt.setString(2, htl.getDescription());
			stmt.setString(3, htl.getOverview());
			stmt.setDouble(4, htl.getPrice());
			stmt.setString(5, htl.getAddress());
			stmt.setString(6, htl.getArea());
			stmt.setString(7, htl.getCity());
			stmt.setString(8, htl.getCountry());
			stmt.setString(9, htl.getCheckInTime());
			stmt.setString(10, htl.getCheckOutTime());

			int affectedRows = stmt.executeUpdate();

			if(affectedRows > 0) {
 				ResultSet generatedKeys = stmt.getGeneratedKeys();
    			
    			if (generatedKeys.next()) {
                
            
                	autoId = generatedKeys.getInt(1);
                	htl.setId(autoId);

                	insertQuery = "update hotels set image_src ='"+htl.getDefaultImage()+"' where id ="+htl.getId();

                	conn = Database.getConnection();
					stmt = conn.prepareStatement(insertQuery);
					stmt.executeUpdate();					
            	}else{
            		throw new SQLException("Unable to retrieve key"); 
            	}
			}

		} catch (SQLException e){
			throw new IOException(e.getMessage());  
		} catch (ClassNotFoundException e){
			throw new IOException(e.getMessage());
		}

		return autoId;
	}

	public static int updateHotel(HotelLib htl) throws IOException{
		Connection conn = null; 
		PreparedStatement stmt = null;
		int autoId = -1;

		String updateQuery = "update hotels set name = ?, description = ?, overview = ?, price = ?, address = ?, area = ?,  city = ?, country = ?, checkin = ?, checkout = ? where id = ? ";
	
		try{
			conn = Database.getConnection();

			stmt = conn.prepareStatement(updateQuery);  
			stmt.setString(1, htl.getName());
			stmt.setString(2, htl.getDescription());
			stmt.setString(3, htl.getOverview());
			stmt.setDouble(4, htl.getPrice());
			stmt.setString(5, htl.getAddress());
			stmt.setString(6, htl.getArea());
			stmt.setString(7, htl.getCity());
			stmt.setString(8, htl.getCountry());
			stmt.setString(9, htl.getCheckInTime());
			stmt.setString(10, htl.getCheckOutTime());
			stmt.setInt(11, htl.getId());

			int affectedRows = stmt.executeUpdate();


		} catch (SQLException e){
			throw new IOException(e.getMessage());  
		} catch (ClassNotFoundException e){
			throw new IOException(e.getMessage());
		}

		return autoId;
	}

	public static int addHotelPrice(String id, String price, String name) throws IOException{
		Connection conn = null; 
		PreparedStatement stmt = null;
		int autoId = -1;

		String insertQuery = "INSERT INTO `hotel_price`(`id_hotel`, `price`, `name`) VALUES (?, ?, ?)";
	
		try{
			conn = Database.getConnection();

			stmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);  
			stmt.setString(1, id);
			stmt.setString(2, price);
			stmt.setString(3, name);

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
			throw new IOException(e.getMessage());  
		} catch (ClassNotFoundException e){
			throw new IOException(e.getMessage());
		}

		return autoId;
	}

	public static void deleteHotelPrice(String id) throws IOException{
		Connection conn = null; 
		PreparedStatement stmt = null;

		String insertQuery = "delete from `hotel_price` where id = ?";
	
		try{
			conn = Database.getConnection();

			stmt = conn.prepareStatement(insertQuery);  
			stmt.setString(1, id);

			stmt.executeUpdate();

		} catch (SQLException e){
			throw new IOException(e.getMessage());  
		} catch (ClassNotFoundException e){
			throw new IOException(e.getMessage());
		}
	}

	public static int addHotelImage(String id, String imgSrc) throws IOException{
		Connection conn = null; 
		PreparedStatement stmt = null;
		int autoId = -1;

		String insertQuery = "INSERT INTO `hotel_image`(`id_hotel`, `img_src`) VALUES (?, ?)";
	
		try{
			conn = Database.getConnection();

			stmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);  
			stmt.setString(1, id);
			stmt.setString(2, imgSrc);

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
			throw new IOException(e.getMessage());  
		} catch (ClassNotFoundException e){
			throw new IOException(e.getMessage());
		}

		return autoId;
	}


	public static void deleteHotelImage(int htlId, String imgSrc) throws IOException{
		Connection conn = null; 
		PreparedStatement stmt = null;

		String insertQuery = "delete from `hotel_image` where id_hotel = ? and img_src = ?";
	
		try{
			conn = Database.getConnection();

			stmt = conn.prepareStatement(insertQuery);  
			stmt.setInt(1, htlId);
			stmt.setString(2, imgSrc);

			stmt.executeUpdate();

		} catch (SQLException e){
			throw new IOException(e.getMessage());  
		} catch (ClassNotFoundException e){
			throw new IOException(e.getMessage());
		}
	}

}
