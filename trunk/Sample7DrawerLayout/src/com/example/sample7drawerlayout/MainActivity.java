package com.example.sample7drawerlayout;

import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements LeftMenuFragment.MenuCallback {

	DrawerLayout mDrawer;

	ActionBarDrawerToggle mToggle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mDrawer = (DrawerLayout)findViewById(R.id.drawer);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.contentcontainer, new MainFragment()).commit();
			LeftMenuFragment menuFragment = new LeftMenuFragment();
			menuFragment.setDrawerLayout(mDrawer);
			getSupportFragmentManager().beginTransaction()
					.add(R.id.leftmenu, menuFragment).commit();
			getSupportFragmentManager().beginTransaction()
					.add(R.id.rightmenu, new RightMenuFragment()).commit();
		}
		mDrawer.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
//		mDrawer.setDrawerListener(new DrawerListener() {
//			
//			@Override
//			public void onDrawerStateChanged(int state) {
//				
//			}
//			
//			@Override
//			public void onDrawerSlide(View view, float slideOffset) {
//				switch(view.getId()) {
//				case R.id.leftmenu :
//					int level = (int)(slideOffset * 10000);
//					icon.setLevel(level);
//					break;
//				case R.id.rightmenu :
//					
//				}
//			}
//			
//			@Override
//			public void onDrawerOpened(View view) {
//				switch(view.getId()) {
//				case R.id.leftmenu :
//				case R.id.rightmenu :
//					Toast.makeText(MainActivity.this, "drawer opened", Toast.LENGTH_SHORT).show();
//				}
//			}
//			
//			@Override
//			public void onDrawerClosed(View view) {
//				switch(view.getId()) {
//				case R.id.leftmenu :
//				case R.id.rightmenu :
//					Toast.makeText(MainActivity.this, "drawer closed", Toast.LENGTH_SHORT).show();
//				}
//			}
//		});
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		mToggle = new ActionBarDrawerToggle(this, mDrawer, R.string.open_desc, R.string.close_desc){
			@Override
			public void onDrawerClosed(View drawerView) {
				super.onDrawerClosed(drawerView);
				Toast.makeText(MainActivity.this, "drawer closed", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				Toast.makeText(MainActivity.this, "drawer open", Toast.LENGTH_SHORT).show();
			}
		};
		mToggle.setHomeAsUpIndicator(R.drawable.ic_drawer);
		mDrawer.setDrawerListener(mToggle);
//		icon = getResources().getDrawable(R.drawable.ic_home_as_up);
//		getSupportActionBar().setHomeAsUpIndicator(icon);
	}
	
	Drawable icon;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mToggle.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mToggle.syncState();
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		if (mToggle.onOptionsItemSelected(item)) {
			return true;
		}
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
//		if (id == android.R.id.home) {
//			if (mDrawer.isDrawerOpen(GravityCompat.START)) {
//				mDrawer.closeDrawer(GravityCompat.START);
//			} else {
//				mDrawer.openDrawer(GravityCompat.START);
//			}
//		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void selectedItem(int menuId) {
		switch(menuId) {
		case LeftMenuFragment.MENU_ID_ONE :
			Toast.makeText(this, "menu one", Toast.LENGTH_SHORT).show();
			break;
		case LeftMenuFragment.MENU_ID_TWO :
			Toast.makeText(this, "menu two", Toast.LENGTH_SHORT).show();
			break;
		case LeftMenuFragment.MENU_ID_THREE :
			Toast.makeText(this, "menu three", Toast.LENGTH_SHORT).show();
			break;
		}
//		mDrawer.closeDrawers();
	}
}
