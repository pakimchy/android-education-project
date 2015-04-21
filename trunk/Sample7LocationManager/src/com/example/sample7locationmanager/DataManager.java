package com.example.sample7locationmanager;

import java.util.ArrayList;
import java.util.List;

import android.location.Address;

public class DataManager {
	static List<Address> db = new ArrayList<Address>();
	public static int addAddress(Address addr) {
		if (db.contains(addr)) 
			return db.indexOf(addr);
		
		db.add(addr);
		return db.size() - 1;
	}
	
	public static List<Address> list() {
		return db;
	}
	
	public static int getId(Address addr) {
		return db.indexOf(addr);
	}
	
}
