package com.example.sample5fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Button btn = (Button)findViewById(R.id.btn_tab1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentOne f = new FragmentOne();
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				ft.replace(R.id.container, f);
				ft.commit();
			}
		});
		
		btn = (Button)findViewById(R.id.btn_tab2);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentTwo f = new FragmentTwo();
				FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
				ft.replace(R.id.container, f);
				ft.commit();
			}
		});
		
		FragmentOne f = new FragmentOne();
		
		Bundle b = new Bundle();
		b.putString("message", "i am activity");
		
		f.setArguments(b);
		
		
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.container, f);
		ft.commit();	
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
