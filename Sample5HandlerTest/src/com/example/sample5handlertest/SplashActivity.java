package com.example.sample5handlertest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class SplashActivity extends Activity {

	public static final int MESSAGE_MOVE_ACTIVITY = 0;
	public static final int TIMEOUT_ACTIVITY = 2000;
	
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case MESSAGE_MOVE_ACTIVITY :
				Intent i = new Intent(SplashActivity.this, MainActivity.class);
				startActivity(i);
				finish();
				break;
			}
		}
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.splash_layout);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		mHandler.sendMessageDelayed(mHandler.obtainMessage(MESSAGE_MOVE_ACTIVITY), TIMEOUT_ACTIVITY);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		mHandler.removeMessages(MESSAGE_MOVE_ACTIVITY);
	}

}
