package com.example.sample7applicationcomponent;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {
	private static Context mContext;
	@Override
	public void onCreate() {
		super.onCreate();
		// initialize applicatoin....
		mContext = this;
	}
	public static Context getContext() {
		return mContext;
	}
}
