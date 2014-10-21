package com.example.sample5tmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.skp.Tmap.TMapMarkerItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapView;
import com.skp.Tmap.TMapView.OnApiKeyListenerCallback;

public class MainActivity extends ActionBarActivity {

	TMapView mMapView;
	LocationManager mLM;
	String mProvider = LocationManager.GPS_PROVIDER;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mLM = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		mMapView = (TMapView)findViewById(R.id.mapView);
		
		mMapView.setSKPMapApiKey("458a10f5-c07e-34b5-b2bd-4a891e024c2a");
		
		mMapView.setOnApiKeyListener(new OnApiKeyListenerCallback() {
			
			@Override
			public void SKPMapApikeySucceed() {
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						Toast.makeText(MainActivity.this, "api setup success", Toast.LENGTH_SHORT).show();
						setupMap();
					}
				});
			}
			
			@Override
			public void SKPMapApikeyFailed(String arg0) {
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						Toast.makeText(MainActivity.this, "api setup fail", Toast.LENGTH_SHORT).show();						
					}
				});
			}
		});
		
		Button btn = (Button)findViewById(R.id.btn_zoom_in);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mMapView.MapZoomIn();
			}
		});
		
		btn = (Button)findViewById(R.id.btn_zoom_out);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mMapView.MapZoomOut();
			}
		});
		
		btn = (Button)findViewById(R.id.btn_add_marker);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				TMapPoint point = mMapView.getCenterPoint();
				addMarker(point.getLatitude(), point.getLongitude());
			}
		});
		
	}
	
	int id = 0;
	private void addMarker(double lat, double lng) {
		TMapMarkerItem item = new TMapMarkerItem();
		Bitmap bm = ((BitmapDrawable)getResources().getDrawable(android.R.drawable.ic_dialog_alert)).getBitmap();
		item.setIcon(bm);
		item.setTMapPoint(new TMapPoint(lat, lng));
		item.setPosition(0.5f, 1);
		item.setCalloutTitle("marker");
		item.setCalloutSubTitle("my icon");
		Bitmap left = ((BitmapDrawable)getResources().getDrawable(android.R.drawable.ic_dialog_email)).getBitmap();
		Bitmap right = ((BitmapDrawable)getResources().getDrawable(android.R.drawable.ic_dialog_map)).getBitmap();
		item.setCalloutLeftImage(left);
		item.setCalloutRightButtonImage(right);
		item.setCanShowCallout(true);
		
		mMapView.addMarkerItem("marker" + id++, item);
		
	}
	
	LocationListener mListener = new LocationListener() {
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			
		}
		
		@Override
		public void onProviderEnabled(String provider) {
			
		}
		
		@Override
		public void onProviderDisabled(String provider) {
			
		}
		
		@Override
		public void onLocationChanged(Location location) {
			mLM.removeUpdates(mListener);
			if (isInitialized) {
				moveMap(location);
			} else {
				cacheLocation = location;
			}
		}
	};
	
	Location cacheLocation = null;
	
	private void moveMap(Location location) {
		mMapView.setCenterPoint(location.getLongitude(), location.getLatitude());
	}
	
	private void setMyLocation(double lat, double lng) {
		mMapView.setLocationPoint(lng, lat);
		Bitmap icon = ((BitmapDrawable)getResources().getDrawable(R.drawable.ic_launcher)).getBitmap();
		mMapView.setIcon(icon);
		mMapView.setIconVisibility(true);
	}

	@Override
	protected void onStart() {
		super.onStart();
		Location location = mLM.getLastKnownLocation(mProvider);
		if (location != null) {
			mListener.onLocationChanged(location);
		}
		mLM.requestLocationUpdates(mProvider, 10000, 0, mListener);
	}
	
	@Override
	protected void onStop() {
		super.onStop();
		mLM.removeUpdates(mListener);
	}
	
	boolean isInitialized = false;
	
	private void setupMap() {
		isInitialized = true;
		mMapView.setLanguage(mMapView.LANGUAGE_KOREAN);
		mMapView.setMapType(mMapView.MAPTYPE_STANDARD);
		mMapView.setTrafficInfo(true);
//		mMapView.setCompassMode(true);
//		mMapView.setTrackingMode(true);
		setMyLocation(37.46628337, 126.9605881);
		
		mMapView.setOnCalloutRightButtonClickListener(new TMapView.OnCalloutRightButtonClickCallback() {
			
			@Override
			public void onCalloutRightButton(TMapMarkerItem item) {
				Toast.makeText(MainActivity.this, "item : " + item.getID(), Toast.LENGTH_SHORT).show();
			}
		});
		if (cacheLocation != null) {
			moveMap(cacheLocation);
		}
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
