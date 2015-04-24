package com.example.sample7appwidget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

public class MyAppWidget extends AppWidgetProvider {

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget_layout);
		views.setTextViewText(R.id.text_name, "my app widget");
		views.setImageViewResource(R.id.image_icon, R.drawable.sample_0);
		Intent intent = new Intent(context, MainActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
		views.setOnClickPendingIntent(R.id.image_icon, pi);
		
		appWidgetManager.updateAppWidget(appWidgetIds, views);
	}
	
}
