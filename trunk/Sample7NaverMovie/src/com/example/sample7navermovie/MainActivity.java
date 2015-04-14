package com.example.sample7navermovie;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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

import com.example.sample7navermovie.NetworkManager.OnResultListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;


public class MainActivity extends ActionBarActivity {

	ListView listView;
	EditText keywordView;
	MyAdapter mAdapter;
	PullToRefreshListView refreshView;
	Handler mHandler = new Handler(Looper.getMainLooper());
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refreshView = (PullToRefreshListView)findViewById(R.id.listView1);
        listView = (ListView)refreshView.getRefreshableView();
        mAdapter = new MyAdapter();
        listView.setAdapter(mAdapter);
        refreshView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> view) {
				String keyword = mAdapter.getKeyword();
				if (keyword != null && !keyword.equals("")) {
					int nextstart = mAdapter.getNextStart();
					if(nextstart != -1) {
						NetworkManager.getInstnace().getNaverMovie(MainActivity.this, keyword, nextstart, 10, new OnResultListener<NaverMovies>(){

							@Override
							public void onSuccess(final NaverMovies result) {
								mHandler.postDelayed(new Runnable() {
									
									@Override
									public void run() {
										mAdapter.addAll(result.movielist);
										refreshView.onRefreshComplete();
									}
								}, 2000);
							}

							@Override
							public void onFail(int code) {
								
							}
							
						});
					} else {
						Toast.makeText(MainActivity.this, "no more data", Toast.LENGTH_SHORT).show();
						mHandler.postDelayed(new Runnable() {
							
							@Override
							public void run() {
								refreshView.onRefreshComplete();
							}
						}, 100);
					}
				} else {
					Toast.makeText(MainActivity.this, "not set data", Toast.LENGTH_SHORT).show();
					mHandler.postDelayed(new Runnable() {
						
						@Override
						public void run() {
							refreshView.onRefreshComplete();
						}
					}, 100);
				}
			}
		});
        keywordView = (EditText)findViewById(R.id.edit_keyword);
        Button btn = (Button)findViewById(R.id.btn_search);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final String keyword = keywordView.getText().toString();
				if (keyword != null && !keyword.equals("")) {
					NetworkManager.getInstnace().getNaverMovie(MainActivity.this, keyword, 1, 10, new OnResultListener<NaverMovies>() {
						
						@Override
						public void onSuccess(NaverMovies result) {
							mAdapter.addAll(result.movielist);
							mAdapter.setKeyword(keyword);
							mAdapter.setTotal(result.total);
						}
						
						@Override
						public void onFail(int code) {
							Toast.makeText(MainActivity.this, "fail : " +code, Toast.LENGTH_SHORT).show();
						}
					});
				}
			}
		});
        listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				MovieItem item = (MovieItem)listView.getItemAtPosition(position);
				Intent intent = new Intent(MainActivity.this, BrowserActivity.class);
				intent.setData(Uri.parse(item.link));
				startActivity(intent);
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
