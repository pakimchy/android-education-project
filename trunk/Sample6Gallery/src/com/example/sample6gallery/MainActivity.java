package com.example.sample6gallery;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Gallery;

public class MainActivity extends ActionBarActivity {

	Gallery gallery;
	MyAdapter mAdapter;
	int[] imageResIds = { 
			R.drawable.gallery_photo_1, R.drawable.gallery_photo_2 , 
			R.drawable.gallery_photo_3, R.drawable.gallery_photo_4 ,
			R.drawable.gallery_photo_5, R.drawable.gallery_photo_6 ,
			R.drawable.gallery_photo_7, R.drawable.gallery_photo_8 
	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		gallery = (Gallery)findViewById(R.id.gallery1);
		mAdapter = new MyAdapter(this);
		gallery.setAdapter(mAdapter);
		
		initData();
		
		int position = Integer.MAX_VALUE / 2;
		
		position = (position / imageResIds.length) * imageResIds.length;
		
		gallery.setSelection(position);
	}
	
	private void initData() {
		for (int i = 0; i < imageResIds.length; i++) {
			mAdapter.add(imageResIds[i]);
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
