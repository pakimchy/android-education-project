package com.example.sample5applicationcomponent;

import android.app.Activity;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {

	public static final String PARAM_COUNT = "count";
	public static final String ACTION_COUNT = "com.example.sample5applicationcomponent.action.COUNT";
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	boolean isRunning = false;
	int mCount = 0;
	
	@Override
	public void onCreate() {
		super.onCreate();
		Toast.makeText(this, "onCreate", Toast.LENGTH_SHORT).show();
		isRunning = true;
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(isRunning) {
					if (mCount % 10 == 0) {
						Intent event = new Intent(ACTION_COUNT);
						event.putExtra(PARAM_COUNT, mCount);
//						sendBroadcast(event);
						sendOrderedBroadcast(event, null, new BroadcastReceiver(){
							
							public void onReceive(Context context, Intent intent) {
								int result = getResultCode();
								if (result == Activity.RESULT_CANCELED) {
									Toast.makeText(MyService.this, "event not process", Toast.LENGTH_SHORT).show();
								} else {
									Toast.makeText(MyService.this, "event process", Toast.LENGTH_SHORT).show();
								}
							}
						}, null, Activity.RESULT_CANCELED, null, null);
					}
					Log.i("MyService", "count : " + mCount);
					mCount++;
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
		registerReceiver(receiver, filter);
	}
	
	
	BroadcastReceiver receiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
				Log.i("MyService", "Screen off");
			} else if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)) {
				Log.i("MyService", "Screen on");
				Toast.makeText(context, "Screen ON", Toast.LENGTH_SHORT).show();
			}
		}
	};
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Toast.makeText(this, "onStartCommand", Toast.LENGTH_SHORT).show();
		int count = intent.getIntExtra(PARAM_COUNT, 0);
		mCount = count;
		return Service.START_STICKY;
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
		isRunning = false;
		
		unregisterReceiver(receiver);
	}
}
