package com.hotel.libraries;

import com.hotel.config.*;
import java.util.*;  

public class HotelPrice{
	private double price;
	private String name;
	private int priceId;

	public HotelPrice(int id, double p, String n){
		price = p;
		name = n;
		priceId = id;
	}

	public int getId(){
		return priceId;
	}

	public String getName(){
		return name;
	}

	public double getPrice(){
		return price;
	}
}