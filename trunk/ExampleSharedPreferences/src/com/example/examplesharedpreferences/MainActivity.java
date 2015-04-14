package com.example.examplesharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {

	public static final String PREFS_NAME = "my_prefs";
	public static final String KEY_USER_NAME = "username";
	
	SharedPreferences mPrefs;
	SharedPreferences.Editor mEditor;
	
	EditText userNameView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mPrefs = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
		mEditor = mPrefs.edit();
		
		userNameView = (EditText)findViewById(R.id.edit_username);
		Button btn = (Button)findViewById(R.id.btn_save);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String userid = userNameView.getText().toString();
				mEditor.putString(KEY_USER_NAME, userid);
				mEditor.commit();
			}
		});
		
		String userid = mPrefs.getString(KEY_USER_NAME, "");
		userNameView.setText(userid);
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
