package com.example.sampleyoutube;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.sampleyoutube.NetworkManager.OnResultListener;

public class MainActivity extends ActionBarActivity {

	private final static int REQUEST_CODE_OAUTH_LOGIN = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button)findViewById(R.id.btn_login);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String url = NetworkManager.getInstnace().getOAuthLoginURL();
				Intent intent = new Intent(MainActivity.this, BrowserActivity.class);
				intent.setData(Uri.parse(url));
				startActivityForResult(intent, REQUEST_CODE_OAUTH_LOGIN);
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_OAUTH_LOGIN && resultCode == Activity.RESULT_OK) {
			String code = data.getStringExtra(BrowserActivity.PARAM_CODE);
			NetworkManager.getInstnace().getAccessToken(this, code, new OnResultListener<AccessToken>() {
				
				@Override
				public void onSuccess(AccessToken result) {
					getYoutubePlayList();
				}
				
				@Override
				public void onFail(int code) {
					Toast.makeText(MainActivity.this, "retrive accessToken fail", Toast.LENGTH_SHORT).show();
				}
			});
		}
	}

	private void getYoutubePlayList() {
		NetworkManager.getInstnace().getYoutubePlayList(this, new OnResultListener<String>() {

			@Override
			public void onSuccess(String result) {
				Toast.makeText(MainActivity.this, "result : " + result, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onFail(int code) {
				Toast.makeText(MainActivity.this, "fail....", Toast.LENGTH_SHORT).show();
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
