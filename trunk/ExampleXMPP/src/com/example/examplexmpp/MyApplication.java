package com.example.examplexmpp;

import org.jivesoftware.smack.SmackAndroid;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

	private static Context mContext;
	
	@Override
	public void onCreate() {
		super.onCreate();
		mContext = this;
		SmackAndroid.init(this);
	}
	
	public static Context getContext() {
		return mContext;
	}
}
