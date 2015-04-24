package com.example.sample7facebook;

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
	
	private NetworkManager() {
		
	}
	
	public interface OnResultListener<T> {
		public void onSuccess(T result);
		public void onError(int code);
	}
	
	Handler mHandler = new Handler(Looper.getMainLooper());
	
	public void loginFacebook(Context context, String accessToken, final OnResultListener<String> listener) {
		mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				listener.onSuccess("success");
			}
		}, 1000);
	}
	
	public void signupFacebook(Context context, final OnResultListener<String> listener) {
		mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				listener.onSuccess("success");
			}
		}, 1000);
	}
}
