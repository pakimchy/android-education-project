package com.example.sample7fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {

	FragmentManager mFragmentManager;
	Button btnTab1, btnTab2;
	private static final String TAG_F1 = "f1";
	private static final String TAG_F2 = "f2";
	OneFragment f1;
	TwoFragment f2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mFragmentManager = getSupportFragmentManager();
		btnTab1 = (Button) findViewById(R.id.btn_tab1);
		btnTab1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				changeTab1();
			}
		});
		btnTab2 = (Button) findViewById(R.id.btn_tab2);
		btnTab2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				changeTab2();
			}
		});
		f1 = new OneFragment();
		f2 = new TwoFragment();
		changeTab1();
		
		Button btn = (Button)findViewById(R.id.btn_other);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(MainActivity.this, OtherActivity.class));
			}
		});
		
		btn = (Button)findViewById(R.id.btn_send_fragment);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String text = ((EditText)findViewById(R.id.edit_message)).getText().toString();
				f1.setText(text);
			}
		});
	}

	private void changeTab1() {
		Fragment f = mFragmentManager.findFragmentByTag(TAG_F1);
		if (f == null) {
			FragmentTransaction ft = mFragmentManager.beginTransaction();
			Bundle bundle = new Bundle();
			String text = ((EditText)findViewById(R.id.edit_message)).getText().toString();
			bundle.putString("message", text);
			f1.setArguments(bundle);
			ft.replace(R.id.container, f1, TAG_F1);
			ft.commit();
			btnTab2.setSelected(false);
			btnTab1.setSelected(true);
		}
	}

	private void changeTab2() {
		Fragment f = mFragmentManager.findFragmentByTag(TAG_F2);
		if (f == null) {
			FragmentTransaction ft = mFragmentManager.beginTransaction();
			ft.replace(R.id.container, f2, TAG_F2);
			ft.commit();
			btnTab1.setSelected(false);
			btnTab2.setSelected(true);
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
