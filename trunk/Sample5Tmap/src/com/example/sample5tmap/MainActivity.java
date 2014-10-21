package com.example.sample5tmap;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.skp.Tmap.TMapView;
import com.skp.Tmap.TMapView.OnApiKeyListenerCallback;

public class MainActivity extends ActionBarActivity {

	TMapView mMapView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
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
		
	}
	
	private void setupMap() {
		mMapView.setLanguage(mMapView.LANGUAGE_KOREAN);
		mMapView.setMapType(mMapView.MAPTYPE_STANDARD);
		mMapView.setTrafficInfo(true);
		mMapView.setCompassMode(true);
		mMapView.setTrackingMode(true);
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
