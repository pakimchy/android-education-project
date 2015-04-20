package com.example.sample7videorecorder;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class MyAdapter extends BaseAdapter {

	List<Bitmap> items = new ArrayList<Bitmap>();
	
	public MyAdapter() {
		
	}
	
	public void add(Bitmap bitmap) {
		items.add(bitmap);
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
		ImageView imageView;
		if (convertView == null) {
			imageView = new ImageView(parent.getContext());
			imageView.setLayoutParams(new Gallery.LayoutParams(200, 200));
		} else {
			imageView = (ImageView)convertView;
		}
		imageView.setImageBitmap(items.get(position));
		return imageView;
	}

}
