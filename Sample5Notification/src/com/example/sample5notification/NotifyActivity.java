package com.example.sample5notification;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class NotifyActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.notify_activity);
	    Intent intent = getIntent();
	    int i = intent.getIntExtra("param1", -1);
	    Toast.makeText(this, "count : " + i, Toast.LENGTH_SHORT).show();
	}

}
