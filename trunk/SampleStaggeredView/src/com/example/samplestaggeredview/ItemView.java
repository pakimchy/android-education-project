package com.example.samplestaggeredview;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class ItemView extends FrameLayout {

	public ItemView(Context context) {
		super(context);
		init();
	}
	
	ImageView iconView;
	TextView titleView;
	ImageItem mItem;
	ImageLoader mLoader;
	int imageWidth;
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_layout, this);
		iconView = (ImageView)findViewById(R.id.image_icon);
		titleView = (TextView)findViewById(R.id.text_title);
		imageWidth = getResources().getDimensionPixelSize(R.dimen.item_width);
		mLoader = ImageLoader.getInstance();
	}
	
	public void setImageItem(ImageItem item) {
		mItem = item;
		titleView.setText(Html.fromHtml(item.title));
		
		int height = (imageWidth * item.sizeheight) / item.sizewidth;
		ViewGroup.LayoutParams params = iconView.getLayoutParams();
		params.height = height;
		params.width = imageWidth;
		iconView.setLayoutParams(params);
		
		mLoader.displayImage(item.thumbnail, iconView);
	}

}
