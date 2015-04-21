package com.example.sample7locationmanager;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	LocationManager mLM;
	String mProvider;
	ListView listView;
	ArrayAdapter<Address> mAdapter;
	EditText keywordView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView)findViewById(R.id.listView1);
		mAdapter = new ArrayAdapter<Address>(this, android.R.layout.simple_list_item_1);
		listView.setAdapter(mAdapter);
		keywordView = (EditText)findViewById(R.id.edit_keyword);
		
		mProvider = LocationManager.GPS_PROVIDER;
		mLM = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		Button btn = (Button)findViewById(R.id.btn_singleupdate);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, MyService.class);
				intent.putExtra("count", 10);
				PendingIntent pi = PendingIntent.getService(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
				mLM.requestSingleUpdate(mProvider, pi);
			}
		});
		
		btn = (Button)findViewById(R.id.btn_search);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String keyword = keywordView.getText().toString();
				if (keyword != null && !keyword.equals("")) {
					geocoding(keyword);
				}
			}
		});
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Address addr = (Address)listView.getItemAtPosition(position);
				addProximity(addr);
			}
		});
	}

	private void addProximity(Address addr) {
		int aid = DataManager.addAddress(addr);
		Intent intent = new Intent(this, ProximityService.class);
		intent.setData(Uri.parse("myscheme://com.example.sample7locationmanager/"+aid));
		intent.putExtra("address", addr);
		PendingIntent pi = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		long expiration = System.currentTimeMillis() + 24 * 60 * 60 * 1000;
		mLM.addProximityAlert(addr.getLatitude(), addr.getLongitude(), 100, expiration, pi);
	}
	
	private void removeProximity(Address addr) {
		int aid = DataManager.getId(addr);
		Intent intent = new Intent(this, ProximityService.class);
		intent.setData(Uri.parse("myscheme://com.example.sample7locationmanager/"+aid));
		PendingIntent pi = PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		mLM.removeProximityAlert(pi);
	}
	
	LocationListener mListener = new LocationListener() {

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			switch(status) {
			case LocationProvider.AVAILABLE :
			case LocationProvider.OUT_OF_SERVICE :
			case LocationProvider.TEMPORARILY_UNAVAILABLE :
			}
		}

		@Override
		public void onProviderEnabled(String provider) {

		}

		@Override
		public void onProviderDisabled(String provider) {

		}

		@Override
		public void onLocationChanged(Location location) {
			Toast.makeText(
					MainActivity.this,
					"lat : " + location.getLatitude() + ", lng : "
							+ location.getLongitude(), Toast.LENGTH_SHORT)
					.show();
			mLM.removeUpdates(this);
			reverseGeocoding(location.getLatitude(), location.getLongitude());
		}
	};
	
	private void reverseGeocoding(double lat, double lng) {
		if (Geocoder.isPresent()) {
			Geocoder geo = new Geocoder(this, Locale.KOREAN);
			try {
				List<Address> addresses = geo.getFromLocation(lat, lng, 10);
				mAdapter.clear();
				for (Address addr : addresses) {
					mAdapter.add(addr);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}
	}

	private void geocoding(String keyword) {
		if (Geocoder.isPresent()) {
			Geocoder geo = new Geocoder(this, Locale.KOREAN);
			try {
				List<Address> addresses = geo.getFromLocationName(keyword, 10);
				mAdapter.clear();
				for (Address addr : addresses) {
					mAdapter.add(addr);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	boolean isFirst = true;
	@Override
	protected void onStart() {
		super.onStart();
		if (!mLM.isProviderEnabled(mProvider)) {
			if (isFirst) {
				startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
				isFirst = false;
			} else {
				Toast.makeText(this, "location settings....", Toast.LENGTH_SHORT).show();
				finish();
			}
			return;
		}
		
		Location location = mLM.getLastKnownLocation(mProvider);
		if (location != null) {
			mListener.onLocationChanged(location);
		}
		
		mLM.requestLocationUpdates(mProvider, 2000, 5, mListener);
	}

	@Override
	protected void onStop() {
		super.onStop();
		mLM.removeUpdates(mListener);
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
