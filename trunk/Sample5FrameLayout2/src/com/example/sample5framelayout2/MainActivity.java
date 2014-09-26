package com.example.sample5framelayout2;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {

	View content1, content2;
	Button btn1, btn2;
	View selectedView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		content1 = findViewById(R.id.content1);
		content2 = findViewById(R.id.content2);
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				selectTab(0);
			}
		});
		btn1 = btn;
		
		btn = (Button)findViewById(R.id.button2);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				selectTab(1);
			}
		});
		btn2 = btn;
		selectTab(0);
	}
	
	private void selectTab(int tabId) {
		switch(tabId) {
		case 0:
			if (selectedView != content1) {
				if (selectedView != null) {
					selectedView.setVisibility(View.GONE);
				}
				selectedView = content1;
				selectedView.setVisibility(View.VISIBLE);
				btn1.setSelected(true);
				btn2.setSelected(false);
			}
			break;
		case 1:
			if (selectedView != content2) {
				if (selectedView != null) {
					selectedView.setVisibility(View.GONE);
				}
				selectedView = content2;
				selectedView.setVisibility(View.VISIBLE);
				btn2.setSelected(true);
				btn1.setSelected(false);
			}
			break;
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
