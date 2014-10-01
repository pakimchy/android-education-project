package com.example.sample5applicationcomponent;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	EditText inputView;
	TextView messageView;
	private final static int REQUEST_CODE_MY_ACTIVITY = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		inputView = (EditText)findViewById(R.id.edit_input);
		messageView = (TextView)findViewById(R.id.message);
		
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, MyActivity.class);
				String text = inputView.getText().toString();
				i.putExtra(MyActivity.PARAM_MESSAGE, text);
				i.putExtra(MyActivity.PARAM_AGE, 40);
				startActivityForResult(i, REQUEST_CODE_MY_ACTIVITY);
			}
		});
		
		btn = (Button)findViewById(R.id.button2);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
				startActivity(i);
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == REQUEST_CODE_MY_ACTIVITY && resultCode == Activity.RESULT_OK) {
			String result = data.getStringExtra(MyActivity.PARAM_RESULT);
			messageView.setText(result);
		}
		
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
