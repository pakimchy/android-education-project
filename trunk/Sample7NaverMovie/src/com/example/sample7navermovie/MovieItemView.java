package com.example.sample7navermovie;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

public class MovieItemView extends FrameLayout {

	public MovieItemView(Context context) {
		super(context);
		init();
	}
	
	ImageView iconView;
	TextView titleView;
	MovieItem mItem;
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_movie, this);
		iconView = (ImageView)findViewById(R.id.image_icon);
		titleView = (TextView)findViewById(R.id.text_title);
	}
	
	public void setMovieItem(MovieItem item) {
		mItem = item;
		titleView.setText(Html.fromHtml(item.title));
		ImageLoader.getInstance().displayImage(item.image, iconView);
	}

}
