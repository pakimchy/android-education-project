package com.example.sample6alarm;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class AlarmService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Toast.makeText(this, "alarm....", Toast.LENGTH_SHORT).show();
		AlarmScheduler.getInstance().processAlarm(this);
		AlarmScheduler.getInstance().registerAlarm(this);
		return Service.START_NOT_STICKY;
	}

}
