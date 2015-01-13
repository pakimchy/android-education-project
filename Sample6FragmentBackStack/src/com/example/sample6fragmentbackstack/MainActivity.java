package com.example.sample6fragmentbackstack;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	Fragment[] list = { new OneFragment(), new TwoFragment(),
			new ThreeFragment() };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button) findViewById(R.id.btn_prev);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				int count = getSupportFragmentManager()
						.getBackStackEntryCount();
				if (count > 0) {
					getSupportFragmentManager().popBackStack();
				} else {
					Toast.makeText(MainActivity.this, "no more backstack",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		btn = (Button) findViewById(R.id.btn_next);
		btn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				int count = getSupportFragmentManager()
						.getBackStackEntryCount();
				if (count < list.length) {
					getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out, R.anim.slide_right_in, R.anim.slide_right_out)
							.replace(R.id.container, list[count])
							.addToBackStack(null).commit();
				} else {
					Toast.makeText(MainActivity.this, "max fragment",
							Toast.LENGTH_SHORT).show();
					getSupportFragmentManager().popBackStack(null,
							FragmentManager.POP_BACK_STACK_INCLUSIVE);
				}
			}
		});

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new BaseFragment()).commit();
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
