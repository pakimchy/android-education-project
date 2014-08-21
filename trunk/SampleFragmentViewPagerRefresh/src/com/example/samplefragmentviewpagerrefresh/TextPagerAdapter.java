package com.example.samplefragmentviewpagerrefresh;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class TextPagerAdapter extends RefreshPagerAdapter {

	ArrayList<String> mItems = new ArrayList<String>();
	
	public TextPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		Bundle b = new Bundle();
		b.putString(TextFragment.FIELD_DATA, mItems.get(position));
		Fragment f = new TextFragment();
		f.setArguments(b);
		return f;
	}

	@Override
	public int getCount() {
		return mItems.size();
	}
	
	public void clear() {
		mItems.clear();
		clearAdapter();
	}
	
	public void addAll(List<String> items) {
		mItems.addAll(items);
		notifyDataSetChanged();
	}

}
