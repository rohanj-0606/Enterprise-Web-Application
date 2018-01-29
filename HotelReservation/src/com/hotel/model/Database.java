package com.hotel.model;
import com.hotel.config.*;
import java.sql.*;

public class Database{
	private static Connection conn;

	static{
		conn = null;
	}

	private Database() throws ClassNotFoundException, SQLException{
			Class.forName("com.mysql.jdbc.Driver");  
			conn = DriverManager.getConnection( Constants.dbUrl, Constants.dbUser, Constants.dbPass);
	}

	public static Connection getConnection() throws ClassNotFoundException, SQLException{
		if(conn == null){
			new Database();
		}
		return conn;
	}
}
