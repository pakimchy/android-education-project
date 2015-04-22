package com.example.sample7alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
	   Intent i = new Intent(context, AlarmProcessService.class);
	   context.startService(i);
	}

}
