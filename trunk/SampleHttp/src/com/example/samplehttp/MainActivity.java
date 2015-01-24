package com.example.samplehttp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	TextView messageView;
	
	private static final int MESSAGE_TEXT = 1;
	
	Handler mHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch(msg.what) {
			case MESSAGE_TEXT :
				String message = (String)msg.obj;
				messageView.setText(message);
				break;
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		messageView = (TextView)findViewById(R.id.text_message);
		Button btn = (Button)findViewById(R.id.btn_get_google);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {
					
					@Override
					public void run() {
						try {
							URL url = new URL("http://www.google.com");
							HttpURLConnection conn = (HttpURLConnection)url.openConnection();
							conn.setConnectTimeout(30000);
							conn.setReadTimeout(3000);
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
								String message = sb.toString();
//								messageView.setText(message);
								Message msg = mHandler.obtainMessage(MESSAGE_TEXT, message);
								mHandler.sendMessage(msg);
							}
							
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
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
