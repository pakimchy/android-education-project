package com.example.sample5applicationcomponent;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
				Person p = new Person();
				p.name = "ysi";
				p.age = 40;
				i.putExtra(MyActivity.PARAM_PERSON, p);
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
		
		btn = (Button)findViewById(R.id.button3);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, MyService.class);
				i.putExtra(MyService.PARAM_COUNT, 100);
				startService(i);
			}
		});
		
		btn = (Button)findViewById(R.id.button4);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, MyService.class);
				stopService(i);
			}
		});
		
		IntentFilter filter = new IntentFilter(MyService.ACTION_COUNT);
		registerReceiver(mReceiver, filter);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unregisterReceiver(mReceiver);
	}

	BroadcastReceiver mReceiver = new BroadcastReceiver() {
		
		@Override
		public void onReceive(Context context, Intent intent) {
			int count = intent.getIntExtra(MyService.PARAM_COUNT, 0);
			Toast.makeText(MainActivity.this, "count : " + count, Toast.LENGTH_SHORT).show();
			setResultCode(Activity.RESULT_OK);
		}
	};
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);		
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
