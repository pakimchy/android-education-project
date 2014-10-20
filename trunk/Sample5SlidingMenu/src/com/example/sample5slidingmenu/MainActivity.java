package com.example.sample5slidingmenu;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity implements
		MenuFragment.MenuClickListener {

	private static final String TAG_MENU_MAIN = "menu";
	private static final String TAG_MENU_ONE = "one";
	private static final String TAG_MENU_TWO = "two";

	SlidingMenu sm;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setBehindContentView(R.layout.menu_frame);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new MainFragment(), TAG_MENU_MAIN)
					.commit();
			getSupportFragmentManager().beginTransaction()
					.add(R.id.menu_container, new MenuFragment()).commit();
		}

		sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		getSupportActionBar().setHomeButtonEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
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
		} else if (id == android.R.id.home) {
			toggle();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void selectMenu(int menu) {
		switch (menu) {
		case MenuFragment.MENU_MAIN:

			emptyBackStack();
//			Fragment f = getSupportFragmentManager().findFragmentByTag(
//					TAG_MENU_MAIN);
//			if (f == null) {
//				getSupportFragmentManager()
//						.beginTransaction()
//						.replace(R.id.container, new MainFragment(),
//								TAG_MENU_MAIN).commit();
//			}
			break;
		case MenuFragment.MENU_ONE:
			Fragment f1 = getSupportFragmentManager().findFragmentByTag(
					TAG_MENU_ONE);
			if (f1 == null) {
				emptyBackStack();
				getSupportFragmentManager()
						.beginTransaction()
						.replace(R.id.container, new MenuOneFragment(),
								TAG_MENU_ONE).addToBackStack(null).commit();
			}
			break;
		case MenuFragment.MENU_TWO:
			Fragment f2 = getSupportFragmentManager().findFragmentByTag(
					TAG_MENU_TWO);
			if (f2 == null) {
				emptyBackStack();
				getSupportFragmentManager()
						.beginTransaction()
						.replace(R.id.container, new MenuTwoFragment(),
								TAG_MENU_TWO).addToBackStack(null).commit();
			}
			break;
		}
		hideMenu();
	}
	
	private void emptyBackStack() {
		while(getSupportFragmentManager().getBackStackEntryCount() > 0) {
			getSupportFragmentManager().popBackStackImmediate();
		}
	}

	Handler mHandler = new Handler();

	public void hideMenu() {
		mHandler.postDelayed(hideMenuRunnable, 100);
	}

	Runnable hideMenuRunnable = new Runnable() {

		@Override
		public void run() {
			showContent();
		}
	};
}
