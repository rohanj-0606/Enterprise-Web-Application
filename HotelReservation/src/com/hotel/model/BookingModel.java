package com.hotel.model;
import com.hotel.config.*;
import com.hotel.libraries.*;
import java.sql.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;  
import java.util.Date;  

public class BookingModel{
	
	public static double getDays(String day1, String day2) throws IOException{
		try{
			Date date1 = new SimpleDateFormat("yyyy-mm-dd").parse(day1);
			Date date2 = new SimpleDateFormat("yyyy-mm-dd").parse(day2);

			return (date2.getTime() - date1.getTime()) / (1000 * 60 * 60 * 24);
		}catch (ParseException e){
			throw new IOException(e.getMessage());
		}
	}

	public static int addBooking(int userId, int searchId, int roomId, int hotelId, String guestName, String guestMobile) throws IOException, SQLException{
		Connection conn = null; 
		PreparedStatement stmt = null;
		int autoId = -1;

		String insertQuery = "INSERT INTO `booking_info`(`id_user`, `id_hotel`, `id_price`, `room_count`, `check_in`, `check_out`, `totalPrice`, `bookingDate`, `guest_name`, `guest_mobile`) VALUES (?, ?, ?, ?, ?, ?, ?, now(), ?, ?)";
		
		try{
			conn = Database.getConnection();

			stmt = conn.prepareStatement(insertQuery, Statement.RETURN_GENERATED_KEYS);  
			stmt.setInt(1, userId);
			stmt.setInt(2, hotelId);
			stmt.setInt(3, roomId);
			
			SearchLib tempRslt = SearchModel.getResult(searchId);

			stmt.setInt(4, tempRslt.getRoomCount());
			stmt.setString(5, tempRslt.getCheckIn());
			stmt.setString(6, tempRslt.getCheckOut());
			stmt.setDouble(7, PriceModel.getPrice(roomId) * BookingModel.getDays(tempRslt.getCheckIn(), tempRslt.getCheckOut()));
			stmt.setString(8, guestName);
			stmt.setString(9, guestMobile);

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
		} catch (IOException e){
			throw new IOException(e.getMessage());
		}

		return autoId;
	}
}
