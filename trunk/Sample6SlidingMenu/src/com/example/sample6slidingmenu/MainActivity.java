package com.example.sample6slidingmenu;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity implements
		MenuFragment.MenuCallback {

	SlidingMenu sm;
	private static final String TAG_MAIN = "main";
	private static final String TAG_ONE = "one";
	private static final String TAG_TWO = "two";
	
	private String currentTag;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setBehindContentView(R.layout.menu_container);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new MainFragment()).commit();
			getSupportFragmentManager().beginTransaction()
					.add(R.id.menu_container, new MenuFragment()).commit();
			currentTag = TAG_MAIN;
		} else {
			String tag = savedInstanceState.getString("tag");
			if (tag.equals(TAG_MAIN)) {
				currentTag = TAG_MAIN;
			} else if (tag.equals(TAG_ONE)) {
				currentTag = TAG_ONE;
			} else if (tag.equals(TAG_TWO)) {
				currentTag = TAG_TWO;
			} else {
				currentTag = TAG_MAIN;
			}
		}

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);

		sm = getSlidingMenu();
		sm.setMode(SlidingMenu.LEFT_RIGHT);
		sm.setSecondaryMenu(R.layout.secondary_menu);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setFadeDegree(0.5f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		setSlidingActionBarEnabled(false);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString("tag", currentTag);
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
		if (id == android.R.id.home) {
			toggle();
			return true;
		}
		if (id == R.id.action_settings) {
			return true;
		}
		if (id == R.id.menu_second) {
			if (sm.isSecondaryMenuShowing()) {
				sm.showContent();
			} else {
				sm.showSecondaryMenu(true);
			}
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
	@Override
	public void selectMenu(int menuId) {
		switch (menuId) {
		case MenuFragment.MENU_MAIN:
			if (currentTag != TAG_MAIN) {
				getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
				currentTag = TAG_MAIN;
			}
			break;
		case MenuFragment.MENU_ONE:
			if (currentTag != TAG_ONE) {
				getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
				getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, new OneFragment()).addToBackStack(null).commit();
				currentTag = TAG_ONE;
			}
			break;
		case MenuFragment.MENU_TWO:
			if (currentTag != TAG_TWO) {
				getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
				getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, new TwoFragment()).addToBackStack(null).commit();
				currentTag = TAG_TWO;
			}
			break;
		}
		showContent();
	}
}
