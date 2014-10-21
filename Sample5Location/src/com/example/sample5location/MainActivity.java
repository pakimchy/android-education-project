package com.example.sample5location;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
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
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	LocationManager mLM;

	String mProvider = LocationManager.NETWORK_PROVIDER;

	ListView listView;
	ArrayAdapter<Address> mAdapter;
	EditText keywordView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView)findViewById(R.id.listView1);
		mAdapter = new ArrayAdapter<Address>(this, android.R.layout.simple_list_item_1, new ArrayList<Address>());
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Address addr = (Address)listView.getItemAtPosition(position);
				Intent intent = new Intent(MainActivity.this, AlertService.class);
				intent.setData(Uri.parse("myscheme://mydata/" + id));
				intent.putExtra("address", addr);
				PendingIntent pi = PendingIntent.getService(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
				
				mLM.addProximityAlert(addr.getLatitude(), addr.getLongitude(), 100, -1, pi);
				
			}
		});
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Address addr = (Address)listView.getItemAtPosition(position);
				Intent intent = new Intent(MainActivity.this, AlertService.class);
				intent.setData(Uri.parse("myscheme://mydata/" + id));
				PendingIntent pi = PendingIntent.getService(MainActivity.this, 0, intent, 0);
				
				mLM.removeProximityAlert(pi);
				
				return true;
			}
		});
		keywordView = (EditText)findViewById(R.id.editText1);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String keyword = keywordView.getText().toString();
				if (keyword != null && !keyword.equals("")) {
					if (Geocoder.isPresent()) {
						Geocoder geo = new Geocoder(MainActivity.this, Locale.getDefault());
						try {
							List<Address> addresses = geo.getFromLocationName(keyword, 10);
							for (Address addr : addresses) {
//								addr.getLatitude(), addr.getLongitude()
								mAdapter.add(addr);
							}
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		});
		mLM = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		criteria.setBearingRequired(true);
		criteria.setSpeedRequired(true);
		criteria.setAltitudeRequired(true);
		criteria.setCostAllowed(true);
		
		mProvider = mLM.getBestProvider(criteria, true);
		Toast.makeText(this, "provider : " + mProvider, Toast.LENGTH_SHORT).show();
		
		

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
			Toast.makeText(
					MainActivity.this,
					"Location : " + location.getLatitude() + ","
							+ location.getLongitude(), Toast.LENGTH_SHORT)
					.show();
			mLM.removeUpdates(this);
			if (Geocoder.isPresent()) {
				Geocoder geo = new Geocoder(MainActivity.this, Locale.getDefault());
				try {
					List<Address> addresses = geo.getFromLocation(location.getLatitude(), location.getLongitude(), 10);
					Toast.makeText(MainActivity.this, "addresses : " + addresses.toString(), Toast.LENGTH_SHORT).show();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} else {
				Toast.makeText(MainActivity.this, "Not Support Geocoder", Toast.LENGTH_SHORT).show();
			}
		}
	};

	boolean isFirst = true;

	@Override
	protected void onStart() {
		super.onStart();

		if (!mLM.isProviderEnabled(mProvider)) {
			if (isFirst) {
				Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				startActivity(i);
				isFirst = false;
			} else {
				Toast.makeText(this, "Location Provider Not Setting", Toast.LENGTH_SHORT).show();
				finish();
			}
			return;
		}
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
