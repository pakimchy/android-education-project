package com.example.exampleimagemultiselect;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {

	ListView listView;
	ImageItemAdapter mAdapter;

	private static final int REQUEST_IMAGE_CHOICE = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.listView1);
		mAdapter = new ImageItemAdapter();
		listView.setAdapter(mAdapter);
		Button btn = (Button) findViewById(R.id.btn_get_image);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(MainActivity.this,
						ImageChoiceActivity.class), REQUEST_IMAGE_CHOICE);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_IMAGE_CHOICE && resultCode == Activity.RESULT_OK) {
			ArrayList<ImageItem> list = data.getParcelableArrayListExtra(ImageChoiceActivity.RESULT_LIST);
			mAdapter.clear();
			for (ImageItem item : list) {
				mAdapter.add(item);
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
