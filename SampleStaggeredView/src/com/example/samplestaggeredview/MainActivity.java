package com.example.samplestaggeredview;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.etsy.android.grid.StaggeredGridView;
import com.example.samplestaggeredview.NetworkManager.OnResultListener;

public class MainActivity extends ActionBarActivity {

	StaggeredGridView gridView;
	MyAdapter mAdapter;
	EditText keywordView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		gridView = (StaggeredGridView)findViewById(R.id.gridView1);
		mAdapter = new MyAdapter(this);
		gridView.setAdapter(mAdapter);
		keywordView = (EditText)findViewById(R.id.edit_keyword);
		Button btn = (Button)findViewById(R.id.btn_search);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String keyword = keywordView.getText().toString();
				if (keyword != null && !keyword.equals("")) {
					NetworkManager.getInstnace().getNaverImage(MainActivity.this, keyword, new OnResultListener<NaverImage>() {
						
						@Override
						public void onSuccess(NaverImage result) {
							mAdapter.addAll(result.items);
						}
						
						@Override
						public void onFail(int code) {
							
						}
					});
				}
			}
		});
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
