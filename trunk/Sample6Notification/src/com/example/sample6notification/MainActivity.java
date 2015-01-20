package com.example.sample6notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

public class MainActivity extends ActionBarActivity {

	int count = 0;
	NotificationManager mNM;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mNM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		Button btn = (Button) findViewById(R.id.btn_notify);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				NotificationCompat.Builder builder = new NotificationCompat.Builder(
						MainActivity.this);
				builder.setSmallIcon(R.drawable.ic_launcher);
				builder.setContentTitle("My Notification" + count);
				builder.setContentText("Notification Test...");
				builder.setTicker("notification....");
				builder.setWhen(System.currentTimeMillis());

				Intent intent = new Intent(MainActivity.this,
						NotifyActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("message", "test:" + count);
				count++;
				PendingIntent pi = PendingIntent.getActivity(MainActivity.this,
						0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

				builder.setContentIntent(pi);

				builder.setDefaults(NotificationCompat.DEFAULT_ALL);

				builder.setAutoCancel(true);

				Notification notification = builder.build();

				NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				nm.notify(count, notification);
			}
		});

		btn = (Button) findViewById(R.id.btn_progress);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mHandler.post(progressRunnable);
			}
		});

		btn = (Button) findViewById(R.id.btn_custom);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				NotificationCompat.Builder builder = new NotificationCompat.Builder(
						MainActivity.this);
				builder.setSmallIcon(R.drawable.ic_launcher);
				builder.setTicker("notification....");
				builder.setWhen(System.currentTimeMillis());

				RemoteViews views = new RemoteViews(getPackageName(),
						R.layout.notification_layout);
				views.setTextViewText(R.id.textView2, "notify test....");
				Intent intent = new Intent(MainActivity.this,
						NotifyActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("message", "test:" + count);
				count++;
				intent.putExtra("id", count);
				PendingIntent pi = PendingIntent.getActivity(MainActivity.this,
						0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
				
				views.setOnClickPendingIntent(R.id.textView1, pi);
				
				builder.setContent(views);

				// builder.setContentTitle("My Notification" + count);
				// // builder.setContentText("Notification Test...");
				// builder.setProgress(100, progress, false);
				// progress += 5;
				//
				// Intent intent = new Intent(MainActivity.this,
				// NotifyActivity.class);
				// intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				// intent.putExtra("message", "test:" + count);
				// count++;
				// PendingIntent pi =
				// PendingIntent.getActivity(MainActivity.this,
				// 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
				//
				// builder.setContentIntent(pi);

				builder.setDefaults(NotificationCompat.DEFAULT_ALL);

				builder.setAutoCancel(true);
				// builder.setOngoing(true);

				Notification notification = builder.build();

				mNM.notify(count, notification);
			}
		});
	}

	Handler mHandler = new Handler();
	int progress = 0;
	Runnable progressRunnable = new Runnable() {

		@Override
		public void run() {
			if (progress > 100) {
				mNM.cancel(1);
			} else {
				NotificationCompat.Builder builder = new NotificationCompat.Builder(
						MainActivity.this);
				builder.setSmallIcon(R.drawable.ic_launcher);
				builder.setContentTitle("My Notification" + count);
				// builder.setContentText("Notification Test...");
				builder.setProgress(100, progress, false);
				progress += 5;
				builder.setTicker("notification....");
				builder.setWhen(System.currentTimeMillis());

				Intent intent = new Intent(MainActivity.this,
						NotifyActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.putExtra("message", "test:" + count);
				count++;
				PendingIntent pi = PendingIntent.getActivity(MainActivity.this,
						0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

				builder.setContentIntent(pi);

				builder.setDefaults(NotificationCompat.DEFAULT_ALL);

				builder.setAutoCancel(true);
				builder.setOngoing(true);

				Notification notification = builder.build();

				mNM.notify(1, notification);

				mHandler.postDelayed(this, 1000);
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
