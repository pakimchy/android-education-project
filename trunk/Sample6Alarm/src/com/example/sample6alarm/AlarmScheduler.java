package com.example.sample6alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class AlarmScheduler {
	private static AlarmScheduler instance;
	public static AlarmScheduler getInstance() {
		if (instance == null) {
			instance = new AlarmScheduler();
		}
		return instance;
	}
	
	private AlarmScheduler() {
		
	}
	
	AlarmManager mAM;
	
	public void registerAlarm(Context context) {
		if (mAM == null) {
			mAM = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		}
		
		long nextTime = getNextTime();
		Intent intent = new Intent(context, AlarmService.class);
		intent.setData(Uri.parse("myscheme://packagename/alarm1"));
		PendingIntent pi = PendingIntent.getService(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		mAM.set(AlarmManager.RTC_WAKEUP, nextTime, pi);		
	}
	
	public void processAlarm(Context context) {
		long time = System.currentTimeMillis();
		// time....
	}
	
	public void addSchedule() {
		
	}
	private long getNextTime() {
		return System.currentTimeMillis() + 5000;
	}
}
