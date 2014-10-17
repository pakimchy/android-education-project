package com.example.sample5facebook;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;

public class MyFragmentActivity extends ActionBarActivity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_activity);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new MyFragment()).commit();
		}
		setTitle("MyFragmentActivity");
	}

}
