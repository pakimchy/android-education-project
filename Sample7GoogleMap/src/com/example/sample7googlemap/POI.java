package com.example.sample7googlemap;

public class POI {
	String id;
	String name;
	String frontLat;
	String frontLon;
	String noorLat;
	String noorLon;
	
	String lowerAddrName;
	
	double latitude = Double.MIN_VALUE;
	public double getLatitude() {
		if (latitude == Double.MIN_VALUE) {
			latitude = (Double.parseDouble(frontLat) + Double.parseDouble(noorLat)) / 2;
		}
		return latitude;
	}
	
	double longitude = Double.MIN_VALUE;
	public double getLongitude() {
		if (longitude == Double.MIN_VALUE) {
			longitude = (Double.parseDouble(frontLon) + Double.parseDouble(noorLon)) / 2;
		}
		return longitude;
	}
	
	@Override
	public String toString() {
		return name;
	}
}
