package com.example.exampleappwidgethost;

import java.util.List;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class AppWidgetListActivity extends Activity {

	AppWidgetManager appWidgetManager;
	ListView listView;
	public static final String PARAM_APP_WIDGET_INFO = "appWidgetInfo";
	
	ArrayAdapter<AppWidgetInfo> mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_app_widget_list);
		appWidgetManager = AppWidgetManager.getInstance(this);
		listView = (ListView)findViewById(R.id.listView1);
		mAdapter = new ArrayAdapter<AppWidgetInfo>(this, android.R.layout.simple_list_item_1);
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				AppWidgetInfo info = (AppWidgetInfo)listView.getItemAtPosition(position);
				Intent data = new Intent();
				data.putExtra(PARAM_APP_WIDGET_INFO, info.info);
				setResult(Activity.RESULT_OK, data);
				finish();
			}
		});
		
		initData();
	}
	
	private void initData() {
		List<AppWidgetProviderInfo> list = appWidgetManager.getInstalledProviders();
		for (AppWidgetProviderInfo info : list) {
			AppWidgetInfo awi = new AppWidgetInfo();
			awi.info = info;
			mAdapter.add(awi);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.app_widget_list, menu);
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
