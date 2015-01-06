package com.example.sample6upnavigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ChildActivity extends ActionBarActivity {

	int count;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_child);
	    Intent i = getIntent();
	    count = i.getIntExtra("count", 0);
	    TextView tv = (TextView)findViewById(R.id.text_message);
	    tv.setText("count : " + count);
	    Button btn = (Button)findViewById(R.id.btn_next);
	    btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(ChildActivity.this, ChildActivity.class);
				i.putExtra("count", count+1);
				startActivity(i);
			}
		});
	    
	    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == android.R.id.home) {
//			NavUtils.navigateUpFromSameTask(this);
			Intent i = new Intent(ChildActivity.this, MainActivity.class);
			i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			startActivity(i);
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
