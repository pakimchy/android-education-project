package com.example.sampl6viewpager;

import java.util.ArrayList;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyPagerAdapter extends PagerAdapter {

	Context mContext;
	ArrayList<String> items = new ArrayList<String>();

	public MyPagerAdapter(Context context) {
		mContext = context;
	}

	ArrayList<TextView> scrapViewList = new ArrayList<TextView>();

	public void add(String text) {
		items.add(text);
		notifyDataSetChanged();
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		TextView tv;
		if (scrapViewList.size() == 0) {
			tv = new TextView(mContext);
		} else {
			tv = scrapViewList.remove(0);
		}
		tv.setText("position : " + position);
		container.addView(tv);
		return tv;
	}

	@Override
	public float getPageWidth(int position) {
		return 0.333f;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		View v = (View) object;
		container.removeView(v);
		scrapViewList.add((TextView) v);
	}

	@Override
	public int getCount() {
		return 5;
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

}
