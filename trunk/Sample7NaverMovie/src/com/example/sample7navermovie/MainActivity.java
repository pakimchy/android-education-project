package com.example.sample7navermovie;

import android.content.Intent;
import android.net.Uri;
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

import com.example.sample7navermovie.NetworkManager.OnResultListener;


public class MainActivity extends ActionBarActivity {

	ListView listView;
	EditText keywordView;
	MyAdapter mAdapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.listView1);
        mAdapter = new MyAdapter();
        listView.setAdapter(mAdapter);
        keywordView = (EditText)findViewById(R.id.edit_keyword);
        Button btn = (Button)findViewById(R.id.btn_search);
        btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String keyword = keywordView.getText().toString();
				if (keyword != null && !keyword.equals("")) {
					NetworkManager.getInstnace().getNaverMovie(MainActivity.this, keyword, 1, 10, new OnResultListener<NaverMovies>() {
						
						@Override
						public void onSuccess(NaverMovies result) {
							mAdapter.addAll(result.movielist);
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
