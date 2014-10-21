package com.example.sample5location;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.LocationManager;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.widget.Toast;

public class AlertService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		boolean isEnter = intent.getBooleanExtra(LocationManager.KEY_PROXIMITY_ENTERING, false);
		Address addr = intent.getParcelableExtra("address");
		if (isEnter) {
			sendNotification("enter : " + addr.toString());
//			Toast.makeText(this, "enter : " + addr.toString(), Toast.LENGTH_SHORT).show();
		} else {
			sendNotification("exit : " + addr.toString());
//			Toast.makeText(this, "exit : " + addr.toString(), Toast.LENGTH_SHORT).show();
		}
		return Service.START_NOT_STICKY;
	}
	
	private void sendNotification(String message) {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
		builder.setSmallIcon(R.drawable.ic_launcher);
		builder.setTicker("proximity alert");
		builder.setContentTitle("proximity alert");
		builder.setContentText(message);
		builder.setAutoCancel(true);
		builder.setDefaults(Notification.DEFAULT_ALL);
		NotificationManager nm = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		nm.notify(0, builder.build());
	}
}
