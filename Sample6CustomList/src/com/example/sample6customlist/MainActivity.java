package com.example.sample6customlist;

import java.util.List;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {

	ListView listView;
	MyAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView)findViewById(R.id.listView1);
		mAdapter = new MyAdapter(this);
		listView.setAdapter(mAdapter);
		
		initData();
	}
	
	private void initData() {
		
		List<ItemData> items = DataManager.getInstance().getItemDataList();
		for (ItemData id : items) {
			mAdapter.add(id);
		}
//		for (int i = 0; i < 10; i++) {
//			ItemData id = new ItemData();
//			id.iconId = R.drawable.ic_launcher;
//			id.title = "title" + i;
//			id.desc = "desc" + i;
//			id.like = i;
//			mAdapter.add(id);
//		}
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
