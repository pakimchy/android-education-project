package com.example.examplelocationrequest;

import java.util.ArrayList;
import java.util.List;

import android.location.Address;

public class ProximityData {
	public long id;
	public Address address;
	
	private static List<ProximityData> list = new ArrayList<ProximityData>();
	
	public static List<ProximityData> getList() {
		return list;
	}
	
	public static boolean addData(long id, Address address) {
		ProximityData old = getData(id);
		if (old != null) {
			if (old.address == address) {
				return false;
			} else {
				list.remove(old);
			}
		}
		ProximityData data = new ProximityData();
		data.id = id;
		data.address = address;
		list.add(data);
		return true;
	}
	
	public static ProximityData getData(long id) {
		for (ProximityData data : list) {
			if (data.id == id) {
				return data;
			}
		}
		return null;
	}
	
	public static boolean removeData(long id) {
		ProximityData old = getData(id);
		if (old != null) {
			list.remove(old);
			return true;
		}
		return false;
	}
}
