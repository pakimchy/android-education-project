package com.example.exampleappwidgethost;

import android.app.Activity;
import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends ActionBarActivity {

	AppWidgetHost appWidgetHost;
	AppWidgetManager appWidgetManager;
	private static final int HOST_ID = 8096;
	private static final int REQUEST_CODE_PICK = 0;
	private static final int REQUEST_CODE_COMPLETE = 1;
	private static final int REQUEST_CODE_PROVIDER_INFO = 2;
	private static final int REQUEST_CODE_GRANT_PERMISSION = 3;
	
	LinearLayout container;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		container = (LinearLayout)findViewById(R.id.container);
		appWidgetHost = new AppWidgetHost(this, 8096);
		appWidgetManager = AppWidgetManager.getInstance(this);
		Button btn = (Button)findViewById(R.id.btn_bind_appwidget);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int appWidgetId = appWidgetHost.allocateAppWidgetId();
				Intent pickIntent = new Intent(AppWidgetManager.ACTION_APPWIDGET_PICK);
				pickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
				startActivityForResult(pickIntent, REQUEST_CODE_PICK);
			}
		});
		
		btn = (Button)findViewById(R.id.btn_get_appwidget);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent getProvider = new Intent(MainActivity.this, AppWidgetListActivity.class);
				startActivityForResult(getProvider, REQUEST_CODE_PROVIDER_INFO);
			}
		});
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_PICK && resultCode == Activity.RESULT_OK) {
			int appWidgetId = data.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
			AppWidgetProviderInfo appWidget = appWidgetManager.getAppWidgetInfo(appWidgetId);
			if (appWidget.configure != null) {
				Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_CONFIGURE);
				intent.setComponent(appWidget.configure);
				intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
				startActivityForResult(intent, REQUEST_CODE_COMPLETE);
			} else {
				completedAppWidget(data);
			}
		} else if (requestCode == REQUEST_CODE_COMPLETE && resultCode == Activity.RESULT_OK) {
			completedAppWidget(data);
		} else if (requestCode == REQUEST_CODE_PROVIDER_INFO && resultCode == Activity.RESULT_OK) {
			AppWidgetProviderInfo info = data.getParcelableExtra(AppWidgetListActivity.PARAM_APP_WIDGET_INFO);
			int appWidgetId = appWidgetHost.allocateAppWidgetId();
			if (!appWidgetManager.bindAppWidgetIdIfAllowed(appWidgetId, info.provider)) {
				Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_BIND);
				intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
				intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_PROVIDER, info.provider);
				startActivityForResult(intent, REQUEST_CODE_GRANT_PERMISSION);
				return ;
			} 
			showAppWidget(appWidgetId);
		} else if (requestCode == REQUEST_CODE_GRANT_PERMISSION && resultCode == Activity.RESULT_OK) {
			int appWidgetId = data.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
			ComponentName provider = data.getParcelableExtra(AppWidgetManager.EXTRA_APPWIDGET_PROVIDER);
			showAppWidget(appWidgetId);
		}
	}
	
	private void bindAppWidget(int appWidgetId, ComponentName provider) {
		showAppWidget(appWidgetId);
	}
	
	private void completedAppWidget(Intent data) {
		int appWidgetId = data.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
		showAppWidget(appWidgetId);
	}
	
	private void showAppWidget(int appWidgetId) {
		AppWidgetProviderInfo appWidget = appWidgetManager.getAppWidgetInfo(appWidgetId);
		AppWidgetHostView view = appWidgetHost.createView(this, appWidgetId, appWidget);
		view.setAppWidget(appWidgetId, appWidget);
		container.addView(view);
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
