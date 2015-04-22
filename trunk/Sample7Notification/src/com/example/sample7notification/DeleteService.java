package com.example.sample7notification;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class DeleteService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Toast.makeText(this, "delete notification", Toast.LENGTH_SHORT).show();
		return Service.START_NOT_STICKY;
	}

}
