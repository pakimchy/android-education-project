package com.begentgroup.inappbillingserver;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

public class NetworkManager {
	private static NetworkManager instance;
	public static NetworkManager getInstnace() {
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
		public void onFail(int code);
	}
	
	public void verifyResult(Context context, final Purchase purchase, final OnResultListener<Purchase> listener) {
		mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				listener.onSuccess(purchase);
			}
		}, 1000);
	}
}
