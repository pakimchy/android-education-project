package com.example.sample5appwidget;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.RemoteViews;

public class MyService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	Handler mHandler = new Handler();
	
	Runnable updateRunnable = new Runnable() {
		
		@Override
		public void run() {
			updateWidget();
			mHandler.postDelayed(this, 10000);
		}
	};
	
	int count = 0;
	AppWidgetManager mAppWidgetManager;
	
	private void updateWidget() {
		RemoteViews views = new RemoteViews(getPackageName(), R.layout.app_widget_layout);
		views.setImageViewResource(R.id.icon, R.drawable.gallery_photo_1);
		views.setTextViewText(R.id.message, "count : " + count);
		count++;
		Intent intent = new Intent(this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		views.setOnClickPendingIntent(R.id.message, pi);
		
		ComponentName provider = new ComponentName(this, MyAppWidgetProvider.class);
		mAppWidgetManager.updateAppWidget(provider, views);
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		mHandler.post(updateRunnable);
		mAppWidgetManager = AppWidgetManager.getInstance(this);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		mHandler.removeCallbacks(updateRunnable);
	}
}
