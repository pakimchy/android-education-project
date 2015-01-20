package com.example.sample6locationmanager;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.LocationManager;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class MyService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		boolean isEnter = intent.getBooleanExtra(
				LocationManager.KEY_PROXIMITY_ENTERING, false);
		Address addr = intent.getParcelableExtra("address");
		String message;
		if (isEnter) {
			message = "Proximity Enter : " + addr.toString();
		} else {
			message = "Proximity Exit : " + addr.toString();
		}
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				this);
		builder.setSmallIcon(R.drawable.ic_launcher);
		builder.setTicker("proximity...");
		builder.setContentTitle("proximity message");
		builder.setContentText(message);
		PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this,
				MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK),
				PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(pi);
		builder.setDefaults(NotificationCompat.DEFAULT_ALL);
		builder.setWhen(System.currentTimeMillis());
		builder.setAutoCancel(true);
		((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE))
				.notify(1, builder.build());
		return Service.START_NOT_STICKY;
	}

}
