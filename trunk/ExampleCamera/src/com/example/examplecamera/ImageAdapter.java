package com.example.examplecamera;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	

	ArrayList<Bitmap> items = new ArrayList<Bitmap>();
	Context mContext;
	
	public ImageAdapter(Context context) {
		mContext = context;
	}
	
	public void add(Bitmap bm) {
		items.add(bm);
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView iv;
		if (convertView == null) {
			iv = new ImageView(mContext);
			iv.setLayoutParams(new Gallery.LayoutParams(100, 100));
		} else {
			iv = (ImageView)convertView;
		}
		iv.setImageBitmap(items.get(position));
		return iv;
	}

}
