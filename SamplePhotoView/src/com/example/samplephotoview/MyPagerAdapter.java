package com.example.samplephotoview;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class MyPagerAdapter extends FragmentPagerAdapter {

	List<ImageItem> items = new ArrayList<ImageItem>();
	public MyPagerAdapter(FragmentManager fm, List<ImageItem> items) {
		super(fm);
		this.items.addAll(items);
	}

	@Override
	public Fragment getItem(int position) {
		FragmentContent f = new FragmentContent();
		Bundle b = new Bundle();
		b.putSerializable(FragmentContent.EXTRA_ITEM, items.get(position));
		f.setArguments(b);
		return f;
	}

	@Override
	public int getCount() {
		return items.size();
	}

}
