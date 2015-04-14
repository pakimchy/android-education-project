package com.example.examplelocationrequest;

import android.app.Service;
import android.content.ContentUris;
import android.content.Intent;
import android.location.Address;
import android.location.LocationManager;
import android.net.Uri;
import android.os.IBinder;
import android.widget.Toast;

public class ProximityService extends Service {

	private static final String MY_SCHEME = "myscheme";
	private static final String URI_HOST = "com.example.examplelocationrequest/proximity";
	private static final String URI_STRING = MY_SCHEME + "://" + URI_HOST;
	
	public static final String EXTRA_ADDRESS = "address";
	public static final Uri PROXIMITY_URI = Uri.parse(URI_STRING);
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		StringBuilder sb = new StringBuilder();
		boolean isEnter = intent.getBooleanExtra(LocationManager.KEY_PROXIMITY_ENTERING, false);
		if (isEnter) {
			sb.append("Proximity Enter").append("\n\r");
		} else {
			sb.append("Proximity Exit").append("\n\r");
		}
		Uri data = intent.getData();
		long id = ContentUris.parseId(data);
		sb.append("id : " + id).append("\n\r");
		Address address = intent.getParcelableExtra(EXTRA_ADDRESS);
		sb.append(address.toString());
		
		Toast.makeText(this, sb.toString() , Toast.LENGTH_SHORT).show();
		return Service.START_NOT_STICKY;
	}

}
