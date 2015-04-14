package com.example.examplelocationrequest;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.PendingIntent;
import android.content.ContentUris;
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
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	TextView messageView;
	ListView listView;
	EditText keywordView;
	LocationManager mLM;
	ArrayAdapter<Address> mAdapter;
	String mProvider = LocationManager.NETWORK_PROVIDER;

	private static final int MESSAGE_TIMEOUT_LOCATION_UPDATE = 1;
	private static final int TIMEOUT_LOCATION_UPDATE = 60 * 1000;
	
	Handler mHandler = new Handler(Looper.getMainLooper()) {
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case MESSAGE_TIMEOUT_LOCATION_UPDATE :
				messageView.setText("timeout location update");
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		messageView = (TextView) findViewById(R.id.text_message);
		keywordView = (EditText)findViewById(R.id.edit_keyword);
		listView = (ListView)findViewById(R.id.listView1);
		mAdapter = new ArrayAdapter<Address>(this, android.R.layout.simple_list_item_1);
		listView.setAdapter(mAdapter);
		mLM = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		Button btn = (Button)findViewById(R.id.btn_search);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String keyword = keywordView.getText().toString();
				if (keyword != null && !keyword.equals("")) {
					doGeocoding(keyword);
				}
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Address address = (Address)listView.getItemAtPosition(position);
				addProximity(id, address);
			}
		});
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				return false;
			}
		});
	}
	
	private void addProximity(long id, Address address) {
		Intent intent = new Intent(MainActivity.this, ProximityService.class);
		Uri data = ContentUris.withAppendedId(ProximityService.PROXIMITY_URI, id);
		intent.setData(data);
		intent.putExtra(ProximityService.EXTRA_ADDRESS, address);
		PendingIntent pi = PendingIntent.getService(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		double latitude = address.getLatitude();
		double longitude = address.getLongitude();
		float radius = 100;
		long expiration = System.currentTimeMillis() + 24 * 60 * 60 * 1000;
		mLM.addProximityAlert(latitude, longitude, radius, expiration, pi);
		ProximityData.addData(id, address);
	}

	private void removeProximity(long id) {
		Intent intent = new Intent(MainActivity.this, ProximityService.class);
		Uri data = ContentUris.withAppendedId(ProximityService.PROXIMITY_URI, id);
		intent.setData(data);
		PendingIntent pi = PendingIntent.getService(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		mLM.removeProximityAlert(pi);
	}
	
	private static final int MAX_RESULT_COUNT = 10;
	
	private void doGeocoding(String keyword) {
		if (Geocoder.isPresent()) {
			Geocoder geocoder = new Geocoder(this, Locale.KOREA);
			try {
				List<Address> list = geocoder.getFromLocationName(keyword, MAX_RESULT_COUNT);
				for (Address address : list) {
					mAdapter.add(address);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			Toast.makeText(this, "Geocoder is not implemented", Toast.LENGTH_SHORT).show();
		}
	}
	
	private void doReverseGeocoding(double latitude, double longitude) {
		if (Geocoder.isPresent()) {
			Geocoder geocoder = new Geocoder(this, Locale.KOREA);
			try {
				List<Address> list = geocoder.getFromLocation(latitude, longitude, MAX_RESULT_COUNT);
				for (Address address : list) {
					mAdapter.add(address);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			Toast.makeText(this, "Geocoder is not implemented", Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	protected void onStart() {
		super.onStart();
		registerLocationListener();
	}

	@Override
	protected void onStop() {
		super.onStop();
		unregisterLocationListener();
	}
	
	LocationListener mListener = new LocationListener() {

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			switch(status) {
			case LocationProvider.AVAILABLE :
				Toast.makeText(MainActivity.this, "Proivder Available", Toast.LENGTH_SHORT).show();
				break;
			case LocationProvider.OUT_OF_SERVICE :
			case LocationProvider.TEMPORARILY_UNAVAILABLE :
				messageView.setText("Proivder unavailable");
				break;
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
			mHandler.removeMessages(MESSAGE_TIMEOUT_LOCATION_UPDATE);
			messageView.setText("latitude : " + location.getLatitude()
					+ ", longitude : " + location.getLongitude());
			doReverseGeocoding(location.getLatitude(), location.getLongitude());
		}
	};

	boolean isFirst = true;
	
	private void registerLocationListener() {
		if (!mLM.isProviderEnabled(mProvider)) {
			if (isFirst) {
				isFirst = false;
				startActivity(new Intent(
						Settings.ACTION_LOCATION_SOURCE_SETTINGS));
			} else {
				Toast.makeText(this, "Lcoation Source Not Enabled",
						Toast.LENGTH_SHORT).show();
				finish();
			}
			return;
		}

		Location location = mLM.getLastKnownLocation(mProvider);
		if (location != null) {
			mListener.onLocationChanged(location);
		}
		mLM.requestLocationUpdates(mProvider, 2000, 10, mListener);
		Message msg = mHandler.obtainMessage(MESSAGE_TIMEOUT_LOCATION_UPDATE);
		mHandler.sendMessageDelayed(msg, TIMEOUT_LOCATION_UPDATE);
	}

	private void unregisterLocationListener() {
		mLM.removeUpdates(mListener);
		mHandler.removeMessages(MESSAGE_TIMEOUT_LOCATION_UPDATE);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
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
