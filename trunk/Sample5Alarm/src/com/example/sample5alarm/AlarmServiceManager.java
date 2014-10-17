package com.example.sample5alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmServiceManager {
	private static AlarmServiceManager instance;
	public static AlarmServiceManager getInstance() {
		if (instance == null) {
			instance = new AlarmServiceManager();
		}
		return instance;
	}
	
	private AlarmServiceManager() {
		
	}

	int count = 3;
	
	public void processAlarm(Context context) {
		processEvent(context);
		long time = getNearTime();
		if (time != -1) {
			AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
			PendingIntent pi = PendingIntent.getService(context, 0, new Intent(
					context, MyService.class), 0);
			am.set(AlarmManager.RTC, time, pi);
		}		
	}
	
	private void processEvent(Context context) {
		long time = System.currentTimeMillis();
		Toast.makeText(context, "process service...", Toast.LENGTH_SHORT).show();
		// time ... process...
	}
	
	
	private long getNearTime() {
		if (count > 0) {
			count--;
			return System.currentTimeMillis() + 5000;
		} else {
			return -1;
		}
	}

	
}
