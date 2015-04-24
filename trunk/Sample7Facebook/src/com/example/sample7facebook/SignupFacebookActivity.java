package com.example.sample7facebook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.sample7facebook.NetworkManager.OnResultListener;
import com.facebook.AccessToken;

public class SignupFacebookActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup_facebook);
		Button btn = (Button)findViewById(R.id.btn_finish);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				NetworkManager.getInstance().signupFacebook(SignupFacebookActivity.this, new OnResultListener<String>() {
					
					@Override
					public void onSuccess(String result) {
						AccessToken token = AccessToken.getCurrentAccessToken();
						PropertyManager.getInstance().setFacebookId(token.getUserId());
						startActivity(new Intent(SignupFacebookActivity.this, MainActivity.class));
						finish();
					}
					
					@Override
					public void onError(int code) {
						
					}
				});
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.signup_facebook, menu);
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
