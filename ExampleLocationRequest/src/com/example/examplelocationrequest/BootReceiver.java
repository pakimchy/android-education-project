package com.example.examplelocationrequest;

import java.util.List;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;

public class BootReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		List<ProximityData> list = ProximityData.getList();
		for (ProximityData item : list) {
			Intent in = new Intent(context, ProximityService.class);
			Uri data = ContentUris.withAppendedId(ProximityService.PROXIMITY_URI, item.id);
			in.setData(data);
			in.putExtra(ProximityService.EXTRA_ADDRESS, item.address);
			PendingIntent pi = PendingIntent.getService(context, 0, in, PendingIntent.FLAG_UPDATE_CURRENT);
			double latitude = item.address.getLatitude();
			double longitude = item.address.getLongitude();
			float radius = 100;
			long expiration = -1;
			lm.addProximityAlert(latitude, longitude, radius, expiration, pi);
		}
	}

}
