package com.example.sample6googlemap;

import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;

public class MyInfoWindow implements InfoWindowAdapter {

	View view;
	TextView nameView;
	TextView telView;
	TextView descView;
	HashMap<Marker, POI> poiResolver;

	public MyInfoWindow(Context context, HashMap<Marker,POI> resolver) {
		poiResolver = resolver;
		view = LayoutInflater.from(context).inflate(R.layout.info_window_layout, null);
		nameView = (TextView)view.findViewById(R.id.text_name);
		telView = (TextView)view.findViewById(R.id.text_tel);
		descView = (TextView)view.findViewById(R.id.text_desc);
	}
	
	@Override
	public View getInfoContents(Marker marker) {
//		return null;
		POI poi = poiResolver.get(marker);
		nameView.setText(poi.name);
		telView.setText("tel:"+poi.telNo);
		descView.setText("desc : " + poi.desc);
		return view;
	}

	@Override
	public View getInfoWindow(Marker marker) {
//		POI poi = poiResolver.get(marker);
//		nameView.setText(poi.name);
//		telView.setText("tel:"+poi.telNo);
//		descView.setText("desc : " + poi.desc);
//		return view;
		return null;
	}

}
