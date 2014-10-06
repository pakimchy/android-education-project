package com.example.sample5menu;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TextView tv = (TextView)findViewById(R.id.message);
		registerForContextMenu(tv);
		ImageView iv = (ImageView)findViewById(R.id.imageView1);
		registerForContextMenu(iv);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		switch(v.getId()) {
		case R.id.message :
			getMenuInflater().inflate(R.menu.text_menu, menu);
			break;
		case R.id.imageView1 :
			getMenuInflater().inflate(R.menu.image_menu, menu);
			break;
		}
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.text_menu_1 :
			Toast.makeText(this, "text menu 1 select", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.text_menu_2 :
			Toast.makeText(this, "text menu 2 select", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.image_menu_1 :
			Toast.makeText(this, "image menu 1 select", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.image_menu_2 :
			Toast.makeText(this, "image menu 2 select", Toast.LENGTH_SHORT).show();
			return true;
		}
		return super.onContextItemSelected(item);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		} else if (id == R.id.menu_1) {
			Toast.makeText(this, "Menu 1 select", Toast.LENGTH_SHORT).show();
			return true;
		} else if (id == R.id.menu_2) {
			Toast.makeText(this, "Menu 2 select", Toast.LENGTH_SHORT).show();
			return true;
		}
		
		return super.onOptionsItemSelected(item);
	}
}
