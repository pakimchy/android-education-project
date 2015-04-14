package com.example.sample7networkmodel;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.sample7networkmodel.NetworkManager.OnResultListener;


public class MainActivity extends ActionBarActivity {

	ListView listView;
	EditText keywordView;
	ArrayAdapter<MovieItem> mAdapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.listView1);
        mAdapter = new ArrayAdapter<MovieItem>(this, android.R.layout.simple_list_item_1);
        listView.setAdapter(mAdapter);
        keywordView = (EditText)findViewById(R.id.edit_keyword);
        Button btn = (Button)findViewById(R.id.btn_search);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String keyword = keywordView.getText().toString();
				if (keyword != null && !keyword.equals("")) {
					MovieRequest request = new MovieRequest(keyword);
					NetworkManager.getInstance().getNetworkData(MainActivity.this, request, new OnResultListener<NaverMovies>() {

						@Override
						public void onSuccess(
								NetworkRequest<NaverMovies> request,
								NaverMovies result) {
							for (MovieItem item : result.movielist) {
								mAdapter.add(item);
							}
						}

						@Override
						public void onFail(NetworkRequest<NaverMovies> request,
								int code) {
							
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
