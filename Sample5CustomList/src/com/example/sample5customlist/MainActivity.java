package com.example.sample5customlist;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sample5customlist.MyAdapter.OnAdapterItemClickListener;
import com.example.sample5customlist.data.ItemData;
import com.example.sample5customlist.view.HeaderView;

public class MainActivity extends ActionBarActivity {

	ListView listView;
	MyAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.listView1);
		HeaderView header = new HeaderView(this);
		listView.addHeaderView(header, "header", true);
		HeaderView secondHeader = new HeaderView(this);
		listView.addHeaderView(secondHeader);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Object item = listView.getItemAtPosition(position);
				if (item != null) {
					if (item instanceof ItemData) {
						ItemData data = (ItemData)item;
						Toast.makeText(MainActivity.this, "item : " + data.title, Toast.LENGTH_SHORT).show();
					} else {
						String str = (String) item;
						Toast.makeText(MainActivity.this, "message : " + str, Toast.LENGTH_SHORT).show();
					}
				}

			}
		});
		mAdapter = new MyAdapter(this);
		mAdapter.setOnAdapterItemClickListener(new OnAdapterItemClickListener() {

			@Override
			public void onAdapterItemClick(View v, ItemData data) {
				Toast.makeText(MainActivity.this, "like click : " + data.title,
						Toast.LENGTH_SHORT).show();
			}
		});
		listView.setAdapter(mAdapter);

		initData();
	}

	private void initData() {
		for (int i = 0; i < 5; i++) {
			ItemData d = new ItemData();
			d.imageId = R.drawable.ic_launcher;
			d.title = "title " + i;
			d.desc = "desc " + i;
			mAdapter.add(d);
		}
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
