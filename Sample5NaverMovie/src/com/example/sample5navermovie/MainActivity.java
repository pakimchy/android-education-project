package com.example.sample5navermovie;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sample5navermovie.NetworkManager.OnResultListener;

public class MainActivity extends ActionBarActivity {

	ListView listView;
	MyAdapter mAdapter;
	EditText keywordView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView)findViewById(R.id.listView1);
		mAdapter = new MyAdapter(this);
		listView.setAdapter(mAdapter);
		keywordView = (EditText)findViewById(R.id.keyword);
		Button btn = (Button)findViewById(R.id.btn_search);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String keyword = keywordView.getText().toString();
				if (keyword != null && !keyword.equals("")) {
					NetworkManager.getInstance().getNaverMovie(MainActivity.this, keyword, 1, 20, new OnResultListener<NaverMovies>() {
						
						@Override
						public void onSuccess(NaverMovies result) {
							mAdapter.addAll(result.item);
						}
						
						@Override
						public void onFail(int code) {
							Toast.makeText(MainActivity.this, "fail", Toast.LENGTH_SHORT).show();							
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
