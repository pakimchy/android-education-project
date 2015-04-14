package com.example.examplenotification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

public class MainActivity extends ActionBarActivity {

	NotificationManager mNM;
	private int mId = 1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button) findViewById(R.id.btn_send_notification);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				sendNotification(null);
			}
		});

		btn = (Button) findViewById(R.id.btn_big_text_notification);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				NotificationCompat.BigTextStyle style = new NotificationCompat.BigTextStyle();
				style.bigText("Helper class for generating large-format notifications that include a lot of text."
						+ "If the platform does not provide large-format notifications, this method has no effect. The user will always see the normal notification view."
						+ "This class is a \"rebuilder\": It attaches to a Builder object and modifies its behavior, like so:");
				sendNotification(style);
			}
		});
		
		btn = (Button)findViewById(R.id.btn_inbox_notification);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				NotificationCompat.InboxStyle style = new NotificationCompat.InboxStyle();
				style.addLine("Content Line 1");
				style.addLine("Content Line 2");
				style.addLine("Content Line 3");
				style.addLine("Content Line 4");
				style.addLine("Content Line 5");
				style.addLine("Content Line 6");
				style.setBigContentTitle("Big Content Title");
				style.setSummaryText("Summary Text");
				sendNotification(style);
			}
		});
		
		btn = (Button)findViewById(R.id.btn_big_picture_notification);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				NotificationCompat.BigPictureStyle style = new NotificationCompat.BigPictureStyle();
				Bitmap b = ((BitmapDrawable)(getResources().getDrawable(R.drawable.sample_0))).getBitmap();
				style.bigPicture(b);
				sendNotification(style);
			}
		});
				
		btn = (Button)findViewById(R.id.btn_progress);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mHandler.removeCallbacks(progressRunnable);
				mProgress = 0;
				mHandler.post(progressRunnable);
			}
		});
		
		btn = (Button)findViewById(R.id.btn_add_action);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sendNotificationWithAction();
			}
		});
		
		btn = (Button)findViewById(R.id.btn_custom);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				sendNotificationCustom();
			}
		});

		btn = (Button) findViewById(R.id.btn_clear_notification);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				clearNotification();
			}
		});
		mNM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
	}

	Handler mHandler = new Handler(Looper.getMainLooper());
	int mProgress;
	Runnable progressRunnable = new Runnable() {
		
		@Override
		public void run() {
			if (mProgress <= 100) {
				sendNotificationProgress(mProgress);
				mProgress += 5;
				mHandler.postDelayed(this, 500);
			} else {
				clearNotification();
			}
		}
	};
	private void sendNotificationProgress(int progress) {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				this);
		builder.setSmallIcon(R.drawable.star_big_on);
		builder.setContentTitle("Download Progress");
		builder.setContentText("file download...");
		builder.setContentInfo("info");
		builder.setProgress(100, progress, false);
		builder.setOngoing(true);
		builder.setOnlyAlertOnce(true);
		builder.setTicker("Ticker Text...");
		builder.setWhen(System.currentTimeMillis());
		builder.setDefaults(NotificationCompat.DEFAULT_ALL);
		builder.setAutoCancel(true);
		Intent intent = new Intent(this, NotifyActivity.class);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		stackBuilder.addParentStack(NotifyActivity.class);
		stackBuilder.addNextIntent(intent);
		PendingIntent pi = stackBuilder.getPendingIntent(0,
				PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(pi);

		mNM.notify(mId, builder.build());
	}

	private void sendNotificationCustom() {
		NotificationCompat.Builder builder = new NotificationCompat.Builder( this);
		builder.setSmallIcon(R.drawable.star_big_on);
		RemoteViews tickerView = new RemoteViews(getPackageName(), R.layout.notification_ticker);
		builder.setTicker("Ticker Text...", tickerView);
		RemoteViews views = new RemoteViews(getPackageName(), R.layout.notification_expend);
		views.setTextViewText(R.id.text_title, "Custom Notification");
		PendingIntent btnPi = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
		views.setOnClickPendingIntent(R.id.btn_go, btnPi);
		views.setImageViewResource(R.id.image_icon, R.drawable.sample_0);
		builder.setContent(views);
		builder.setWhen(System.currentTimeMillis());
		builder.setDefaults(NotificationCompat.DEFAULT_ALL);
		builder.setAutoCancel(true);
		Intent intent = new Intent(this, NotifyActivity.class);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		stackBuilder.addParentStack(NotifyActivity.class);
		stackBuilder.addNextIntent(intent);
		PendingIntent pi = stackBuilder.getPendingIntent(0,
				PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(pi);
		mNM.notify(mId, builder.build());
	}
	
	private void sendNotificationWithAction() {
		NotificationCompat.Builder builder = new NotificationCompat.Builder( this);
		builder.setSmallIcon(R.drawable.star_big_on);
		builder.setContentTitle("Content Title");
		builder.setContentText("Content Text...");
		builder.setContentInfo("info");
		builder.setSubText("Sub Text");
		builder.setTicker("Ticker Text...");
		builder.setWhen(System.currentTimeMillis());
		builder.setDefaults(NotificationCompat.DEFAULT_ALL);
		builder.setAutoCancel(true);
		Intent intent = new Intent(this, NotifyActivity.class);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		stackBuilder.addParentStack(NotifyActivity.class);
		stackBuilder.addNextIntent(intent);
		PendingIntent pi = stackBuilder.getPendingIntent(0,
				PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(pi);
		PendingIntent prevPi = PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
		PendingIntent nextPi = PendingIntent.getActivity(this, 0, new Intent(this, NotifyActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
		builder.addAction(R.drawable.ic_launcher, "Prev", prevPi);
		builder.addAction(R.drawable.star_big_on, "Next", nextPi);
		mNM.notify(mId, builder.build());
	}

	private void sendNotification(NotificationCompat.Style style) {
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				this);
		builder.setSmallIcon(R.drawable.star_big_on);
		builder.setContentTitle("Content Title");
		builder.setContentText("Content Text...");
		builder.setContentInfo("info");
		builder.setSubText("Sub Text");
		builder.setTicker("Ticker Text...");
		builder.setWhen(System.currentTimeMillis());
		builder.setDefaults(NotificationCompat.DEFAULT_ALL);
		builder.setAutoCancel(true);
		if (style != null) {
			builder.setStyle(style);
		}
		Intent intent = new Intent(this, NotifyActivity.class);
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
		stackBuilder.addParentStack(NotifyActivity.class);
		stackBuilder.addNextIntent(intent);
		PendingIntent pi = stackBuilder.getPendingIntent(0,
				PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(pi);

		mNM.notify(mId, builder.build());
	}

	private void clearNotification() {
		mNM.cancel(mId);
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
