package com.example.sample6navermovie;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.ActionBarActivity;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sample6navermovie.NetworkManager.OnResultListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class MainActivity extends ActionBarActivity {

	PullToRefreshListView refreshView;
	
	ListView listView;
//	ArrayAdapter<MovieItem> mAdapter;
	MovieAdapter mAdapter;
	EditText keywordView;
	Handler mHandler = new Handler();
	long startTime;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		refreshView = (PullToRefreshListView)findViewById(R.id.listView1);
		refreshView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> rView) {
//				String label = DateUtils.formatDateTime(getApplicationContext(), System.currentTimeMillis(),
//						DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
//				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				
				String keyword = mAdapter.getKeyword();
				if (keyword != null && !keyword.equals("")) {
					int start = mAdapter.getCount() + 1;
					int total = mAdapter.getTotalCount();
					if (start <= total) {
						startTime = SystemClock.uptimeMillis();
						NetworkManager.getInstnace().getNaverMovie(MainActivity.this, keyword, start, new OnResultListener<NaverMovie>(){
							@Override
							public void onSuccess(final NaverMovie movie) {
								long currentTime = SystemClock.uptimeMillis();
								int delta = (int)(currentTime - startTime);
								if (delta < 2000) {
									mHandler.postDelayed(new Runnable() {
										public void run() {
											mAdapter.addAll(movie.items);
											refreshView.onRefreshComplete();
										}
									}, 2000 - delta);
								} else {
									mAdapter.addAll(movie.items);
									refreshView.onRefreshComplete();
								}
								
							}
							@Override
							public void onFail(int code) {
								
							}
						});
					} else {
						Toast.makeText(MainActivity.this, "no more item", Toast.LENGTH_SHORT).show();
						runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								refreshView.onRefreshComplete();
							}
						});
					}
				}
				
//				mHandler.postDelayed(new Runnable() {
//					
//					@Override
//					public void run() {
//						refreshView.onRefreshComplete();
//					}
//				}, 2000);
			}
		});
		listView = refreshView.getRefreshableView();
		mAdapter = new MovieAdapter(this);
		listView.setAdapter(mAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				MovieItem item = (MovieItem)listView.getItemAtPosition(position);
				Intent i = new Intent(MainActivity.this, BrowserActivity.class);
				i.setData(Uri.parse(item.link));
				startActivity(i);
			}
		});
		keywordView = (EditText)findViewById(R.id.edit_keyword);
		Button btn = (Button)findViewById(R.id.btn_search);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				new NaverMovieTask().execute();
				final String keyword = keywordView.getText().toString();
				if (keyword != null && !keyword.equals("")) {
					NetworkManager.getInstnace().getNaverMovie(MainActivity.this, keyword, 1, new OnResultListener<NaverMovie>() {
						
						@Override
						public void onSuccess(NaverMovie movie) {
							mAdapter.clear();
							mAdapter.addAll(movie.items);
							mAdapter.setTotalCount(movie.total);
							mAdapter.setKeyword(keyword);
						}

						@Override
						public void onFail(int code) {
							Toast.makeText(MainActivity.this, "fail...", Toast.LENGTH_SHORT).show();							
						}
					});
//					NetworkManager.getInstnace().getNaverMovie(keyword, new OnResultListener<NaverMovie>() {
//						
//						@Override
//						public void onSuccess(NaverMovie movie) {
////							for (MovieItem item : movie.items) {
////								mAdapter.add(item);
////							}
//							mAdapter.clear();
//							mAdapter.addAll(movie.items);
//						}
//
//						@Override
//						public void onFail(int code) {
//							Toast.makeText(MainActivity.this, "fail...", Toast.LENGTH_SHORT).show();							
//						}
//					});
				}
			}
		});		
	}
	
//	class NaverMovieTask extends AsyncTask<String, Integer, NaverMovie> {
//		@Override
//		protected NaverMovie doInBackground(String... params) {
//			String keyword = keywordView.getText().toString();
//			if (keyword != null && !keyword.equals("")) {
//				try {
//					String urlString = "http://openapi.naver.com/search?key=c1b406b32dbbbbeee5f2a36ddc14067f&display=10&start=1&target=movie&query=" + URLEncoder.encode(keyword, "utf-8");
//					URL url = new URL(urlString);
//					HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//					int responseCode = conn.getResponseCode();
//					if (responseCode == HttpURLConnection.HTTP_OK) {
//						InputStream is = conn.getInputStream();
//						XMLParser parser = new XMLParser();
//						NaverMovie movie = parser.fromXml(is, "channel", NaverMovie.class);
//						return movie;
//					}
//				} catch (UnsupportedEncodingException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (MalformedURLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}				
//			}
//			return null;
//		}
//		
//		@Override
//		protected void onPostExecute(NaverMovie result) {
//			super.onPostExecute(result);
//			if (result != null) {
//				for (MovieItem item : result.items) {
//					mAdapter.add(item);
//				}
//			}
//		}
//	}

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
