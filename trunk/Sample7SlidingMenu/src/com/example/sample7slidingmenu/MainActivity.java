package com.example.sample7slidingmenu;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;


public class MainActivity extends SlidingFragmentActivity {

	SlidingMenu mSliding;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setBehindContentView(R.layout.left_menu_layout);
        mSliding = getSlidingMenu();
        mSliding.setBehindOffset(100);
        mSliding.setShadowWidthRes(R.dimen.shadow_width);
        mSliding.setShadowDrawable(R.drawable.shadow);
        mSliding.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        mSliding.setSecondaryMenu(R.layout.right_menu_layout);
        mSliding.setSecondaryShadowDrawable(R.drawable.shadowright);
        mSliding.setMode(SlidingMenu.LEFT_RIGHT);
        setSlidingActionBarEnabled(false);
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
}
