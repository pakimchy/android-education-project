package com.example.sample7slidingmenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity implements
		MenuFragment.MenuCallback {

	SlidingMenu mSliding;
	public static final String TAG_MAIN = "main";
	public static final String TAG_ONE = "one";
	public static final String TAG_TWO = "two";
	public static final String TAG_THREE = "three";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setBehindContentView(R.layout.left_menu_layout);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new MainFragment(), TAG_MAIN).commit();
			getSupportFragmentManager().beginTransaction()
					.add(R.id.menu_container, new MenuFragment()).commit();
		}
		mSliding = getSlidingMenu();
		mSliding.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		mSliding.setShadowWidthRes(R.dimen.shadow_width);
		mSliding.setShadowDrawable(R.drawable.shadow);
		mSliding.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		// mSliding.setSecondaryMenu(R.layout.right_menu_layout);
		// mSliding.setSecondaryShadowDrawable(R.drawable.shadowright);
		// mSliding.setMode(SlidingMenu.LEFT_RIGHT);
		// mSliding.setOnOpenedListener(new OnOpenedListener() {
		//
		// @Override
		// public void onOpened() {
		//
		// }
		// });
		setSlidingActionBarEnabled(true);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_drawer);
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
			if (mSliding.isSecondaryMenuShowing()) {
				mSliding.showContent();
			} else {
				mSliding.showSecondaryMenu(true);
			}
			return true;
		}
		if (id == android.R.id.home) {
			toggle();
		}
		return super.onOptionsItemSelected(item);
	}

	Fragment getCurrentFragment(String tag) {
		return getSupportFragmentManager().findFragmentByTag(tag);
	}

	@Override
	public void selectMenu(int menuId) {
		Fragment f = null;
		String tag = null;
		Fragment old = null;
		switch (menuId) {
		case MenuFragment.MENU_ID_MAIN:
			// f = new MainFragment();
			tag = TAG_MAIN;
			break;
		case MenuFragment.MENU_ID_ONE:
			old = getCurrentFragment(TAG_ONE);
			if (old == null) {
				tag = TAG_ONE;
				f = new OneFragment();
			}
			break;
		case MenuFragment.MENU_ID_TWO:
			old = getCurrentFragment(TAG_TWO);
			if (old == null) {
				tag = TAG_TWO;
				f = new TwoFragment();
			}
			break;
		case MenuFragment.MENU_ID_THREE:
			old = getCurrentFragment(TAG_THREE);
			if (old == null) {
				tag = TAG_THREE;
				f = new ThreeFragment();
			}
			break;
		}
		if (tag != null) {
			getSupportFragmentManager().popBackStack(null,
					FragmentManager.POP_BACK_STACK_INCLUSIVE);
			if (f != null) {
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.container, f, tag).addToBackStack(null)
						.commit();
			}
		}
		showContent();
	}
}
