package com.example.samplenetworkmelon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	Handler mHandler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							URL url = new URL("http://apis.skplanetx.com/melon/charts/realtime?count=10&page=1&version=1");
							HttpURLConnection conn = (HttpURLConnection)url.openConnection();
							conn.setRequestMethod("GET");
							conn.setRequestProperty("Accept", "application/json");
							conn.setRequestProperty("appKey", "458a10f5-c07e-34b5-b2bd-4a891e024c2a");
							conn.setConnectTimeout(30000);
							conn.setReadTimeout(30000);
							
							int responseCode = conn.getResponseCode();
							if (responseCode == HttpURLConnection.HTTP_OK) {
								InputStream is = conn.getInputStream();
								BufferedReader br = new BufferedReader(new InputStreamReader(is));
								StringBuilder sb = new StringBuilder();
								String line;
								while((line = br.readLine())!= null) {
									sb.append(line);
									sb.append("\n\r");
								}
								final String message = sb.toString();
								JSONObject obj = new JSONObject(message);
								JSONObject melon = obj.getJSONObject("melon");
								final Melon melonObject = new Melon();
								melonObject.parseJSON(melon);
								
								mHandler.post(new Runnable() {
									
									@Override
									public void run() {
										Toast.makeText(MainActivity.this, "message : " + message, Toast.LENGTH_SHORT).show();
									}
								});
							}
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
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
