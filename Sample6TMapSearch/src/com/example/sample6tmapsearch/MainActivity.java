package com.example.sample6tmapsearch;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.sample6tmapsearch.NetworkManager.OnResultListener;

public class MainActivity extends ActionBarActivity {

	ListView listView;
	EditText keywordView;
	ArrayAdapter<POI> mAdapter;
	ArrayAdapter<DongInfo> mDongAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.listView1);
		keywordView = (EditText) findViewById(R.id.edit_keyword);
		mAdapter = new ArrayAdapter<POI>(this,
				android.R.layout.simple_list_item_1);
		mDongAdapter = new ArrayAdapter<DongInfo>(this,
				android.R.layout.simple_list_item_1);
		listView.setAdapter(mAdapter);

		Button btn = (Button) findViewById(R.id.btn_search);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String keyword = keywordView.getText().toString();
				if (keyword != null && !keyword.equals("")) {
					if (!isPoi) {
						NetworkManager.getInstnace().getPoiSearch(
								MainActivity.this, keyword,
								new OnResultListener<SearchPOIResult>() {

									@Override
									public void onSuccess(SearchPOIResult result) {
										listView.setAdapter(mAdapter);
										mAdapter.clear();
										for (POI poi : result.searchPoiInfo.pois.poi) {
											mAdapter.add(poi);
										}
									}

									@Override
									public void onFail(int code) {

									}
								});
						isPoi = true;
					} else {
						NetworkManager.getInstnace().getAreaData(
								MainActivity.this, keyword, 1, 10,
								new OnResultListener<AreaDataResult>() {
									@Override
									public void onSuccess(AreaDataResult result) {
										listView.setAdapter(mDongAdapter);
										mDongAdapter.clear();
										for (DongInfo d : result.findPoiAreaDataByNameInfo.dongInfo) {
											mDongAdapter.add(d);
										}
									}

									@Override
									public void onFail(int code) {

									}
								});
						isPoi = false;
					}
				}
			}
		});
	}

	boolean isPoi = false;

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
