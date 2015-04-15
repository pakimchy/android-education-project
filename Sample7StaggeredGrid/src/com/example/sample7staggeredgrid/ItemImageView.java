package com.example.sample7staggeredgrid;

import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemImageView extends FrameLayout {

	public ItemImageView(Context context) {
		super(context);
		init();
	}
	
	ImageView thumbnailView;
	TextView titleView;
	float width;
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_image, this);
		thumbnailView = (ImageView)findViewById(R.id.image_thumbnail);
		titleView = (TextView)findViewById(R.id.text_title);
		width = getResources().getDimensionPixelSize(R.dimen.image_width);
	}
	
	public void setImageItem(ImageItem item) {
		titleView.setText(item.title);
		float height = width * item.sizeheight / item.sizewidth;
		ViewGroup.LayoutParams params = thumbnailView.getLayoutParams();
		params.width = (int)width;
		params.height = (int)height;
		thumbnailView.setLayoutParams(params);
		ImageLoader.getInstance().displayImage(item.thumbnail, thumbnailView);
	}

}
