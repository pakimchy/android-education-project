package com.example.sample5basicwidget;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
		messageView = (TextView)findViewById(R.id.message);
		checkBox = (CheckBox)findViewById(R.id.checkBox1);
		checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if (isChecked) {
					Toast.makeText(MainActivity.this, "isChecked true", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(MainActivity.this, "isChecked false", Toast.LENGTH_SHORT).show();
				}
			}
		});
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String msg = getResources().getString(R.string.hellostring);
				messageView.setText(Html.fromHtml(msg));
				
				Button btn = (Button)v;
//				if (btn.isSelected()) {
//					btn.setSelected(false);
//				} else {
//					btn.setSelected(true);
//				}
				btn.setSelected(!btn.isSelected());
				
				if (checkBox.isChecked()) {
					Toast.makeText(MainActivity.this, "checked", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(MainActivity.this, "unchecked", Toast.LENGTH_SHORT).show();
				}
			}
		});
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
