package com.example.sample7viewpager;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyPagerAdapter extends PagerAdapter {

	List<String> list = new ArrayList<String>();
	List<View> mScrappedView = new ArrayList<View>();
	Context mContext;
	public MyPagerAdapter(Context context) {
		mContext = context;
		for (int i = 0; i < 10; i++) {
			list.add("item : " + i);
		}
	}
	
	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		
		TextView tv;
		if (mScrappedView.size() == 0) {
			tv = new TextView(container.getContext());
		} else {
			View v = mScrappedView.remove(0);
			tv = (TextView)v;
		}
		tv.setText(list.get(position));
		container.addView(tv);
		return tv;
	}
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View)object);
		mScrappedView.add((View)object);
	}
	
	@Override
	public float getPageWidth(int position) {
		return 1f;
	}
	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view==object;
	}
	
}
