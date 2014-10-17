package com.example.sample5notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {

	NotificationManager mNM;
	int count = 0;
	int id = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mNM = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

		Button btn = (Button)findViewById(R.id.btn_send_notification);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this);
				builder.setSmallIcon(R.drawable.ic_launcher);
				builder.setTicker("my notification ticker...");
				
				builder.setContentTitle("title");
				builder.setContentText("content ...." + count);
				builder.setWhen(System.currentTimeMillis());
				
				Intent i = new Intent(MainActivity.this, MainActivity.class);
				i.putExtra("param1", count);
				i.putExtra("type", 1);
				count++;
				PendingIntent pi = PendingIntent.getActivity(MainActivity.this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
				
				builder.setContentIntent(pi);
				
				builder.setDefaults(Notification.DEFAULT_ALL);
				
				builder.setAutoCancel(true);
				
				mNM.notify(id++, builder.build());
			}
		});
		
		Intent i = getIntent();
		int type = i.getIntExtra("type", 0);
		if (type == 1) {
			Intent intent = new Intent(MainActivity.this, NotifyActivity.class);
			intent.putExtra("param1", i.getIntExtra("param1",-1));
			startActivity(intent);
		}
		
		btn = (Button)findViewById(R.id.btn_progress);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				progress = 0;
				mHandler.post(updateRunnable);
			}
		});
	}
	
	Handler mHandler = new Handler();
	
	int progress = 0;
	Runnable updateRunnable = new Runnable() {
		
		@Override
		public void run() {
			if (progress <= 100) {
				NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this);
				builder.setSmallIcon(R.drawable.ic_launcher);
				builder.setTicker("progress : " + progress);
				builder.setContentTitle("download...");
				builder.setProgress(100, progress, false);
				builder.setOngoing(true);
				mNM.notify(1, builder.build());
				mHandler.postDelayed(this, 1000);
				progress+=10;
			} else {
				mNM.cancel(1);
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
