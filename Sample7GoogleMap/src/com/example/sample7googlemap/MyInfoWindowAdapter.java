package com.example.sample7googlemap;

import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;

public class MyInfoWindowAdapter implements InfoWindowAdapter {

	View view;
	TextView nameView, addressView;
	Map<Marker, POI> mPoiResolver;
	public MyInfoWindowAdapter(Context context, Map<Marker,POI> poiResolver) {
		view = LayoutInflater.from(context).inflate(R.layout.info_layout, null);
		nameView = (TextView)view.findViewById(R.id.text_name);
		addressView = (TextView)view.findViewById(R.id.text_address);
		mPoiResolver = poiResolver;
	}
	@Override
	public View getInfoContents(Marker marker) {
		POI poi = mPoiResolver.get(marker);
		if (poi != null) {
			nameView.setText(poi.name);
			addressView.setText(poi.lowerAddrName);
		} else {
			nameView.setText(marker.getTitle());
			addressView.setText(marker.getSnippet());
		}
		return view;
	}

	@Override
	public View getInfoWindow(Marker marker) {
		return null;
	}

}
