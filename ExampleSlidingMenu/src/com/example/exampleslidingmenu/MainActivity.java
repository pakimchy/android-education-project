package com.example.exampleslidingmenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity implements
		MenuFragment.MenuCallbacks {

	private static final String TAG_MAIN = "main";
	private static final String TAG_FIRST = "first";
	private static final String TAG_SECOND = "second";
	private String screenTag = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setBehindContentView(R.layout.menu_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new MainFragment()).commit();
			screenTag = TAG_MAIN;
			getSupportFragmentManager().beginTransaction()
					.add(R.id.menu_container, new MenuFragment()).commit();
		}

		SlidingMenu sm = getSlidingMenu();
		sm.setMode(SlidingMenu.LEFT_RIGHT);
		sm.setSecondaryMenu(R.layout.menu_secondary);
		sm.setSecondaryShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setFadeDegree(0.5f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			toggle();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onMenuSelected(int menuId) {
		Fragment f = null;
		boolean backMain = false;
		boolean isAdd = false;
		switch (menuId) {
		case MenuFragment.MENU_ID_MAIN:
			if (screenTag != TAG_MAIN) {
				screenTag = TAG_MAIN;
				backMain = true;
			}
			break;
		case MenuFragment.MENU_ID_FIRST:
			if (screenTag != TAG_FIRST) {
				screenTag = TAG_FIRST;
				backMain = true;
				f = new FirstFragment();
				isAdd = true;
			}
			break;
		case MenuFragment.MENU_ID_SECOND:
			if (screenTag != TAG_SECOND) {
				screenTag = TAG_SECOND;
				backMain = false;
				f = new SecondFragment();
				isAdd = true;
			}
			break;
		}
		if (backMain) {
			getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
		}
		if (isAdd) {
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, f).addToBackStack(null).commit();
		}
		showContent();
	}
	
	
}
