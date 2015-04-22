package com.example.sample7alarm;

import java.util.List;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class AlarmProcessService extends Service {

	AlarmManager mAM;
	
	@Override
	public void onCreate() {
		super.onCreate();
		mAM = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
	}
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		List<AlarmData> list = DBManager.processData();
		for (AlarmData d : list) {
			Toast.makeText(this, "alarm : " + d.message, Toast.LENGTH_SHORT).show();
			d.time = d.time + 30 * 1000;
			DBManager.setAlarmData(d);
		}

		AlarmData near = DBManager.nearData();
		
		Intent i = new Intent(this, AlarmProcessService.class);
		PendingIntent pi = PendingIntent.getService(this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
		mAM.set(AlarmManager.RTC, near.time, pi);
		
		return Service.START_NOT_STICKY;
	}
}
