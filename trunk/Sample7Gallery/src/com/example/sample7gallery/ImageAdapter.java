package com.example.sample7gallery;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {

	List<Integer> images = new ArrayList<Integer>();
	public ImageAdapter() {
		
	}
	
	public void addAll(List<Integer> imageList) {
		images.addAll(imageList);
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		if (images.size() == 0) {
			return 0;
		} else {
			return Integer.MAX_VALUE;
		}
//		return images.size();
	}

	@Override
	public Object getItem(int position) {
		position = position % images.size();
		return images.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView iv;
		if (convertView == null) {
			Context context = parent.getContext();
			iv = new ImageView(context);
			int imageWidth = context.getResources().getDimensionPixelSize(R.dimen.image_width);
			int imageHeight = context.getResources().getDimensionPixelSize(R.dimen.image_height);
			iv.setLayoutParams(new Gallery.LayoutParams(imageWidth, imageHeight));
			iv.setScaleType(ImageView.ScaleType.FIT_XY);
		} else {
			iv = (ImageView)convertView;
		}
		position = position % images.size();
		iv.setImageResource(images.get(position));
		return iv;
	}

}
