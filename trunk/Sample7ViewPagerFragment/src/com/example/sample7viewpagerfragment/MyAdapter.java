package com.example.sample7viewpagerfragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class MyAdapter extends FragmentStatePagerAdapter {

	int FRAGMENT_COUNT = 3;

	public MyAdapter(FragmentManager fm) {
		super(fm);
	}
	
	public void setFragment() {
		FRAGMENT_COUNT = 4;
		notifyDataSetChanged();
	}

	@Override
	public Fragment getItem(int position) {
		Fragment f = null;
		switch (position) {
		case 0:
			f = new OneFragment();
			break;
		case 1:
			f = new TwoFragment();
			break;
		case 2:
			if (FRAGMENT_COUNT == 3) {
				f = new ThreeFragment();
			} else {
				f = new FourFragment();
			}
			break;
		case 3:
		default:
			f = new ThreeFragment();
			break;
		}
		return f;
	}

	@Override
	public int getCount() {
		return FRAGMENT_COUNT;
	}

}
