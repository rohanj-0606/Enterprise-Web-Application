package com.hotel.libraries;

import com.hotel.config.*;
import java.util.*;  

public class SearchLib{
	private String checkIn;
	private String checkOut;
	private int roomCount;
	private int id;

	public SearchLib(int id, String checkIn, String checkOut, int roomCount){
		this.id = id;
		this.checkIn = checkIn;
		this.checkOut = checkOut;
		this.roomCount = roomCount;
	}

	public String getCheckIn(){
		return checkIn;
	}

	public String getCheckOut(){
		return checkOut;
	}

	public int getRoomCount(){
		return roomCount;
	}

	public int getId(){
		return id;
	}
}