package com.example.sample6locationmanager;

import java.util.ArrayList;

import android.location.Address;

public class DataManager {
	private static DataManager instance;
	public static DataManager getInstance() {
		if (instance == null) {
			instance = new DataManager();
		}
		return instance;
	}
	
	ArrayList<Address> items = new ArrayList<Address>();
	private DataManager() {
		
	}
	
	public int add(Address addr) {
		if (items.contains(addr)) {
			return items.indexOf(addr);
		}
		items.add(addr);
		return items.size() - 1;
	}
	
	public int indexOf(Address addr) {
		return items.indexOf(addr);
	}
	
	public ArrayList<Address> getList() {
		return items;
	}
}
