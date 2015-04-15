package com.example.sample7autologin;

import android.content.Context;
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
	
	Handler mHandler = new Handler(Looper.getMainLooper());
	private NetworkManager() {
		
	}
	
	public interface OnResultListener<T> {
		public void onSuccess(T result);
		public void onError(int code);
	}
	
	public void login(Context context, String userid, String password, final OnResultListener<String> listener) {
		mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				listener.onSuccess("success");
			}
		}, 1000);
	}
	
	public void signup(Context cotnext, String userid, String password, final OnResultListener<String> listener) {
		mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				listener.onSuccess("success");
			}
		}, 1000);
	}
	
}
