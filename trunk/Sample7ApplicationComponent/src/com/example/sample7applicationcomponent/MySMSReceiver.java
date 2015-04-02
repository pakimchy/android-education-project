package com.example.sample7applicationcomponent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MySMSReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Log.i("MySMSReceiver","Receive SMS");
		context.startService(new Intent(context,MyService.class));
	}
}
