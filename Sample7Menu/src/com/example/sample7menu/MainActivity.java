package com.example.sample7menu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.MenuItemCompat.OnActionExpandListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.SearchView.OnQueryTextListener;
import android.support.v7.widget.ShareActionProvider;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		TextView tv = (TextView) findViewById(R.id.text_message);
		ImageView iv = (ImageView) findViewById(R.id.image_icon);

		registerForContextMenu(tv);
		registerForContextMenu(iv);
		
		Button btn = (Button)findViewById(R.id.btn_show_action_mode);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startSupportActionMode(new ActionMode.Callback() {
					
					@Override
					public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
						return false;
					}
					
					@Override
					public void onDestroyActionMode(ActionMode mode) {
						
					}
					
					@Override
					public boolean onCreateActionMode(ActionMode mode, Menu menu) {
						getMenuInflater().inflate(R.menu.action_mode_menu, menu);
						return true;
					}
					
					@Override
					public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
						switch(item.getItemId()) {
						case R.id.menu_mode_one :
						case R.id.menu_mode_two :
						case R.id.menu_mode_three :
							Toast.makeText(MainActivity.this, "ActionMode Menu Click", Toast.LENGTH_SHORT).show();
						}
						mode.finish();
						return false;
					}
				});
			}
		});
		
		btn = (Button)findViewById(R.id.btn_show_popup_menu);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				PopupMenu popup = new PopupMenu(MainActivity.this, v);
				popup.inflate(R.menu.popup_menu);
				popup.setOnMenuItemClickListener(new OnMenuItemClickListener() {
					
					@Override
					public boolean onMenuItemClick(MenuItem item) {
						switch(item.getItemId()) {
						case R.id.menu_popup_one :
						case R.id.menu_popup_two :
							Toast.makeText(MainActivity.this, "Popup Menu Click", Toast.LENGTH_SHORT).show();
							break;
						}
						return false;
					}
				});
				popup.show();
			}
		});
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu,
			View v,
			ContextMenuInfo menuInfo) {
		switch(v.getId()) {
		case R.id.text_message :
			getMenuInflater().inflate(R.menu.text_view_menu, menu);
			break;
		case R.id.image_icon :
			getMenuInflater().inflate(R.menu.image_view_menu, menu);
			break;
		}
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case R.id.menu_image_one :
		case R.id.menu_image_two :
		case R.id.menu_text_one :
		case R.id.menu_text_two :
			break;
		}
		return false;
	}

	EditText keywordView;

	SearchView searchView;
	ShareActionProvider actionProvider;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		MenuItem item = menu.findItem(R.id.action_settings);
		SearchView view = (SearchView) MenuItemCompat.getActionView(item);
		view.setOnQueryTextListener(new OnQueryTextListener() {

			@Override
			public boolean onQueryTextSubmit(String keyword) {
				Toast.makeText(MainActivity.this, "keyword : " + keyword,
						Toast.LENGTH_SHORT).show();
				return true;
			}

			@Override
			public boolean onQueryTextChange(String keyword) {

				return false;
			}
		});
		// SearchView view = (SearchView)MenuItemCompat.getActionView(item);
		// view.setOnSearchResultListener(new OnSearchResultListener() {
		//
		// @Override
		// public void onSearchResult(String keyword) {
		// Toast.makeText(MainActivity.this, "Search Keyword : " + keyword,
		// Toast.LENGTH_SHORT).show();
		// }
		// });
		// View view = MenuItemCompat.getActionView(item);
		// keywordView = (EditText) view.findViewById(R.id.edit_keyword);
		// Button btn = (Button) view.findViewById(R.id.btn_search);
		// btn.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// Toast.makeText(MainActivity.this,
		// "keyword : " + keywordView.getText().toString(),
		// Toast.LENGTH_SHORT).show();
		// }
		// });
		MenuItemCompat.setOnActionExpandListener(item,
				new OnActionExpandListener() {

					@Override
					public boolean onMenuItemActionCollapse(MenuItem item) {
						Toast.makeText(MainActivity.this, "collapse menu",
								Toast.LENGTH_SHORT).show();
						return true;
					}

					@Override
					public boolean onMenuItemActionExpand(MenuItem item) {
						Toast.makeText(MainActivity.this, "expand menu",
								Toast.LENGTH_SHORT).show();
						return true;
					}

				});

		item = menu.findItem(R.id.menu_one);
		actionProvider = (ShareActionProvider) MenuItemCompat
				.getActionProvider(item);
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
