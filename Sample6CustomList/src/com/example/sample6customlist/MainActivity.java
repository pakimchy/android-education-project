package com.example.sample6customlist;

import java.util.List;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sample6customlist.MyAdapter.OnAdapterItemClickListener;

public class MainActivity extends ActionBarActivity {

	ListView listView;
	MyAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.listView1);
		TextView headerView = new TextView(this);
		headerView.setText("Header View");
		listView.addHeaderView(headerView, "header", true);
		TextView footerView = new TextView(this);
		footerView.setText("Footer View");
		listView.addFooterView(footerView);

		mAdapter = new MyAdapter(this);
		mAdapter.setOnAdapterItemClickListener(new OnAdapterItemClickListener() {

			@Override
			public void onAdapterItemClick(MyAdapter adapter, View view,
					ItemData item) {
				Toast.makeText(MainActivity.this, "like click : " + item.title,
						Toast.LENGTH_SHORT).show();
			}
		});
		listView.setAdapter(mAdapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Object data = listView.getItemAtPosition(position);
				if (data instanceof String) {
					String text = (String) data;
					Toast.makeText(MainActivity.this, "header click : " + text,
							Toast.LENGTH_SHORT).show();
				} else {
					ItemData item = (ItemData) data;
					Toast.makeText(MainActivity.this,
							"item click : " + item.title, Toast.LENGTH_SHORT)
							.show();
				}
			}
		});

		initData();
	}

	private void initData() {

		List<ItemData> items = DataManager.getInstance().getItemDataList();
		for (ItemData id : items) {
			mAdapter.add(id);
		}
		// for (int i = 0; i < 10; i++) {
		// ItemData id = new ItemData();
		// id.iconId = R.drawable.ic_launcher;
		// id.title = "title" + i;
		// id.desc = "desc" + i;
		// id.like = i;
		// mAdapter.add(id);
		// }
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
