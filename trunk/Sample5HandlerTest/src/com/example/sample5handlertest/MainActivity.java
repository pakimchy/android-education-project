package com.example.sample5handlertest;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	public static final int MESSAGE_BACK_PRESSED_TIME_OUT = 0;
	public static final int TIMEOUT_BACK_PRESSED = 2000;
	boolean isBackPressed = false;

	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MESSAGE_BACK_PRESSED_TIME_OUT:
				isBackPressed = false;
				break;
			}
		}
	};

	@Override
	public void onBackPressed() {
		if (!isBackPressed) {
			isBackPressed = true;
			Toast.makeText(this, "one more back press", Toast.LENGTH_SHORT)
					.show();
			mHandler.sendMessageDelayed(
					mHandler.obtainMessage(MESSAGE_BACK_PRESSED_TIME_OUT),
					TIMEOUT_BACK_PRESSED);
		} else {
			mHandler.removeMessages(MESSAGE_BACK_PRESSED_TIME_OUT);
			super.onBackPressed();
		}
	}

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
}
