package com.example.sample7appwidget;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.IBinder;
import android.widget.RemoteViews;

public class WidgetUpdateService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	int mCount = 0;
	
	@Override
	public int onStartCommand(Intent i, int flags, int startId) {
		mCount++;
		RemoteViews views = new RemoteViews(getPackageName(), R.layout.app_widget_layout);
		views.setTextViewText(R.id.text_name, "count : " + mCount);
		views.setImageViewResource(R.id.image_icon, R.drawable.sample_0);
		Intent intent = new Intent(this, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent pi = PendingIntent.getActivity(this, 0, intent, 0);
		views.setOnClickPendingIntent(R.id.image_icon, pi);
		
		AppWidgetManager awm = AppWidgetManager.getInstance(this);
		
		ComponentName component = new ComponentName(this, MyAppWidget.class);
		int[] appWidgetIds = awm.getAppWidgetIds(component);
		
		awm.updateAppWidget(appWidgetIds, views);
		
		
		return Service.START_NOT_STICKY;
	}
}
