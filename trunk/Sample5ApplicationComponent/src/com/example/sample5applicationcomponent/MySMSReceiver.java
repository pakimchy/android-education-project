package com.example.sample5applicationcomponent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MySMSReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
//		Toast.makeText(context, "SMS Received", Toast.LENGTH_SHORT).show();
		context.startService(new Intent(context, MyService.class));
	}

}
