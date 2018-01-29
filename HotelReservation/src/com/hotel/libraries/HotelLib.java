package com.hotel.libraries;

import com.hotel.config.*;
import java.util.*;  

public class HotelLib{
	private int id;
	private String name;
	private String description;
	private String overview;
	private double averageRating;
	private double price;
	private String address;
	private String area;
	private String city;
	private String country;
	private String defaultImage;
	private String checkInTime;
	private String checkOutTime;

	private ArrayList<String> carauselImage;
	private ArrayList<HotelPrice> hotelPrice;



	public HotelLib(int id, String name, String description, String overview, double averageRating, double price, String address, String area, String city, String country, String defaultImage, String checkInTime, String checkOutTime){
		this.id = id;
		this.name = name;
		this.description = description;
		this.overview = overview;
		this.averageRating = averageRating;
		this.price = price;
		this.address = address;
		this.area = area;
		this.city = city;
		this.country = country;
		this.defaultImage = defaultImage;
		this.checkInTime = checkInTime;
		this.checkOutTime = checkOutTime;
		carauselImage = new ArrayList<String>();
		hotelPrice = new ArrayList<HotelPrice>();
		carauselImage.add(defaultImage);
	}

	public HotelLib(String name, String description, String overview, double price, String address, String area, String city, String country, String checkInTime, String checkOutTime){
		this.id = 0;
		this.name = name;
		this.description = description;
		this.overview = overview;
		this.averageRating = 0.00;
		this.price = price;
		this.address = address.toUpperCase();
		this.area = area.toUpperCase();
		this.city = city.toUpperCase();
		this.country = country.toUpperCase();
		this.defaultImage = "";
		this.checkInTime = checkInTime;
		this.checkOutTime = checkOutTime;
		carauselImage = new ArrayList<String>();
		hotelPrice = new ArrayList<HotelPrice>();
	}

	public HotelLib(int id, String name, String description, String overview, double price, String address, String area, String city, String country, String checkInTime, String checkOutTime){
		this.id = id;
		this.name = name;
		this.description = description;
		this.overview = overview;
		this.averageRating = 0.00;
		this.price = price;
		this.address = address;
		this.area = area;
		this.city = city;
		this.country = country;
		this.defaultImage = "";
		this.checkInTime = checkInTime;
		this.checkOutTime = checkOutTime;
		carauselImage = new ArrayList<String>();
		hotelPrice = new ArrayList<HotelPrice>();
	}

	public void addImage(String img){
		if(!img.isEmpty()){
			carauselImage.add(img);
		}
	}

	public void setId(int id){
		this.id = id;
		defaultImage = "img/"+String.valueOf(id)+"-htl-default.jpg";
	}

	public void setDefaultImage(String img){
		this.defaultImage = img;
	}

	public void addPrice(int priceId, double price, String suiteName){
			HotelPrice htlPrice = new HotelPrice(priceId, price, suiteName);
			hotelPrice.add(htlPrice);
	}

	public ArrayList<String> getAllImage(){
		return carauselImage;
	}

	public ArrayList<HotelPrice> getAllPrice(){
		return hotelPrice;
	}

	public int getId(){
		return id;
	}

	public String getName(){
		return name.toUpperCase();
	}

	public String getDescription(){
		return description;
	}

	public String getOverview(){
		return overview;
	}

	public String getAddress(){
		return address.toUpperCase();
	}

	public String getCity(){
		return city.toUpperCase();
	}

	public String getCountry(){
		return country.toUpperCase();
	}	


	public String getCheckInTime(){
		return checkInTime;
	}	

	public String getCheckOutTime(){
		return checkOutTime;
	}	

	public String getDefaultImage(){
		return defaultImage;
	}

	public String getArea(){
		return area.toUpperCase();
	}

	public double getRating(){
		return averageRating;
	}

	public double getPrice(){
		return price;
	}

	public String getImage(){
		return defaultImage;
	}
}
