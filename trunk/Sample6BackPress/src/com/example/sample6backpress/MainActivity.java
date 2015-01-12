package com.example.sample6backpress;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	boolean isBackPressed = false;
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case MESSAGE_TIME_OUT_BACK_KEY :
				isBackPressed = false;
				break;
			}
		}
	};
	private static final int MESSAGE_TIME_OUT_BACK_KEY = 0;
	private static final int TIME_BACK_KEY = 2000;
	
	@Override
	public void onBackPressed() {
		if (!isBackPressed) {
			isBackPressed = true;
			Toast.makeText(this, "one more back key", Toast.LENGTH_SHORT).show();
			mHandler.sendEmptyMessageDelayed(MESSAGE_TIME_OUT_BACK_KEY, TIME_BACK_KEY);
		} else {
			mHandler.removeMessages(MESSAGE_TIME_OUT_BACK_KEY);
			super.onBackPressed();
		}
	}
}
