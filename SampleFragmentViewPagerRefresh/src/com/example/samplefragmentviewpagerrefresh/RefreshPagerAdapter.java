package com.example.samplefragmentviewpagerrefresh;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

public abstract class RefreshPagerAdapter extends FragmentPagerAdapter {

	ArrayList<Fragment> fragmentList = new ArrayList<Fragment>();
	FragmentManager mFm;
	Fragment dummy = new Fragment();

	public RefreshPagerAdapter(FragmentManager fm) {
		super(fm);
		mFm = fm;
	}

	public void clearAdapter() {
		FragmentTransaction ft = mFm.beginTransaction();
		boolean isAction = false;
		for (Fragment fragment : fragmentList) {
			if (fragment != null) {
				String tag = fragment.getTag();
				if (tag != null) {
					Fragment f = mFm.findFragmentByTag(tag);
					if (f != null) {
						ft.remove(f);
						isAction = true;
					}
				}
			}
		}
		if (isAction) {
			ft.commit();
		}
		// mFm.executePendingTransactions();
		fragmentList.clear();
		notifyDataSetChanged();
	}

	@Override
	public int getItemPosition(Object object) {
		Fragment f = (Fragment) object;
		int index = fragmentList.indexOf(f);
		if (index == -1) {
			index = POSITION_NONE;
		} else {
			index = POSITION_UNCHANGED;
		}
		return index;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		Fragment f = (Fragment) super.instantiateItem(container, position);
		if (position < fragmentList.size()) {
			fragmentList.add(position, f);
		} else {
			for (int i = fragmentList.size(); i < position; i++) {
				fragmentList.add(dummy);
			}
			fragmentList.add(f);
		}
		return f;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		Fragment fragment = (Fragment) object;
		Fragment f = mFm.findFragmentByTag(fragment.getTag());
		if (f != null) {
			super.destroyItem(container, position, object);
		}
	}

}
