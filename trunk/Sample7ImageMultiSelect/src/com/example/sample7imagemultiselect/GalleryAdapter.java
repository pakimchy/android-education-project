package com.example.sample7imagemultiselect;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class GalleryAdapter extends BaseAdapter {
	List<ImageItem> items = new ArrayList<ImageItem>();
	public GalleryAdapter() {
		
	}
	
	public void addAll(List<ImageItem> items) {
		this.items.addAll(items);
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
		ImageView view;
		if (convertView == null) {
			view = new ImageView(parent.getContext());
			view.setLayoutParams(new Gallery.LayoutParams(300, 300));
		} else {
			view = (ImageView)convertView;
		}
		ImageItem item = items.get(position);
		Uri uri = Uri.fromFile(new File(item.path));
		ImageLoader.getInstance().displayImage(uri.toString(), view);
		return view;
	}

}
