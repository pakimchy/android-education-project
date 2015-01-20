package com.example.sample6tmap;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
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
import com.skp.Tmap.TMapPOIItem;
import com.skp.Tmap.TMapPoint;
import com.skp.Tmap.TMapView;

public class MainActivity extends ActionBarActivity {

	TMapView mapView;
	LocationManager mLM;

	private static final String API_KEY = "458a10f5-c07e-34b5-b2bd-4a891e024c2a";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mLM = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		mapView = (TMapView) findViewById(R.id.mapView);
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

		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				TMapPoint point = mapView.getCenterPoint();
				TMapPoint pp = new TMapPoint(point.getLatitude(), point
						.getLongitude());
				TMapMarkerItem item = new TMapMarkerItem();
				item.setTMapPoint(pp);
				Bitmap icon = ((BitmapDrawable) getResources().getDrawable(
						R.drawable.ic_popup_reminder)).getBitmap();
				item.setIcon(icon);
				item.setPosition(0.5f, 1.0f);
				item.setCalloutTitle("my icon");
				item.setCalloutSubTitle("marker test");
				Bitmap lefticon = ((BitmapDrawable) getResources().getDrawable(
						R.drawable.star_big_on)).getBitmap();
				item.setCalloutLeftImage(lefticon);
				Bitmap rightbutton = ((BitmapDrawable) getResources()
						.getDrawable(R.drawable.alert_dialog_icon)).getBitmap();
				item.setCalloutRightButtonImage(rightbutton);
				item.setCanShowCallout(true);
				item.setName("ysi");

				mapView.addMarkerItem("id" + count, item);
			}
		});
	}

	int count = 0;
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
				setMyLocation(location);
			} else {
				cacheLocation = location;
			}
			mLM.removeUpdates(this);
		}
	};

	private void moveMap(Location location) {
		mapView.setCenterPoint(location.getLongitude(), location.getLatitude());
	}

	private void setMyLocation(Location location) {
		mapView.setLocationPoint(location.getLongitude(),
				location.getLatitude());
		Bitmap icon = ((BitmapDrawable) getResources().getDrawable(
				R.drawable.ic_launcher)).getBitmap();
		mapView.setIcon(icon);
		mapView.setIconVisibility(true);
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

		mapView.setOnCalloutRightButtonClickListener(new TMapView.OnCalloutRightButtonClickCallback() {

			@Override
			public void onCalloutRightButton(TMapMarkerItem item) {
				String name = item.getName();
				if (name.equals("ysi")) {
					Toast.makeText(MainActivity.this, "ysi marker click",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		mapView.setOnClickListenerCallBack(new TMapView.OnClickListenerCallback() {

			@Override
			public boolean onPressUpEvent(ArrayList<TMapMarkerItem> markerlist,
					ArrayList<TMapPOIItem> poilist, TMapPoint point, PointF pt) {
				Toast.makeText(MainActivity.this, "press up event", Toast.LENGTH_SHORT).show();
				return false;
			}

			@Override
			public boolean onPressEvent(ArrayList<TMapMarkerItem> arg0,
					ArrayList<TMapPOIItem> arg1, TMapPoint arg2, PointF arg3) {
				Toast.makeText(MainActivity.this, "press event", Toast.LENGTH_SHORT).show();
				return false;
			}
		});
		if (cacheLocation != null) {
			moveMap(cacheLocation);
			setMyLocation(cacheLocation);
		}
		// mapView.setSightVisible(true);
		// mapView.setCompassMode(true);
		// mapView.setTrackingMode(true);
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
