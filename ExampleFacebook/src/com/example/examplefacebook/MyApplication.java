package com.example.examplefacebook;

import android.app.Application;
import android.content.Context;

import com.facebook.FacebookSdk;

public class MyApplication extends Application {
	private static Context mContext;
	
	@Override
	public void onCreate() {
		super.onCreate();
		mContext = this;
		FacebookSdk.sdkInitialize(this);		
	}
	
	public static Context getContext() {
		return mContext;
	}
}
