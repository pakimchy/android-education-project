package com.example.sample6applicationcomponent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyActivity extends ActionBarActivity {

	TextView messageView;
	public static final String EXTRA_NAME = "name";
	public static final String EXTRA_AGE = "age";
	public static final String EXTRA_PERSON = "person";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my);
		messageView = (TextView)findViewById(R.id.text_message);
		
		Button btn = (Button)findViewById(R.id.btn_finish);
		Intent intent = getIntent();
//		String text = intent.getStringExtra(EXTRA_NAME);
//		int age = intent.getIntExtra(EXTRA_AGE, 0);
//		Person p = (Person)intent.getSerializableExtra(EXTRA_PERSON);
		Person p = intent.getParcelableExtra(EXTRA_PERSON);
		messageView.setText(p.name + "(" + p.age +")");
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my, menu);
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
