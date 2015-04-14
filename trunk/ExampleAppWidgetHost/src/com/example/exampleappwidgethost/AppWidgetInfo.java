package com.example.exampleappwidgethost;

import android.appwidget.AppWidgetProviderInfo;

public class AppWidgetInfo {
	public AppWidgetProviderInfo info;
	@Override
	public String toString() {
		return info.provider.getPackageName();
	}
}
