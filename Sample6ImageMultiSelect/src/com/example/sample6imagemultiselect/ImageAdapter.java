package com.example.sample6imagemultiselect;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Images.Thumbnails;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	Context mContext;
	ArrayList<Uri> items = new ArrayList<Uri>();
	public ImageAdapter(Context context) {
		mContext = context;
	}
	
	public void addAll(Uri[] uris) {
		items.addAll(Arrays.asList(uris));
		notifyDataSetChanged();
	}
	
	public void add(Uri uri) {
		items.add(uri);
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
		Uri uri = items.get(position);
		long id = ContentUris.parseId(uri);
		return id;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView iv;
		if (convertView == null) {
			iv = new ImageView(mContext);
			iv.setLayoutParams(new Gallery.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		} else {
			iv = (ImageView)convertView;
		}
		Bitmap bm = Images.Thumbnails.getThumbnail(mContext.getContentResolver(), getItemId(position), Thumbnails.MINI_KIND, null);
		iv.setImageBitmap(bm);
		return iv;
	}

}
