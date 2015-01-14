package com.example.sample6melon;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.sample6melon.NetworkManager.OnResultListener;
import com.google.gson.Gson;

public class MainActivity extends ActionBarActivity {

	ListView listView;
	ArrayAdapter<Song> mAdapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView)findViewById(R.id.listView1);
		mAdapter = new ArrayAdapter<Song>(this, android.R.layout.simple_list_item_1);
		listView.setAdapter(mAdapter);
		Button btn = (Button)findViewById(R.id.btn_melon);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				new MelonTask().execute();
				NetworkManager.getInstnace().getMelon(MainActivity.this, 1, 10, new OnResultListener<ResultMelon>() {
					
					@Override
					public void onSuccess(ResultMelon movie) {
						for (Song s : movie.melon.songlist.song) {
							mAdapter.add(s);
						}
						Toast.makeText(MainActivity.this, "result : " + movie, Toast.LENGTH_SHORT).show();
					}
					
					@Override
					public void onFail(int code) {
						Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
					}
				});
			}
		});
	}

	class MelonTask extends AsyncTask<String, Integer, ResultMelon> {
		@Override
		protected ResultMelon doInBackground(String... params) {
			String urlString = "http://apis.skplanetx.com/melon/charts/realtime?count=10&page=1&version=1";
			try {
				URL url = new URL(urlString);
				HttpURLConnection conn = (HttpURLConnection)url.openConnection();
				conn.setRequestProperty("Accept", "application/json");
				conn.setRequestProperty("appKey", "458a10f5-c07e-34b5-b2bd-4a891e024c2a");
				int responseCode = conn.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {
					InputStream is = conn.getInputStream();
					Gson gson = new Gson();
					ResultMelon result = gson.fromJson(new InputStreamReader(is), ResultMelon.class);
					return result;
//					BufferedReader br = new BufferedReader(new InputStreamReader(is));
//					StringBuilder sb = new StringBuilder();
//					String line;
//					while((line = br.readLine()) != null) {
//						sb.append(line);
//						sb.append("\n\r");
//					}
//					return sb.toString();
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			return null;
		}
		
		@Override
		protected void onPostExecute(ResultMelon result) {
			super.onPostExecute(result);
			if (result != null) {
				for (Song s : result.melon.songlist.song) {
					mAdapter.add(s);
				}
				Toast.makeText(MainActivity.this, "result : " + result, Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
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
