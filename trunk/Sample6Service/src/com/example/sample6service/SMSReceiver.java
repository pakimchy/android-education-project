package com.example.sample6service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SMSReceiver extends BroadcastReceiver {

	private static final String TAG = "SMSReceiver";
	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i(TAG, "SMS Received");
		Intent i = new Intent(context, MyService.class);
		context.startService(i);
	}

}
