package com.example.sample5fragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.example.sample5fragment.FragmentOne.OnResultListener;

public class MyActivity extends ActionBarActivity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	
	    setContentView(R.layout.my_activity_layout);
	    
	    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
	    FragmentOne f = new FragmentOne();
	    f.setOnResultListener(new OnResultListener() {
			
			@Override
			public void onResult(String message) {
				Toast.makeText(MyActivity.this, "message : " + message, Toast.LENGTH_SHORT).show();
			}
		});
	    ft.replace(R.id.container, f);
	    ft.commit();
	}

}
