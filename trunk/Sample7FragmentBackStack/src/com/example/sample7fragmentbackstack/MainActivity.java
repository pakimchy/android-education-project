package com.example.sample7fragmentbackstack;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {

	FragmentManager mFM;
	Fragment[] list = { new OneFragment(), new TwoFragment(), new ThreeFragment()};
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mFM = getSupportFragmentManager();
		Button btn = (Button) findViewById(R.id.btn_back);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				int count = mFM.getBackStackEntryCount();
				if (count > 0) {
					mFM.popBackStack();
				}
			}
		});

		btn = (Button) findViewById(R.id.btn_next);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				int count = mFM.getBackStackEntryCount();
				if (count < list.length) {
					Fragment f = list[count];
					FragmentTransaction ft = mFM.beginTransaction();
					ft.replace(R.id.container, f);
					ft.addToBackStack(null);
					ft.commit();
				} else {
					mFM.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
				}

			}
		});

		if (savedInstanceState == null) {
			BaseFragment f = new BaseFragment();
			FragmentTransaction ft = mFM.beginTransaction();
			ft.add(R.id.container, f);
			ft.commit();
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
