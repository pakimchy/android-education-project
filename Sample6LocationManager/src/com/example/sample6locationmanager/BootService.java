package com.example.sample6locationmanager;

import java.util.ArrayList;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.LocationManager;
import android.net.Uri;
import android.os.IBinder;

public class BootService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		ArrayList<Address> list = DataManager.getInstance().getList();
		for (Address addr : list) {
			double lat = addr.getLatitude();
			double lng = addr.getLongitude();
			
			int index = DataManager.getInstance().add(addr);
			
			Intent in = new Intent(this, MyService.class);
			intent.setData(Uri.parse("myscheme://mydomain/"+index));
			intent.putExtra("address", addr);
			PendingIntent pi = PendingIntent.getService(this, 0, in, PendingIntent.FLAG_UPDATE_CURRENT);
			
			long expiration = -1; //System.currentTimeMillis() + 24 * 60 * 60 * 1000;
			lm.addProximityAlert(lat, lng, 100, expiration, pi);
			
		}
		return Service.START_NOT_STICKY;
	}

}
