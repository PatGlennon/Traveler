package com.example.traveler;

import java.io.Serializable;


public class TravelPoint implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String description;
	private int latitude, longitude;
	private String city, state, date; 
	private String imgLocation = null;
	
	// Empty Constructor
	public TravelPoint(){}
	
	public TravelPoint(String desc, String city, String state, String date, int lat, int lng, String imgLocation){
		description = desc;
		this.date = date;
		this.city = city;
		this.state = state;
		latitude = lat;
		longitude = lng;
		this.imgLocation = imgLocation;
	}
	
	public String getDescription(){
		return description;
	}
	public String getDate(){
		return date;
	}
	public int getLatitude(){
		return latitude;
	}
	public int getLongitude(){
		return longitude;
	}
	public String getCity(){
		return city;
	}
	public String getState(){
		return state;
	}
	public String getImageLocation(){
		return imgLocation;
	}
	public String toString(){
		String s = description + date + latitude + longitude + city + state + imgLocation;
		return s;
	}

}
