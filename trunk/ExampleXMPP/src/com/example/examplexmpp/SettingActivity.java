package com.example.examplexmpp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SettingActivity extends ActionBarActivity {

	EditText idView, passwordView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		idView = (EditText)findViewById(R.id.edit_userid);
		passwordView = (EditText)findViewById(R.id.edit_password);
		Button btn = (Button)findViewById(R.id.btn_ok);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String id = idView.getText().toString();
				String password = passwordView.getText().toString();
				PropertyManager.getInstance().setUserId(id);
				PropertyManager.getInstance().setPassword(password);
				finish();
			}
		});
		
		btn = (Button)findViewById(R.id.btn_cancel);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		idView.setText(PropertyManager.getInstance().getUserId());
		passwordView.setText(PropertyManager.getInstance().getPassword());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.setting, menu);
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
