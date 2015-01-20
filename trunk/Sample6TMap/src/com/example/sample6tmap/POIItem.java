package com.example.sample6tmap;

import com.skp.Tmap.TMapPOIItem;

public class POIItem {
	TMapPOIItem poi;
	@Override
	public String toString() {
		return poi.getPOIName();
	}
}
