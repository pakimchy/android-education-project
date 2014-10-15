package com.example.sample5sharedpreferences;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {

	EditText idView;
	EditText passwordView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		idView = (EditText)findViewById(R.id.id_view);
		passwordView = (EditText)findViewById(R.id.password_view);
		
		Button btn = (Button)findViewById(R.id.btn_save);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String id = idView.getText().toString();
				String password = passwordView.getText().toString();
				PropertyManager.getInstance().setUserId(id);
				PropertyManager.getInstance().setPassword(password);
			}
		});
		
		idView.setText(PropertyManager.getInstance().getUserId());
		passwordView.setText(PropertyManager.getInstance().getPassword());
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
