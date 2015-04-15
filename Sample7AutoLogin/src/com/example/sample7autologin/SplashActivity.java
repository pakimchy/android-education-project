package com.example.sample7autologin;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.sample7autologin.NetworkManager.OnResultListener;

public class SplashActivity extends ActionBarActivity {

	Handler mHandler = new Handler(Looper.getMainLooper());
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		String userid = PropertyManager.getInstance().getUserId();
		if (!userid.equals("")) {
			String password = PropertyManager.getInstance().getPassword();
			NetworkManager.getInstance().login(this, userid, password, new OnResultListener<String>() {
				
				@Override
				public void onSuccess(String result) {
					startActivity(new Intent(SplashActivity.this, MainActivity.class));
					finish();
				}
				
				@Override
				public void onError(int code) {
					startActivity(new Intent(SplashActivity.this, LoginActivity.class));
					finish();
				}
			});
		} else {
			mHandler.postDelayed(new Runnable() {
				
				@Override
				public void run() {
					startActivity(new Intent(SplashActivity.this, LoginActivity.class));
					finish();
				}
			}, 2000);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash, menu);
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
