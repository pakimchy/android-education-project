package com.example.sample7applicationcomponent;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	private static final String TAG = "MyService";
	boolean isRunning = true;
	int mCount = 0;
	
	public static final String ACTION_MOD_ZERO = "com.example.sample7applicationcomponent.action.MOD_ZERO";
	LocalBroadcastManager mLBM;
	public static boolean isReceived = false;
	Handler mHandler = new Handler(Looper.getMainLooper());
	@Override
	public void onCreate() {
		super.onCreate();
		Toast.makeText(this, "onCreate...", Toast.LENGTH_SHORT).show();
		mLBM = LocalBroadcastManager.getInstance(this);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(isRunning) {
					Log.i(TAG, "Count : " + mCount);
					if (mCount % 10 == 0) {
						Intent intent = new Intent(ACTION_MOD_ZERO);
						intent.putExtra("count", mCount);
						isReceived = false;
						mLBM.sendBroadcastSync(intent);
						if(!isReceived) {
							mHandler.post(new Runnable() {
								
								@Override
								public void run() {
									Toast.makeText(MyService.this, "Ativity Not Processing", Toast.LENGTH_SHORT).show();
								}
							});
						}
//						sendBroadcast(intent);
//						sendOrderedBroadcast(intent, null, new BroadcastReceiver() {
//							@Override
//							public void onReceive(Context context, Intent intent) {
//								int code = getResultCode();
//								if (code == Activity.RESULT_CANCELED) {
//									Toast.makeText(MyService.this, "Ativity Not Processing", Toast.LENGTH_SHORT).show();
//								}
//							}
//						}, null, Activity.RESULT_CANCELED, null, null);
					}
					mCount++;
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
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
				Log.i(TAG, "Screen on");
			} else if (intent.getAction().equals(Intent.ACTION_SCREEN_OFF)) {
				Log.i(TAG, "Screen off");
			}
		}
	};
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Toast.makeText(this, "onStartCommand...", Toast.LENGTH_SHORT).show();
		mCount = intent.getIntExtra("count", 0);
		return Service.START_NOT_STICKY;
	}
	
	@Override
	public void onDestroy() {
		Toast.makeText(this, "onDestroy...", Toast.LENGTH_SHORT).show();
		super.onDestroy();
		isRunning = false;
		unregisterReceiver(mReceiver);
	}
}
