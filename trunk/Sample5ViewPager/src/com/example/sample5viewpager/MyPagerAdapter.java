package com.example.sample5viewpager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyPagerAdapter extends FragmentPagerAdapter {

	public MyPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		FragmentOne f = new FragmentOne();
		Bundle b = new Bundle();
		b.putString(FragmentOne.PARAM_MESSAGE, "position : " + position);
		
		f.setArguments(b);
		
		return f;
	}

	@Override
	public int getCount() {
		return 3;
	}

}
