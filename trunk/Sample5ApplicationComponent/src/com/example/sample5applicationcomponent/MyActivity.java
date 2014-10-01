package com.example.sample5applicationcomponent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MyActivity extends Activity {
	
	public static final String PARAM_MESSAGE = "message";
	public static final String PARAM_AGE = "age";
	
	TextView messageView;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
		setContentView(R.layout.my_activity_layout);
		messageView = (TextView)findViewById(R.id.message);
		
		Button btn = (Button)findViewById(R.id.button1);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		Intent i = getIntent();
		String text = i.getStringExtra(PARAM_MESSAGE);
		int age = i.getIntExtra(PARAM_AGE, 0);
		messageView.setText(text + ", age : " + age);
	}

}
