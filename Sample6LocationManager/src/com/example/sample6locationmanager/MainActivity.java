package com.example.sample6locationmanager;

import java.io.IOException;
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
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	LocationManager mLM;
	
	String mProvider;
	
	EditText keywordView;
	ListView listView;
	ArrayAdapter<Address> mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		keywordView = (EditText)findViewById(R.id.edit_keyword);
		listView = (ListView)findViewById(R.id.listView1);
		mAdapter = new ArrayAdapter<Address>(this, android.R.layout.simple_list_item_1);
		listView.setAdapter(mAdapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Address addr = (Address)listView.getItemAtPosition(position);
				double lat = addr.getLatitude();
				double lng = addr.getLongitude();
				
				int index = DataManager.getInstance().add(addr);
				
				Intent intent = new Intent(MainActivity.this, MyService.class);
				intent.setData(Uri.parse("myscheme://mydomain/"+index));
				intent.putExtra("address", addr);
				PendingIntent pi = PendingIntent.getService(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
				
				long expiration = -1; //System.currentTimeMillis() + 24 * 60 * 60 * 1000;
				mLM.addProximityAlert(lat, lng, 100, expiration, pi);
			}
		});
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Address addr = (Address)listView.getItemAtPosition(position);
				int index = DataManager.getInstance().indexOf(addr);
				Intent intent = new Intent(MainActivity.this, MyService.class);
				intent.setData(Uri.parse("myscheme://mydomain/"+index));
				PendingIntent pi = PendingIntent.getService(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
				mLM.removeProximityAlert(pi);
				return true;
			}
		});
		
		Button btn = (Button)findViewById(R.id.btn_search);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String keyword = keywordView.getText().toString();
				if (Geocoder.isPresent() && keyword != null && !keyword.equals("")) {
					Geocoder coder = new Geocoder(MainActivity.this, Locale.KOREAN);
					try {
						List<Address> list = coder.getFromLocationName(keyword, 10);
						mAdapter.clear();
						for (Address addr : list) {
							mAdapter.add(addr);
						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			}
		});
		
		mLM = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
	}

	LocationListener mListener = new LocationListener() {
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			switch(status) {
			case LocationProvider.OUT_OF_SERVICE :
			case LocationProvider.TEMPORARILY_UNAVAILABLE :
				break;
			case LocationProvider.AVAILABLE :
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
			Toast.makeText(MainActivity.this, "location : " + location.toString(), Toast.LENGTH_SHORT).show();
			if (Geocoder.isPresent()) {
				Geocoder coder = new Geocoder(MainActivity.this, Locale.KOREAN);
				try {
					List<Address> list = coder.getFromLocation(location.getLatitude(), location.getLongitude(), 10);
					mAdapter.clear();
					for (Address addr : list) {
						mAdapter.add(addr);
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			} else {
				Toast.makeText(MainActivity.this, "geocoder not implement", Toast.LENGTH_SHORT).show();
			}
			
		}
	};
	
	boolean isFirst = true;
	
	@Override
	protected void onStart() {
		super.onStart();
		
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		criteria.setCostAllowed(true);
		mProvider = mLM.getBestProvider(criteria, true);
		
		mProvider = LocationManager.GPS_PROVIDER;

		if (mProvider == null
				|| mProvider.equals(LocationManager.PASSIVE_PROVIDER) || !mLM.isProviderEnabled(mProvider)) {
			if (isFirst) {
				Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				startActivity(intent);
				isFirst = false;
			} else {
				Toast.makeText(MainActivity.this, "this app need location setting", Toast.LENGTH_SHORT).show();
				finish();
			}
			return;
			
		}
		
		Location location = mLM.getLastKnownLocation(mProvider);
		if (location != null) {
			mListener.onLocationChanged(location);
		}
//		mLM.requestLocationUpdates(mProvider, 2000, 5.0f, mListener);
		mLM.requestSingleUpdate(mProvider, mListener, null);
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
