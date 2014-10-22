package com.example.sample5googlemap;

import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;

public class MyInfoWindowAdapter implements InfoWindowAdapter {

	View infoWindow;
	TextView nameView;
	TextView addressView;
	TextView typeView;
	HashMap<Marker, POI> mResolver;

	public MyInfoWindowAdapter(Context context, HashMap<Marker, POI> resolver) {
		infoWindow = LayoutInflater.from(context).inflate(
				R.layout.info_window_layout, null);
		nameView = (TextView) infoWindow.findViewById(R.id.name);
		addressView = (TextView) infoWindow.findViewById(R.id.address);
		typeView = (TextView) infoWindow.findViewById(R.id.type);
		mResolver = resolver;
	}

	@Override
	public View getInfoContents(Marker m) {
		POI poi = mResolver.get(m);
		if (poi != null) {
			nameView.setText(poi.name);
			addressView.setText(poi.upperAddrName + poi.middleAddrName);
			typeView.setText(poi.upperBizName);
			return infoWindow;
		} else {
			return null;
		}
	}

	@Override
	public View getInfoWindow(Marker m) {
		return null;
	}

}
