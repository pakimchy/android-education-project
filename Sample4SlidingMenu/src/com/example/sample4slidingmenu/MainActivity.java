package com.example.sample4slidingmenu;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnCloseListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenListener;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenedListener;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity {

	ImageView dimImage;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setBehindContentView(R.layout.menu_frame);
		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.container, new MainFragment()).commit();
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.menu_container, new MenuFragment()).commit();
		}
		dimImage = (ImageView)findViewById(R.id.dim_image);
		SlidingMenu sm = getSlidingMenu();
		sm.setMode(SlidingMenu.LEFT_RIGHT);
		sm.setSecondaryMenu(R.layout.right_menu);
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);

		sm.setOnOpenListener(new OnOpenListener() {
			
			@Override
			public void onOpen() {
				dimImage.setVisibility(View.VISIBLE);
			}
		});
		sm.setOnCloseListener(new OnCloseListener() {
			
			@Override
			public void onClose() {
				dimImage.setVisibility(View.GONE);
			}
		});
		getSupportActionBar().setHomeButtonEnabled(true);
		setSlidingActionBarEnabled(false);
	}
	
	
	public void switchTwoFragment() {
		getSupportFragmentManager().beginTransaction().replace(R.id.container, new TwoFragment()).commit();
		showContent();
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId()) {
		case android.R.id.home :
			toggle();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
