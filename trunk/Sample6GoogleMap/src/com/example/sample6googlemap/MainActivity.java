package com.example.sample6googlemap;

import java.util.HashMap;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sample6googlemap.NetworkManager.OnResultListener;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends ActionBarActivity implements
	GoogleMap.OnCameraChangeListener,
	GoogleMap.OnMarkerClickListener,
	GoogleMap.OnInfoWindowClickListener {

	GoogleMap mMap;

	LocationManager mLM;
	
	HashMap<POI, Marker> markerResolver = new HashMap<POI, Marker>();
	HashMap<Marker, POI> poiResolver = new HashMap<Marker, POI>();
	EditText keywordView;
	ListView listView;
	ArrayAdapter<POI> mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setupMapIfNeeded();
		mLM = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		
		keywordView = (EditText)findViewById(R.id.edit_keyword);
		listView = (ListView)findViewById(R.id.listView1);
		mAdapter = new ArrayAdapter<POI>(this, android.R.layout.simple_list_item_1);
		listView.setAdapter(mAdapter);
		
		Button btn = (Button)findViewById(R.id.btn_zoom_in);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CameraUpdate update = CameraUpdateFactory.zoomIn();
				mMap.animateCamera(update);
			}
		});
		
		btn = (Button)findViewById(R.id.btn_zoom_out);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CameraUpdate update = CameraUpdateFactory.zoomOut();
				mMap.animateCamera(update);
			}
		});
		
		btn = (Button)findViewById(R.id.btn_marker);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				CameraPosition position = mMap.getCameraPosition();
				POI poi = new POI();
				poi.name = "poi name" + count++;
				addMarker(position.target, poi);
			}
		});
		
		btn = (Button)findViewById(R.id.btn_search);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String keyword = keywordView.getText().toString();
				if (keyword != null && !keyword.equals("")) {
					NetworkManager.getInstnace().getPoiSearch(MainActivity.this, keyword, new OnResultListener<SearchPOIResult>() {
						
						@Override
						public void onSuccess(SearchPOIResult result) {
							for (int i = 0; i < mAdapter.getCount(); i++) {
								POI poi = mAdapter.getItem(i);
								Marker m = markerResolver.get(poi);
								m.remove();
							}
							mAdapter.clear();
							
							for (POI poi : result.searchPoiInfo.pois.poi) {
								mAdapter.add(poi);
								LatLng target = new LatLng(poi.getLatitude(), poi.getLongitude());
								addMarker(target, poi);
							}
						}
						
						@Override
						public void onFail(int code) {
							
						}
					});
				}
			}
		});
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				POI poi = (POI)listView.getItemAtPosition(position);
				Marker marker = markerResolver.get(poi);
				CameraUpdate update = CameraUpdateFactory.newLatLng(marker.getPosition());
				mMap.animateCamera(update);
				marker.showInfoWindow();
			}
		});
	}

	int count = 0;
	
	private void addMarker(LatLng target, POI poi) {
		MarkerOptions options = new MarkerOptions();
		options.position(target);
		options.title(poi.name);
		options.snippet(poi.telNo);
		options.icon(BitmapDescriptorFactory.defaultMarker());
		options.anchor(0.5f, 1.0f);
		options.draggable(true);
		Marker marker = mMap.addMarker(options);
		markerResolver.put(poi, marker);
		poiResolver.put(marker, poi);
	}

	LocationListener mListener = new LocationListener() {
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void onLocationChanged(Location location) {
			moveMap(location);
			mLM.removeUpdates(mListener);
		}
	};
	
	private void moveMap(Location location) {
		if (mMap != null) {
			CameraPosition.Builder builder = new CameraPosition.Builder();
			builder.target(new LatLng(location.getLatitude(), location.getLongitude()));
			builder.zoom(15.5f);
//			builder.bearing(20);
//			builder.tilt(30);
			CameraUpdate update = CameraUpdateFactory.newCameraPosition(builder.build());
//			mMap.moveCamera(update);
			mMap.animateCamera(update);
		}
	}
	
	String mProvider = LocationManager.GPS_PROVIDER;
	
	@Override
	protected void onStart() {
		super.onStart();
		Location location = mLM.getLastKnownLocation(mProvider);
		if (location != null) {
			mListener.onLocationChanged(location);
		}
		mLM.requestLocationUpdates(mProvider, 0, 0, mListener);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		mLM.removeUpdates(mListener);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		setupMapIfNeeded();
	}

	private void setupMapIfNeeded() {
		if (mMap == null) {
			mMap = ((SupportMapFragment) getSupportFragmentManager()
					.findFragmentById(R.id.fragment1)).getMap();
			if (mMap != null) {
				setupMap();
			}
		}
	}

	private void setupMap() {
		mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		mMap.setMyLocationEnabled(true);
		mMap.getUiSettings().setZoomControlsEnabled(true);
		mMap.getUiSettings().setCompassEnabled(true);
		mMap.setOnCameraChangeListener(this);
		mMap.setOnMarkerClickListener(this);
		mMap.setOnInfoWindowClickListener(this);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onCameraChange(CameraPosition position) {
		LatLng latlng = position.target;
		Log.i("MainActivity","lat : " + latlng.latitude + ", lng : " + latlng.longitude);
	}

	@Override
	public boolean onMarkerClick(Marker marker) {
		POI poi = poiResolver.get(marker);
		Toast.makeText(this, "marker click : " + poi.name , Toast.LENGTH_SHORT).show();
		marker.showInfoWindow();
		return true;
	}

	@Override
	public void onInfoWindowClick(Marker marker) {
		marker.hideInfoWindow();
	}
}
