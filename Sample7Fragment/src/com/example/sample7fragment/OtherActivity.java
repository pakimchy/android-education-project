package com.example.sample7fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class OtherActivity extends ActionBarActivity implements OneFragment.MessageReceiver {

	OneFragment onef;
	TextView messageView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_other);
		String message = getIntent().getStringExtra("message");
		messageView = (TextView)findViewById(R.id.text_message);
		messageView.setText(message);
		onef = (OneFragment)getSupportFragmentManager().findFragmentById(R.id.fragment1);
		Button btn = (Button)findViewById(R.id.btn_get_fragment_value);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String text = onef.getEditValue();
				Toast.makeText(OtherActivity.this, text, Toast.LENGTH_SHORT).show();
			}
		});
		
		btn = (Button)findViewById(R.id.btn_finish);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent data = new Intent();
				String result = "echo : " + messageView.getText().toString();
				data.putExtra("result", result);
				setResult(Activity.RESULT_OK, data);
				finish();
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.other, menu);
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

	@Override
	public void receiveMessage(String message) {
		Toast.makeText(this, "message : " + message, Toast.LENGTH_SHORT).show();
	}
}
