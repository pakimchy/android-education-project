package com.example.sample5fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity 
implements FragmentOne.OnFragmentMessage {

	FragmentOne f1;
	FragmentTwo f2;
	FragmentThree f3;
	private static final String F1_TAG = "f1";
	private static final String F2_TAG = "f2";
	private static final String F3_TAG = "f3";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button)findViewById(R.id.btn_tab1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				FragmentOne f = new FragmentOne();
				if (getSupportFragmentManager().findFragmentByTag(F1_TAG) == null) {
					FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
					ft.replace(R.id.container, f1, F1_TAG);
					ft.commit();
				}
			}
		});
		
		btn = (Button)findViewById(R.id.btn_tab2);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				FragmentTwo f = new FragmentTwo();
				if (getSupportFragmentManager().findFragmentByTag(F2_TAG) == null) {
					FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
					ft.replace(R.id.container, f2, F2_TAG);
					ft.commit();
				}
			}
		});
		
		btn = (Button)findViewById(R.id.btn_tab3);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (getSupportFragmentManager().findFragmentByTag(F3_TAG) == null) {
					FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
					ft.replace(R.id.container, f3, F3_TAG);
					ft.commit();
				}
			}
		});
		
		btn = (Button)findViewById(R.id.btn_activity);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, MyActivity.class);
				startActivity(i);
			}
		});
		
		f1 = new FragmentOne();
		
		Bundle b = new Bundle();
		b.putString("message", "i am activity");
		
		f1.setArguments(b);
		
		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.container, f1, F1_TAG);
		ft.commit();	
		
		f2 = new FragmentTwo();
		f3 = new FragmentThree();
	}
	
	public void onFragmentDataChanged(String text) {
		Toast.makeText(this, "fragment : " + text, Toast.LENGTH_SHORT).show();
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

	@Override
	public void onFragmentMessage(String message) {
		Toast.makeText(this, "fragment : " + message, Toast.LENGTH_SHORT).show();
	}
}
