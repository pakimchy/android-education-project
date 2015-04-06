package com.example.sample7menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.MenuItemCompat.OnActionExpandListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	EditText keywordView;

	SearchView searchView;
	ShareActionProvider actionProvider;
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		MenuItem item = menu.findItem(R.id.action_settings);
		SearchView view = (SearchView)MenuItemCompat.getActionView(item);
		view.setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String keyword) {
				Toast.makeText(MainActivity.this, "keyword : " + keyword, Toast.LENGTH_SHORT).show();
				return true;
			}
			
			@Override
			public boolean onQueryTextChange(String keyword) {
				
				return false;
			}
		});
//		SearchView view = (SearchView)MenuItemCompat.getActionView(item);
//		view.setOnSearchResultListener(new OnSearchResultListener() {
//			
//			@Override
//			public void onSearchResult(String keyword) {
//				Toast.makeText(MainActivity.this, "Search Keyword : " + keyword, Toast.LENGTH_SHORT).show();
//			}
//		});
//		View view = MenuItemCompat.getActionView(item);
//		keywordView = (EditText) view.findViewById(R.id.edit_keyword);
//		Button btn = (Button) view.findViewById(R.id.btn_search);
//		btn.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				Toast.makeText(MainActivity.this,
//						"keyword : " + keywordView.getText().toString(),
//						Toast.LENGTH_SHORT).show();
//			}
//		});
		MenuItemCompat.setOnActionExpandListener(item, new OnActionExpandListener() {

			@Override
			public boolean onMenuItemActionCollapse(MenuItem item) {
				Toast.makeText(MainActivity.this, "collapse menu", Toast.LENGTH_SHORT).show();
				return true;
			}

			@Override
			public boolean onMenuItemActionExpand(MenuItem item) {
				Toast.makeText(MainActivity.this, "expand menu", Toast.LENGTH_SHORT).show();
				return true;
			}
			
		});
		
		item = menu.findItem(R.id.menu_one);
		actionProvider = (ShareActionProvider)MenuItemCompat.getActionProvider(item);
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("image/*");
		actionProvider.setShareIntent(intent);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			Toast.makeText(this, "selected Menu", Toast.LENGTH_SHORT).show();
			return true;
		} else if (id == R.id.menu_one) {
			Toast.makeText(this, "menu one click", Toast.LENGTH_SHORT).show();
			return true;
		} else if (id == R.id.menu_sub_1) {
			Toast.makeText(this, "menu sub one click", Toast.LENGTH_SHORT)
					.show();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
