package com.example.sample7locationmanager;

import java.util.List;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.LocationManager;
import android.net.Uri;

public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
	    LocationManager lm = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
	    List<Address> addresses = DataManager.list();
	    for (int i = 0 ; i < addresses.size(); i++) {
	    	Address addr = addresses.get(i);
	    	Intent ii = new Intent(context, ProximityService.class);
	    	ii.setData(Uri.parse("myscheme://com.example.sample7locationmanager/"+i));
	    	ii.putExtra("address", addr);
	    	PendingIntent pi = PendingIntent.getService(context, 0, ii, PendingIntent.FLAG_UPDATE_CURRENT);
	    	lm.addProximityAlert(addr.getLatitude(), addr.getLongitude(), 100, -1, pi);
	    }
	}

}
