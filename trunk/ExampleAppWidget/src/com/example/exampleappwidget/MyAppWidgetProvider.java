package com.example.exampleappwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

public class MyAppWidgetProvider extends AppWidgetProvider {
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		for (int appWidgetId : appWidgetIds) {
			// keyguard app widget...
			Bundle myOptions = appWidgetManager.getAppWidgetOptions(appWidgetId);
			int category = myOptions.getInt(AppWidgetManager.OPTION_APPWIDGET_HOST_CATEGORY,-1);
			boolean isKeyguard = category == AppWidgetProviderInfo.WIDGET_CATEGORY_KEYGUARD;
						
			int layoutId = isKeyguard?R.layout.lock_widget_layout:R.layout.app_widget_layout;
			RemoteViews views = new RemoteViews(context.getPackageName(), layoutId);
			views.setTextViewText(R.id.text_message, "count 1");
			views.setImageViewResource(R.id.image_icon, R.drawable.ic_launcher);
			Intent intent = new Intent(context, MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
			views.setOnClickPendingIntent(R.id.image_icon, pi);
			appWidgetManager.updateAppWidget(appWidgetId, views);
		}
	}
}
