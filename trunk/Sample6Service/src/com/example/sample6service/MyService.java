package com.example.sample6service;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.PendingIntent.CanceledException;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {

	private static final String TAG = "MyService";
	
	public static final String ACTION_DIV_TEN = "com.example.sample6service.action.DIV_TEN";
	public static final String PERMISSION_DIV_TEN = "com.example.sample6service.permission.DIV_COUNT";
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	int mCount = 0;
	boolean isRunning = false;
	@Override
	public void onCreate() {
		super.onCreate();
		Toast.makeText(this, "onCreate...", Toast.LENGTH_SHORT).show();
		isRunning = true;
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(isRunning) {
					mCount++;
					Log.i(TAG, "count : " + mCount);
					if (mCount % 10 == 0) {
						Intent i = new Intent(ACTION_DIV_TEN);
						i.putExtra("count", mCount);
						sendOrderedBroadcast(i, PERMISSION_DIV_TEN, new BroadcastReceiver() {
							@Override
							public void onReceive(Context context, Intent intent) {
								int code = getResultCode();
								if (code == Activity.RESULT_CANCELED) {
									Toast.makeText(MyService.this, "not received", Toast.LENGTH_SHORT).show();
								}
							}
						}, null, Activity.RESULT_CANCELED, null, null);
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
		
		IntentFilter filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
		filter.addAction(Intent.ACTION_SCREEN_OFF);

		registerReceiver(mReceiver, filter);
	}
	
	BroadcastReceiver mReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
				Toast.makeText(context, "Screen ON", Toast.LENGTH_SHORT).show();
			} else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
				Log.i(TAG, "Screen Off");
			}
		}
	};
	
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Toast.makeText(this, "onStartCommand...", Toast.LENGTH_SHORT).show();
		if (intent != null) {
			int count = intent.getIntExtra("count", 0);
			PendingIntent pi = intent.getParcelableExtra("result");
			Intent data = new Intent();
			data.putExtra("message", "count : " + mCount);
			mCount = count;
			try {
				pi.send(this, Activity.RESULT_OK, data);
			} catch (CanceledException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return Service.START_NOT_STICKY;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Toast.makeText(this, "onDestroy...", Toast.LENGTH_SHORT).show();
		isRunning = false;
		unregisterReceiver(mReceiver);
	}

}
