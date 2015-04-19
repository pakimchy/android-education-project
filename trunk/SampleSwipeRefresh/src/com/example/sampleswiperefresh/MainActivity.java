package com.example.sampleswiperefresh;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity {

	SwipeRefreshLayout refresh;
	ListView listView;
	ArrayAdapter<String> mAdapter;
	Handler mHandler = new Handler(Looper.getMainLooper());
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refresh = (SwipeRefreshLayout)findViewById(R.id.refresh);
        listView = (ListView)findViewById(R.id.listView1);
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(mAdapter);
        refresh.setOnRefreshListener(new OnRefreshListener() {
			
			@Override
			public void onRefresh() {
				mHandler.postDelayed(new Runnable() {
					
					@Override
					public void run() {
						refresh.setRefreshing(false);
					}
				}, 2000);
			}
		});
        refresh.setColorSchemeColors(Color.RED, Color.GREEN,Color.BLUE);
        initData();
    }
    
    private void initData() {
    	for (int i = 0; i < 20; i++) {
    		mAdapter.add("item " + i);
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
