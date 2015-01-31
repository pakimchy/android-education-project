package com.example.samplenetworknavermovie;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.samplenetworknavermovie.NetworkManager.OnResultListener;

public class MainActivity extends Activity {

	EditText keywordView;
	ListView listView;
	MyAdapter mAdapter;
	private static final String KEY = "55f1e342c5bce1cac340ebb6032c7d9a";
	Handler mHandler = new Handler();
	NaverMovieRequest mRequest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		keywordView = (EditText) findViewById(R.id.edit_keyword);
		keywordView.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String keyword = s.toString();
				if (mRequest != null) {
					mRequest.setCancel(true);
					mRequest = null;
				}
				if (keyword != null && !keyword.equals("")) {
					mRequest = new NaverMovieRequest(keyword);
					NetworkManager.getInstance().getNaverMovie(MainActivity.this, mRequest,
							new OnResultListener<NaverMovies>() {

								@Override
								public void onSuccess(NetworkRequest request,
										NaverMovies result) {
									if (mRequest == request) {
										mAdapter.clear();
										for (MovieItem item : result.items) {
											mAdapter.add(item);
										}
									}
									mRequest = null;
								}

								@Override
								public void onFail(NetworkRequest request,
										int code) {

								}
							});
				} else {
					mAdapter.clear();
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});
		listView = (ListView) findViewById(R.id.listView1);
		mAdapter = new MyAdapter(this);
		listView.setAdapter(mAdapter);

		Button btn = (Button) findViewById(R.id.btn_search);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String keyword = keywordView.getText().toString();
				if (keyword != null && !keyword.equals("")) {
					NaverMovieRequest request = new NaverMovieRequest(keyword);
					NetworkManager.getInstance().getNaverMovie(MainActivity.this, request,
							new OnResultListener<NaverMovies>() {

								@Override
								public void onSuccess(NetworkRequest request,
										NaverMovies result) {
									for (MovieItem item : result.items) {
										mAdapter.add(item);
									}
								}

								@Override
								public void onFail(NetworkRequest request,
										int code) {

								}
							});
				}
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		NetworkManager.getInstance().cancel(this);
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
