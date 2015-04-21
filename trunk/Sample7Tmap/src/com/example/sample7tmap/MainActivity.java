package com.example.sample7tmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.skp.Tmap.TMapView;


public class MainActivity extends ActionBarActivity {

	TMapView mapView;
	LocationManager mLM;
	String mProvider = LocationManager.GPS_PROVIDER;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapView = (TMapView)findViewById(R.id.mapview);
        mLM = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        
        new MapInitTask().execute();
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
			if (!isInitiaized) {
				cacheLocation = location;
			} else {
				moveMap(location.getLatitude(),location.getLongitude());
				setMyLocation(location.getLatitude(), location.getLongitude());
			}
			
			mLM.removeUpdates(this);
		}
	};

	private void moveMap(double lat, double lng) {
		mapView.setCenterPoint(lng, lat);
	}
	
	private void setMyLocation(double lat, double lng) {
		mapView.setLocationPoint(lng, lat);
		Bitmap icon = ((BitmapDrawable)getResources().getDrawable(R.drawable.ic_launcher)).getBitmap();
		mapView.setIcon(icon);
		mapView.setIconVisibility(true);
	}
	
	Location cacheLocation = null;
	
	@Override
	protected void onStart() {
		super.onStart();
		Location location = mLM.getLastKnownLocation(mProvider);
		if (location != null) {
			mListener.onLocationChanged(location);
		}
		mLM.requestLocationUpdates(mProvider, 0, 0,  mListener);
	}
    
	
	@Override
	protected void onStop() {
		super.onStop();
		mLM.removeUpdates(mListener);
	}
	
    class MapInitTask extends AsyncTask<Void, Integer, Boolean> {
    	@Override
    	protected Boolean doInBackground(Void... params) {
    		mapView.setSKPMapApiKey("458a10f5-c07e-34b5-b2bd-4a891e024c2a");
    		mapView.setLanguage(TMapView.LANGUAGE_KOREAN);
    		return true;
    	}
    	
    	@Override
    	protected void onPostExecute(Boolean result) {
    		super.onPostExecute(result);
    		setupMap();
    	}
    }
    
    boolean isInitiaized = false;
    private void setupMap() {
    	isInitiaized = true;
    	mapView.setMapType(TMapView.MAPTYPE_STANDARD);
    	mapView.setTrafficInfo(true);
    	if (cacheLocation != null) {
    		moveMap(cacheLocation.getLatitude(), cacheLocation.getLongitude());
    		setMyLocation(cacheLocation.getLatitude(), cacheLocation.getLongitude());
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
