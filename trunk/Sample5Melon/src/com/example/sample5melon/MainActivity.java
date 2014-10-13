package com.example.sample5melon;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;

public class MainActivity extends ActionBarActivity {

	// TextView messageView;
	ListView listView;
	ArrayAdapter<Song> mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// messageView = (TextView)findViewById(R.id.message);
		listView = (ListView) findViewById(R.id.listView1);
		mAdapter = new ArrayAdapter<Song>(this,
				android.R.layout.simple_list_item_1, new ArrayList<Song>());
		listView.setAdapter(mAdapter);
		Button btn = (Button) findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new MyMelonTask().execute(1, 10);
			}
		});
	}

	public static final String URL_TEXT = "http://apis.skplanetx.com/melon/charts/realtime";
	public static final String APP_KEY = "458a10f5-c07e-34b5-b2bd-4a891e024c2a";

	class MyMelonTask extends AsyncTask<Integer, Integer, MelonObject> {
		@Override
		protected MelonObject doInBackground(Integer... params) {
			int page = params[0];
			int count = params[1];

			String urlText = URL_TEXT + "?version=1&page=" + page + "&count="
					+ count;
			try {
				URL url = new URL(urlText);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");
				conn.setRequestProperty("appKey", APP_KEY);
				conn.setConnectTimeout(30000);
				conn.setReadTimeout(30000);
				int code = conn.getResponseCode();
				if (code == HttpURLConnection.HTTP_OK) {
					InputStream is = conn.getInputStream();
					// BufferedReader br = new BufferedReader(new
					// InputStreamReader(is));
					// StringBuilder sb = new StringBuilder();
					// String line;
					// while((line = br.readLine()) != null) {
					// sb.append(line + "\n\r");
					// }
					// return sb.toString();
					Gson gson = new Gson();
					MelonResult result = gson.fromJson(
							new InputStreamReader(is), MelonResult.class);
					return result.melon;
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
		protected void onPostExecute(MelonObject result) {
			super.onPostExecute(result);
			if (result != null) {
				// messageView.setText(result);
				for (Song s : result.songs.song) {
					mAdapter.add(s);
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
