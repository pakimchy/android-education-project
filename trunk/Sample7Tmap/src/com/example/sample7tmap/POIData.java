package com.example.sample7tmap;

import com.skp.Tmap.TMapPOIItem;

public class POIData {
	TMapPOIItem poi;
	@Override
	public String toString() {
		return poi.getPOIName();
	}
}
