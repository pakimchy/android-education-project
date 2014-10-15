package com.example.sample5navermovie;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sample5navermovie.NetworkManager.OnResultListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class MainActivity extends ActionBarActivity {

	ListView listView;
	MyAdapter mAdapter;
	EditText keywordView;
	PullToRefreshListView pullView;
	String query;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		pullView = (PullToRefreshListView) findViewById(R.id.listView1);
		pullView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				int start = mAdapter.getCount() + 1;
				if (query != null && mAdapter.getTotal() > start) {
					NetworkManager.getInstance().getNaverMovie(
							MainActivity.this, query, start, 10,
							new OnResultListener<NaverMovies>() {

								@Override
								public void onSuccess(NaverMovies result) {
									mAdapter.addAll(result.items);
									mAdapter.setTotal(result.total);
									pullView.onRefreshComplete();
								}

								@Override
								public void onFail(int code) {
									Toast.makeText(MainActivity.this,
											"fail...", Toast.LENGTH_SHORT)
											.show();
									pullView.onRefreshComplete();
								}
							});
				} else {
					Toast.makeText(MainActivity.this, "end...", Toast.LENGTH_SHORT).show();
					pullView.postDelayed(new Runnable() {
						
						@Override
						public void run() {
							pullView.onRefreshComplete();
						}
					}, 1000);
				}
				// Toast.makeText(MainActivity.this,
				// "onRefresh",Toast.LENGTH_SHORT).show();
				// pullView.postDelayed(new Runnable() {
				//
				// @Override
				// public void run() {
				// pullView.onRefreshComplete();
				// }
				// }, 2000);
			}
		});
		listView = pullView.getRefreshableView();
		mAdapter = new MyAdapter(this);
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				MovieItem item = (MovieItem) listView
						.getItemAtPosition(position);

				if (item.link != null && !item.link.equals("")) {
					Intent i = new Intent(MainActivity.this,
							BrowserActivity.class);
					i.putExtra(BrowserActivity.PARAM_URL, item.link);
					// Intent i = new Intent(Intent.ACTION_VIEW,
					// Uri.parse(item.link));
					startActivity(i);
				}
			}
		});
		keywordView = (EditText) findViewById(R.id.keyword);
		Button btn = (Button) findViewById(R.id.btn_search);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String keyword = keywordView.getText().toString();
				if (keyword != null && !keyword.equals("")) {
					query = keyword;
					mAdapter.clearAll();
					NetworkManager.getInstance().getNaverMovie(
							MainActivity.this, keyword, 1, 10,
							new OnResultListener<NaverMovies>() {

								@Override
								public void onSuccess(NaverMovies result) {
									mAdapter.addAll(result.items);
									mAdapter.setTotal(result.total);
								}

								@Override
								public void onFail(int code) {
									Toast.makeText(MainActivity.this, "fail",
											Toast.LENGTH_SHORT).show();
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
