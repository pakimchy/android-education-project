package com.example.exampleactivitytransition;

import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.os.Bundle;

public class MyActivity extends ActionBarActivity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_my);
	    Button btn = (Button)findViewById(R.id.button1);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
//				overridePendingTransition(R.anim.slide_in, R.anim.set_rotate_disappear);
			}
		});
	}

}
