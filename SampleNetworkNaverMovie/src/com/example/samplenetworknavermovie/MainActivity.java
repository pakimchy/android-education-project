package com.example.samplenetworknavermovie;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	EditText keywordView;
	ListView listView;
	ArrayAdapter<String> mAdapter;
	private static final String KEY = "55f1e342c5bce1cac340ebb6032c7d9a";
	Handler mHandler = new Handler();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		keywordView = (EditText)findViewById(R.id.edit_keyword);
		listView = (ListView)findViewById(R.id.listView1);
		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1);
		listView.setAdapter(mAdapter);
		
		Button btn = (Button)findViewById(R.id.btn_search);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						String keyword = keywordView.getText().toString();
						if (keyword != null && !keyword.equals("")) {
							try {
								String urlText = String.format("http://openapi.naver.com/search?key=%s&query=%s&display=10&start=1&target=movie",KEY,URLEncoder.encode(keyword, "utf-8"));
								URL url = new URL(urlText);
								HttpURLConnection conn = (HttpURLConnection)url.openConnection();
								conn.setConnectTimeout(30000);
								conn.setReadTimeout(30000);
								int responseCode = conn.getResponseCode();
								if (responseCode == HttpURLConnection.HTTP_OK) {
									InputStream is = conn.getInputStream();
									BufferedReader br = new BufferedReader(new InputStreamReader(is));
									StringBuilder sb = new StringBuilder();
									String line;
									while((line = br.readLine()) != null) {
										sb.append(line);
										sb.append("\n\r");
									}
									final String message = sb.toString();
									mHandler.post(new Runnable() {
										
										@Override
										public void run() {
											Toast.makeText(MainActivity.this, "message : " + message, Toast.LENGTH_SHORT).show();
										}
									});
									
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
					}
				}).start();
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
