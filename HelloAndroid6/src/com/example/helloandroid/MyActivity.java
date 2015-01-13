package com.example.helloandroid;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.support.v7.app.ActionBarActivity;

public class MyActivity extends ActionBarActivity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_my);
	    Button btn = (Button)findViewById(R.id.btn_finish);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(MyActivity.this, "MyActivity finished", Toast.LENGTH_SHORT).show();
				
				finish();
//				overridePendingTransition(R.anim.slide_left_in, R.anim.slide_left_out);
			}
		});
	}

}
