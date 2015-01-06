package com.example.sample6viewpagerfragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class MyAdapter extends FragmentStatePagerAdapter {

	public MyAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		TextFragment tf = new TextFragment();
		Bundle b = new Bundle();
		b.putInt(TextFragment.EXTRA_POSITION, position);
		tf.setArguments(b);
		return tf;
	}

	@Override
	public int getCount() {
		return 5;
	}

}
