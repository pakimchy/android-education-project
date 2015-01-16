package com.example.sample6autologin;

import android.os.Handler;
import android.os.Looper;

public class NetworkManager {
	private static NetworkManager instance;
	public static NetworkManager getInstance() {
		if (instance == null) {
			instance = new NetworkManager();
		}
		return instance;
	}
	
	private NetworkManager() {
		
	}
	
	Handler mHandler = new Handler(Looper.getMainLooper());
	
	public interface OnResultListener {
		public void onSuccess(String message);
	}
	public void login(String id, String password, final OnResultListener listener) {
		mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				listener.onSuccess("success");
			}
		}, 1000);
	}
}
