package com.example.sample6appwidget;

import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.widget.RemoteViews;

public class WidgetUpdateService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	Handler mHandler = new Handler();
	
	boolean isUpdate = false;
	int mCount = 0;
	Runnable updateRunnable = new Runnable() {
		
		@Override
		public void run() {
			if (isUpdate) {
				RemoteViews views = new RemoteViews(getPackageName(), R.layout.app_widget_layout);
				views.setTextViewText(R.id.text_title, "count : " + mCount);
				AppWidgetManager awm = AppWidgetManager.getInstance(WidgetUpdateService.this);
				ComponentName provider = new ComponentName(WidgetUpdateService.this, MyAppWidgetProvider.class);
				awm.updateAppWidget(provider, views);
				mCount++;
				mHandler.postDelayed(this, 1000);
			}
		}
	};
	@Override
	public void onCreate() {
		super.onCreate();
		isUpdate = true;
		mHandler.post(updateRunnable);
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		mHandler.removeCallbacks(updateRunnable);
	}
}
