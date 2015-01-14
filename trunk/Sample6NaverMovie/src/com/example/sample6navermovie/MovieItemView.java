package com.example.sample6navermovie;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.sample6navermovie.ImageManager.OnImageListener;
import com.nostra13.universalimageloader.core.ImageLoader;

public class MovieItemView extends FrameLayout {

	public MovieItemView(Context context) {
		super(context);
		init();
	}

	ImageView iconView;
	TextView titleView;
	TextView directorView;
	RatingBar ratingView;
	ImageLoader mLoader;
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_naver_movie, this);
		iconView = (ImageView)findViewById(R.id.image_icon);
		titleView = (TextView)findViewById(R.id.text_title);
		directorView = (TextView)findViewById(R.id.text_director);
		ratingView = (RatingBar)findViewById(R.id.rating_rating);
		mLoader = ImageLoader.getInstance();
//		ratingView.setFocusable(false);
	}

	MovieItem mItem;
	
	public void setMovieItem(MovieItem item) {
		mItem = item;
		titleView.setText(Html.fromHtml(item.title));
		directorView.setText(item.director);
		ratingView.setRating(item.userRating / 2);
		mLoader.displayImage(item.image, iconView);
//		if (item.image != null && !item.image.equals("")) {
//			ImageManager.getInstance().getImage(item.image, new OnImageListener() {
//				
//				@Override
//				public void onLoadImage(String url, Bitmap bitmap) {
//					if (url.equals(mItem.image)) {
//						iconView.setImageBitmap(bitmap);
//					}
//				}
//			});
//			iconView.setImageResource(R.drawable.ic_stub);
//		} else {
//			iconView.setImageResource(R.drawable.ic_empty);
//		}
	}
	

}
