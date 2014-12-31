package com.example.sample6compoundwidget;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.example.sample6compoundwidget.MyItemView.OnMyIconClickListener;

public class MainActivity extends ActionBarActivity {

	MyItemView itemView;
	ImageView photoView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		photoView = (ImageView)findViewById(R.id.imageView1);
		photoView.setVisibility(View.GONE);
		photoView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				photoView.setVisibility(View.GONE);
			}
		});
		itemView = (MyItemView)findViewById(R.id.myItemView1);
		itemView.setOnMyIconClickListener(new OnMyIconClickListener() {
			
			@Override
			public void onMyIconClick(View v, MyItem item) {
				if (item != null) {
					photoView.setImageResource(item.iconResId);
					photoView.setVisibility(View.VISIBLE);
				}
			}
		});
		MyItem item = new MyItem();
		item.iconResId = R.drawable.ic_launcher;
		item.title = "My Name YSI";
		itemView.setMyItem(item);
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
