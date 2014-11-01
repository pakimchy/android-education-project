package com.example.sampleaidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class MyService extends Service {

	int counter;
	
	@Override
	public IBinder onBind(Intent intent) {
		return binder;
	}

	IMyService.Stub binder = new IMyService.Stub() {
		
		@Override
		public void setCount(int count) throws RemoteException {
			getPackageManager().checkPermission(permName, pkgName)
			counter = count;
		}
		
		@Override
		public int getCount() throws RemoteException {
			return counter;
		}
	};
	
	boolean isRunning = true;
	
	@Override
	public void onCreate() {
		super.onCreate();
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(isRunning) {
					counter++;
					Log.i("MyService", " count : " + counter);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		isRunning = false;
	}
}
