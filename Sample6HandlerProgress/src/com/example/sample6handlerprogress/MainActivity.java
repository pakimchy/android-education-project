package com.example.sample6handlerprogress;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	TextView progressText;
	Handler mHandler = new Handler();
	public static final int INTERVAL = 1000;
	long startTime = -1;
	
	int count;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		progressText = (TextView)findViewById(R.id.text_progress);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				count = 0;
				startTime = -1;
				mHandler.removeCallbacks(updateRunnable);
				mHandler.post(updateRunnable);
			}
		});
	}
	
	Runnable updateRunnable = new Runnable() {
		
		@Override
		public void run() {
			long currentTime = SystemClock.uptimeMillis();
			if (startTime == -1) {
				startTime = currentTime;
			}
			int delta = (int)(currentTime - startTime);
			count = delta / INTERVAL;
			int rest = INTERVAL - (delta % INTERVAL);
			if (count < 20) {
				progressText.setText("progress : " + count);
//				count++;
				mHandler.postDelayed(this, rest);
			} else {
				progressText.setText("progress done");
			}
		}
	};

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
