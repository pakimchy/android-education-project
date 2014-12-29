package com.example.sample6basicwidget;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	TextView messageView;
	CheckBox checkBox;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		messageView = (TextView)findViewById(R.id.text_message);
//		tv.setText(R.string.hi);
		String text = getResources().getString(R.string.hi);
//		int count = 10;
		messageView.setText(Html.fromHtml(text));
		
//		Button btn = (Button)findViewById(R.id.button1);
//		btn.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Toast.makeText(MainActivity.this, "Button Clicked", Toast.LENGTH_SHORT).show();
//			}
//		});
		
		checkBox = (CheckBox)findViewById(R.id.checkBox1);
		
		checkBox.setChecked(true);
		
		checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				Toast.makeText(MainActivity.this, "checked changed!!!", Toast.LENGTH_SHORT).show();
			}
		});
		
	}
	
	public void onClickButton(View v) {
		boolean isChecked = checkBox.isChecked();
		String message;
		if (isChecked) {
			message = getResources().getString(R.string.check_message);
		} else {
			message = getResources().getString(R.string.not_check_message);
		}
		Toast.makeText(this, "Button Clicked : " + message, Toast.LENGTH_SHORT).show();
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
