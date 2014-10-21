package com.example.sample5googlemap;

public class POI {
	public String id;
	public String name;
	public String telNo;
	public String frontLat;
	public String frontLon;
	public String noorLat;
	public String noorLon;
	public String upperAddrName;
	public String middleAddrName;
	public String lowerAddrName;
	public String detailAddrName;
	public String firstNo;
	public String secondNo;
	public String upperBizName;
	public String middleBizName;
	public String desc;
	
	@Override
	public String toString() {
		return name + "(" + telNo + ")";
	}
	
	public double getLatitude() {
		return (Double.parseDouble(frontLat) + Double.parseDouble(noorLat)) / 2;
	}
	
	public double getLongitude() {
		return (Double.parseDouble(frontLon) + Double.parseDouble(noorLon)) / 2;
	}
}
