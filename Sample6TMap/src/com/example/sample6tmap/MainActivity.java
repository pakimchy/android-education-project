package com.example.sample6tmap;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.skp.Tmap.TMapView;

public class MainActivity extends ActionBarActivity {

	TMapView mapView;
	LocationManager mLM;
	
	private static final String API_KEY = "458a10f5-c07e-34b5-b2bd-4a891e024c2a";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mLM = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		mapView = (TMapView)findViewById(R.id.mapView);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				mapView.setSKPMapApiKey(API_KEY);
				mapView.setLanguage(TMapView.LANGUAGE_KOREAN);
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						setupMap();
					}
				});
			}
		}).start();
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
			if (isInitialized) {
				moveMap(location);
			} else {
				cacheLocation = location;
			}
			mLM.removeUpdates(this);
		}
	};
	
	private void moveMap(Location location) {
		mapView.setCenterPoint(location.getLongitude(), location.getLatitude());
	}
	
	boolean isInitialized = false;
	Location cacheLocation = null;
	String mProvider;
	@Override
	protected void onStart() {
		super.onStart();
		mProvider = LocationManager.GPS_PROVIDER;
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
	
	private void setupMap() {
		isInitialized = true;
		mapView.setMapType(TMapView.MAPTYPE_STANDARD);
		mapView.setTrafficInfo(true);
		if (cacheLocation != null) {
			moveMap(cacheLocation);
		}
//		mapView.setSightVisible(true);
//		mapView.setCompassMode(true);
//		mapView.setTrackingMode(true);
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
}
