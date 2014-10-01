package com.example.sample5gridview;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class MainActivity extends ActionBarActivity {

	GridView gridView;
//	ArrayAdapter<String> mAdapter;
	MyAdapter mAdapter;
	
	int[] imageIds = { R.drawable.gallery_photo_1, 
			 R.drawable.gallery_photo_2,
			 R.drawable.gallery_photo_3,
			 R.drawable.gallery_photo_4,
			 R.drawable.gallery_photo_5,
			 R.drawable.gallery_photo_6,
			 R.drawable.gallery_photo_7,
			 R.drawable.gallery_photo_8
			 };
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		gridView = (GridView)findViewById(R.id.gridView1);
		
//		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, new ArrayList<String>());
		mAdapter = new MyAdapter(this);
		
		gridView.setAdapter(mAdapter);
		
		initData();
	}
	
	private void initData() {
		for (int i = 0; i < 100; i++) {
//			mAdapter.add("item " + i);
			mAdapter.add(imageIds[i % imageIds.length]);
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
