package com.example.sample5slidingmenu;

import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity 
implements MenuFragment.MenuClickListener {

	SlidingMenu sm;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setBehindContentView(R.layout.menu_frame);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new MainFragment()).commit();
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
		switch(menu) {
		case MenuFragment.MENU_MAIN :
			getSupportFragmentManager().beginTransaction().replace(R.id.container, new MainFragment()).commit();
			break;
		case MenuFragment.MENU_ONE :
			getSupportFragmentManager().beginTransaction().replace(R.id.container, new MenuOneFragment()).commit();
			break;
		case MenuFragment.MENU_TWO :
			getSupportFragmentManager().beginTransaction().replace(R.id.container, new MenuTwoFragment()).commit();
			break;
		}
		hideMenu();
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
