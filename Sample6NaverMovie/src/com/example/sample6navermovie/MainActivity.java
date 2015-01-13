package com.example.sample6navermovie;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import com.begentgroup.xmlparser.XMLParser;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {

	ListView listView;
	ArrayAdapter<MovieItem> mAdapter;
	EditText keywordView;
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
				new NaverMovieTask().execute();
			}
		});		
	}
	
	class NaverMovieTask extends AsyncTask<String, Integer, NaverMovie> {
		@Override
		protected NaverMovie doInBackground(String... params) {
			String keyword = keywordView.getText().toString();
			if (keyword != null && !keyword.equals("")) {
				try {
					String urlString = "http://openapi.naver.com/search?key=c1b406b32dbbbbeee5f2a36ddc14067f&display=10&start=1&target=movie&query=" + URLEncoder.encode(keyword, "utf-8");
					URL url = new URL(urlString);
					HttpURLConnection conn = (HttpURLConnection)url.openConnection();
					int responseCode = conn.getResponseCode();
					if (responseCode == HttpURLConnection.HTTP_OK) {
						InputStream is = conn.getInputStream();
						XMLParser parser = new XMLParser();
						NaverMovie movie = parser.fromXml(is, "channel", NaverMovie.class);
						return movie;
					}
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(NaverMovie result) {
			super.onPostExecute(result);
			if (result != null) {
				for (MovieItem item : result.items) {
					mAdapter.add(item);
				}
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
