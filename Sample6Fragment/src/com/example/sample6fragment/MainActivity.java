package com.example.sample6fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {

//	Fragment tabOneFragment, tabTwoFragment;
	private static final String F1_TAG = "tab1";
	private static final String F2_TAG = "tab2";

	Button btnTab1, btnTab2;
	EditText inputView;
	TabOneFragment tabOneFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		inputView = (EditText)findViewById(R.id.edit_input);
		Button btn = (Button)findViewById(R.id.btn_send);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (tabOneFragment != null) {
					tabOneFragment.setMessage(inputView.getText().toString());
				}
			}
		});
		btnTab1 = (Button) findViewById(R.id.btn_tab1);
		btnTab1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				btnTab1.setSelected(true);
				btnTab2.setSelected(false);
				Fragment f = getSupportFragmentManager().findFragmentByTag(
						F1_TAG);
				if (f == null) {
					FragmentTransaction ft = getSupportFragmentManager()
							.beginTransaction();
					Bundle b = new Bundle();
					b.putString("message", inputView.getText().toString());
					tabOneFragment = new TabOneFragment();
					tabOneFragment.setArguments(b);
					
					ft.replace(R.id.container, tabOneFragment, F1_TAG);
					ft.commit();
				}
			}
		});

		btnTab2 = (Button) findViewById(R.id.btn_tab2);
		btnTab2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				btnTab1.setSelected(false);
				btnTab2.setSelected(true);
				Fragment f = getSupportFragmentManager().findFragmentByTag(
						F2_TAG);
				if (f == null) {
					FragmentTransaction ft = getSupportFragmentManager()
							.beginTransaction();
					f = new TabTwoFragment();
					ft.replace(R.id.container, f, F2_TAG);
					ft.commit();
					tabOneFragment = null;
				}
			}
		});

		tabOneFragment = new TabOneFragment();

		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.add(R.id.container, tabOneFragment, F1_TAG);
		ft.commit();
		btnTab1.setSelected(true);
		btnTab2.setSelected(false);
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
