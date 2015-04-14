package com.example.exampleimagemultiselect;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class ListItemView extends FrameLayout {

	ImageView contentView;
	TextView titleView;
	ImageItem mItem;
	
	public ListItemView(Context context) {
		super(context);
		init();
	}
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.list_item, this);
		contentView = (ImageView)findViewById(R.id.image_content);
		titleView = (TextView)findViewById(R.id.text_title);
	}
	
	public void setImageItem(ImageItem item) {
		mItem = item;
		titleView.setText(item.title);
		ImageLoader.getInstance().displayImage(item.uri.toString(), contentView);
	}

}
