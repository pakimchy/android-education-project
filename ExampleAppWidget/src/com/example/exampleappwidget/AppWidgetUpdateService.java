package com.example.exampleappwidget;

import java.util.Set;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.RemoteViews;

public class AppWidgetUpdateService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
//		Set<Integer> appWidgetIds = PropertyManager.getInstance().getAppWidgetIds();
		ComponentName component = new ComponentName(this, MyAppWidgetProvider.class);
		int[] appWidgetIds = appWidgetManager.getAppWidgetIds(component);
		for (int appWidgetId : appWidgetIds) {
			// keyguard app widget...
			Bundle myOptions = appWidgetManager.getAppWidgetOptions(appWidgetId);
			int category = myOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_HOST_CATEGORY,-1);
			boolean isKeyguard = category == AppWidgetProviderInfo.WIDGET_CATEGORY_KEYGUARD;
						
			int layoutId = isKeyguard?R.layout.lock_widget_layout:R.layout.app_widget_layout;
			RemoteViews views = new RemoteViews(getPackageName(), layoutId);
			views.setTextViewText(R.id.text_message, "count 1");
			views.setImageViewResource(R.id.image_icon, R.drawable.ic_launcher);
			Intent i = new Intent(this, MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			PendingIntent pi = PendingIntent.getActivity(this, 0, i, 0);
			views.setOnClickPendingIntent(R.id.image_icon, pi);
			appWidgetManager.updateAppWidget(appWidgetId, views);
		}		
		
		return Service.START_NOT_STICKY;
	}
}
