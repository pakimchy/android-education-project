package com.example.sample6googlemap;

public class POI {
	public String id;
	public String name;
	public String telNo;
	public String desc;
	public String frontLat;
	public String frontLon;
	public String noorLat;
	public String noorLon;
	
	public double getLatitude() {
		return (Double.parseDouble(frontLat) + Double.parseDouble(noorLat)) / 2;
	}
	
	public double getLongitude() {
		return (Double.parseDouble(frontLon) + Double.parseDouble(noorLon)) / 2;
	}
	
	@Override
	public String toString() {
		return name+"\n\r("+telNo+")";
	}
}
