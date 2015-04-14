package com.example.exampleappwidget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.RemoteViews;

public class MyAppWidgetProvider2 extends AppWidgetProvider {


	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.app_widget_layout);
		Intent intent = new Intent(context, WidgetService.class);
		views.setRemoteAdapter(R.id.list_widget, intent);
		intent.setData(Uri.parse("myscheme://appwidget.example.com/appwidget/"+System.currentTimeMillis()));
		appWidgetManager.updateAppWidget(appWidgetIds, views);
		
	}
	
}
