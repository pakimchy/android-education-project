package com.example.sample6menu;

import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TextView tv = (TextView)findViewById(R.id.text_message);
		registerForContextMenu(tv);
		
		EditText et = (EditText)findViewById(R.id.edit_input);
		registerForContextMenu(et);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		switch(v.getId()) {
		case R.id.text_message :
			getMenuInflater().inflate(R.menu.text_context, menu);
			break;
		case R.id.edit_input :
			getMenuInflater().inflate(R.menu.edit_context, menu);
			break;
		}
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.menu_t_1 :
		case R.id.menu_t_2 :
		case R.id.menu_e_1 :
		case R.id.menu_e_2 :
			Toast.makeText(this, "menu selected", Toast.LENGTH_SHORT).show();
			return true;
		}
		return super.onContextItemSelected(item);
	}
	
	EditText keywordView;
	
	SearchView searchView;
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		MenuItem item = menu.findItem(R.id.menu_one);
//		View view = MenuItemCompat.getActionView(item);
//		keywordView = (EditText)view.findViewById(R.id.edit_keyword);
//		Button btn = (Button)view.findViewById(R.id.btn_search);
//		btn.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				String keyword = keywordView.getText().toString();
//				Toast.makeText(MainActivity.this, "keyword : " + keyword, Toast.LENGTH_SHORT).show();
//			}
//		});
		searchView = (SearchView)MenuItemCompat.getActionView(item);
		searchView.setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String keyword) {
				Toast.makeText(MainActivity.this, "keyword : " + keyword, Toast.LENGTH_SHORT).show();
				return false;
			}
			
			@Override
			public boolean onQueryTextChange(String keyword) {
				return false;
			}
		});
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
		if (id == R.id.menu_one) {
			Toast.makeText(this, "Menu One Click", Toast.LENGTH_SHORT).show();
			return true;
		}
		if (id == R.id.item_sub_one) {
			Toast.makeText(this, "Menu Sub One Click", Toast.LENGTH_SHORT).show();
			return true;
		}
		if (id == R.id.item_sub_two) {
			Toast.makeText(this, "Menu Sub Two Click", Toast.LENGTH_SHORT).show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
