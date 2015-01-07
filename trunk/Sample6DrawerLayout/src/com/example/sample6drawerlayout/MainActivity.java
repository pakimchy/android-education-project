package com.example.sample6drawerlayout;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends ActionBarActivity {

	DrawerLayout mDrawer;
	ActionBarDrawerToggle mToggle;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mDrawer = (DrawerLayout)findViewById(R.id.drawer);
		
		mDrawer.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		mDrawer.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.END);
//		mDrawer.openDrawer(GravityCompat.START);
		mToggle = new ActionBarDrawerToggle(this, mDrawer, R.string.open, R.string.close);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		mDrawer.setDrawerListener(mToggle);
		mToggle.setHomeAsUpIndicator(R.drawable.ic_drawer);
//		mDrawer.setDrawerListener(new DrawerListener() {
//			
//			@Override
//			public void onDrawerStateChanged(int arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void onDrawerSlide(View arg0, float arg1) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void onDrawerOpened(View arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//			
//			@Override
//			public void onDrawerClosed(View arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
	}
	
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		mToggle.onConfigurationChanged(newConfig);
		super.onConfigurationChanged(newConfig);
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
		mToggle.onOptionsItemSelected(item);
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
