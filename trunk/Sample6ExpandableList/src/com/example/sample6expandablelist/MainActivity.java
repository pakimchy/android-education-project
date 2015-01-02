package com.example.sample6expandablelist;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	ExpandableListView listView;
	MyAdapter mAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ExpandableListView)findViewById(R.id.expandableListView1);
		mAdapter = new MyAdapter(this);
		listView.setAdapter(mAdapter);
		listView.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				ChildItem child = (ChildItem)mAdapter.getChild(groupPosition, childPosition);
				Toast.makeText(MainActivity.this, "child : " + child.title, Toast.LENGTH_SHORT).show();
				return true;
			}
		});
		listView.setOnGroupCollapseListener(new OnGroupCollapseListener() {
			
			@Override
			public void onGroupCollapse(int groupPosition) {
				Toast.makeText(MainActivity.this, "Group collapse", Toast.LENGTH_SHORT).show();
				listView.expandGroup(groupPosition);
			}
		});
		
		listView.setOnGroupExpandListener(new OnGroupExpandListener() {
			
			@Override
			public void onGroupExpand(int groupPosition) {
				Toast.makeText(MainActivity.this, "Group expand", Toast.LENGTH_SHORT).show();
			}
		});
		
		initData();
		
		for (int i = 0; i < mAdapter.getGroupCount(); i++) {
			listView.expandGroup(i);
		}
		
	}
	
	private void initData() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 5; j++) {
				mAdapter.put("group"+i, "child"+i+":"+j);
			}
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
