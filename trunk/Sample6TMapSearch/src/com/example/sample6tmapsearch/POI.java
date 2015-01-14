package com.example.sample6tmapsearch;

public class POI {
	public String id;
	public String name;
	public String telNo;
	public String desc;
	
	@Override
	public String toString() {
		return name+"\n\r("+telNo+")";
	}
}
