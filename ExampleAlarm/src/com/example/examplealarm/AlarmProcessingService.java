package com.example.examplealarm;

import java.util.List;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class AlarmProcessingService extends Service {

	private static final String TAG = "AlarmProcessingService";
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	AlarmManager mAM;
	public static final Uri ALARM_URI = Uri.parse("myscheme://com.example.examplealarm/alarm");
	
	@Override
	public void onCreate() {
		super.onCreate();
		mAM = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		long current = System.currentTimeMillis();
		List<AlarmData> list = AlarmData.getProcessList(current);
		processAlarmData(list);
		updateAlarmData(list);
		AlarmData.updateList(list);
		long time = AlarmData.getNextAlarmTime();
		if (time > 0) {
			Intent actionIntent = new Intent(this, AlarmProcessingService.class);
			actionIntent.setData(ALARM_URI);
			PendingIntent operation = PendingIntent.getService(this, 0, actionIntent, PendingIntent.FLAG_UPDATE_CURRENT);
			mAM.set(AlarmManager.RTC_WAKEUP, time, operation);
		}
		Toast.makeText(this, "Process Alarm", Toast.LENGTH_SHORT).show();
		return Service.START_NOT_STICKY;
	}
	
	private void processAlarmData(List<AlarmData> list) {
		for (AlarmData d : list) {
			Log.i(TAG, "d.time : " + d.time + ", d.data : " + d.data);
		}
	}
	
	private void updateAlarmData(List<AlarmData> list) {
		for (AlarmData d : list) {
			d.time += 10 * 60 * 1000; // after 10 min
		}
	}
}
