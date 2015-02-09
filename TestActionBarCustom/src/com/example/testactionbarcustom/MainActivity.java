package com.example.testactionbarcustom;

import java.lang.reflect.Field;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.internal.widget.ToolbarWidgetWrapper;
import android.support.v7.widget.ActionMenuPresenter;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	Handler mHandler = new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setDisplayShowHomeEnabled(false);
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setDisplayShowCustomEnabled(true);
		TextView tv = new TextView(this);
		tv.setBackgroundColor(Color.GREEN);
		tv.setText("Test...");
		actionBar.setBackgroundDrawable(new ColorDrawable(Color.BLUE));
		actionBar.setCustomView(tv, new ActionBar.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

		View decorView = getWindow().getDecorView();
		View container = decorView.findViewById(R.id.action_bar_container);
		Toolbar toolbar = (Toolbar)decorView.findViewById(R.id.action_bar);
		View contextView = decorView.findViewById(R.id.action_context_bar);
		ToolbarWidgetWrapper decorToolbar = (ToolbarWidgetWrapper)toolbar.getWrapper();
		
		try {
			Field mActionMenuPresenterField = decorToolbar.getClass().getDeclaredField("mActionMenuPresenter");
			mActionMenuPresenterField.setAccessible(true);
			ActionMenuPresenter mActionMenuPresenter = new MyActionMenuPresenter(toolbar.getContext());
			mActionMenuPresenterField.set(decorToolbar, mActionMenuPresenter);
		} catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException e) {
			e.printStackTrace();
		}
		
		
		mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				testActionBar();
			}
		}, 100);
	}
	
	private void testActionBar() {
		ActionBar actionBar = getSupportActionBar();
		View decorView = getWindow().getDecorView();
		View container = decorView.findViewById(R.id.action_bar_container);
		Toolbar toolbar = (Toolbar)decorView.findViewById(R.id.action_bar);
		View contextView = decorView.findViewById(R.id.action_context_bar);
		ToolbarWidgetWrapper decorToolbar = (ToolbarWidgetWrapper)toolbar.getWrapper();
		container.setBackgroundColor(Color.RED);
		toolbar.setBackgroundColor(Color.BLUE);
		contextView.setBackgroundColor(Color.YELLOW);
		
		View itemView = findViewById(R.id.action_settings);
		if (itemView != null) {
			LayoutParams params = itemView.getLayoutParams();
//			params.height = 128;
//			itemView.setLayoutParams(params);
		}
		
		View customView = actionBar.getCustomView();
		Log.i("MainActivity" , "customView : " + customView.getLeft());


		
		try {
			Field navButtonViewField = toolbar.getClass().getDeclaredField("mNavButtonView");
			navButtonViewField.setAccessible(true);
			View mNavButtonView = (View) navButtonViewField.get(toolbar);
			Log.i("MainActivity", "NavButtonView : " + mNavButtonView.getMeasuredWidth());
		} catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException e) {
			e.printStackTrace();
		}

		try {
			Field mCollapseButtonViewField = toolbar.getClass().getDeclaredField("mCollapseButtonView");
			mCollapseButtonViewField.setAccessible(true);
			View mCollapseButtonView = (View) mCollapseButtonViewField.get(toolbar);
			if (mCollapseButtonView != null) {
				Log.i("MainActivity", "mCollapseButtonView : " + mCollapseButtonView.getMeasuredWidth());
			}
		} catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException e) {
			e.printStackTrace();
		}

		try {
			Field mCollapseButtonViewField = toolbar.getClass().getDeclaredField("mLogoView");
			mCollapseButtonViewField.setAccessible(true);
			View mCollapseButtonView = (View) mCollapseButtonViewField.get(toolbar);
			if (mCollapseButtonView != null) {
				Log.i("MainActivity", "mLogoView : " + mCollapseButtonView.getMeasuredWidth());
			}
		} catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException e) {
			e.printStackTrace();
		}

		try {
			Field mCollapseButtonViewField = toolbar.getClass().getDeclaredField("mLogoView");
			mCollapseButtonViewField.setAccessible(true);
			View mCollapseButtonView = (View) mCollapseButtonViewField.get(toolbar);
			if (mCollapseButtonView != null) {
				Log.i("MainActivity", "mLogoView : " + mCollapseButtonView.getMeasuredWidth());
			}
		} catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException e) {
			e.printStackTrace();
		}

		try {
			Field mCollapseButtonViewField = toolbar.getClass().getDeclaredField("mTitleTextView");
			mCollapseButtonViewField.setAccessible(true);
			View mCollapseButtonView = (View) mCollapseButtonViewField.get(toolbar);
			if (mCollapseButtonView != null) {
				Log.i("MainActivity", "mTitleTextView : " + mCollapseButtonView.getMeasuredWidth());
			}
		} catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException e) {
			e.printStackTrace();
		}

		try {
			Field mCollapseButtonViewField = toolbar.getClass().getDeclaredField("mSubtitleTextView");
			mCollapseButtonViewField.setAccessible(true);
			View mCollapseButtonView = (View) mCollapseButtonViewField.get(toolbar);
			if (mCollapseButtonView != null) {
				Log.i("MainActivity", "mSubtitleTextView : " + mCollapseButtonView.getMeasuredWidth());
			}
		} catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException e) {
			e.printStackTrace();
		}
		
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
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
