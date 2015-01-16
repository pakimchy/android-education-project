package com.example.sample6autologin;

import com.example.sample6autologin.NetworkManager.OnResultListener;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignUpActivity extends ActionBarActivity {

	EditText idView;
	EditText passwordView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		idView = (EditText)findViewById(R.id.editText1);
		passwordView = (EditText)findViewById(R.id.editText2);
		Button btn = (Button)findViewById(R.id.btn_send);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				final String id = idView.getText().toString();
				final String password = passwordView.getText().toString();
				NetworkManager.getInstance().login(id, password, new OnResultListener() {
					
					@Override
					public void onSuccess(String message) {
						PropertyManager.getInstnace().setUserName(id);
						PropertyManager.getInstnace().setPassword(password);
						Intent intent = new Intent(SignUpActivity.this, MainActivity.class);						
						intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_NEW_TASK);
						startActivity(intent);
						finish();
					}
				});
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sign_up, menu);
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
