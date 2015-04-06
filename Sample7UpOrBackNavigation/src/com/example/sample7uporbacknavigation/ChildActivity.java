package com.example.sample7uporbacknavigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ChildActivity extends ActionBarActivity {

	int mNumber;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_child);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		Intent i = getIntent();
		mNumber = i.getIntExtra("number", 1);
		addFragment(mNumber);
//		TextView tv = (TextView)findViewById(R.id.text_number);
//		tv.setText("Number : " + mNumber);
//		Button btn = (Button)findViewById(R.id.btn_next);
//		btn.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent(ChildActivity.this, ChildActivity.class);
//				intent.putExtra("number", mNumber + 1);
//				startActivity(intent);
//			}
//		});
	}

	public void addFragment(int number) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		Bundle b = new Bundle();
		b.putInt("number", number);
		ChildFragment f = new ChildFragment();
		f.setArguments(b);
		ft.replace(R.id.container, f);
		ft.addToBackStack(null);
		ft.commit();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.child, menu);
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
		if (id == android.R.id.home) {
//			NavUtils.navigateUpFromSameTask(this);
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
