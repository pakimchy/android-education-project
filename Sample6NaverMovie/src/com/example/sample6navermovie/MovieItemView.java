package com.example.sample6navermovie;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

public class MovieItemView extends FrameLayout {

	public MovieItemView(Context context) {
		super(context);
		init();
	}

	ImageView iconView;
	TextView titleView;
	TextView directorView;
	RatingBar ratingView;
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_naver_movie, this);
		iconView = (ImageView)findViewById(R.id.image_icon);
		titleView = (TextView)findViewById(R.id.text_title);
		directorView = (TextView)findViewById(R.id.text_director);
		ratingView = (RatingBar)findViewById(R.id.rating_rating);
//		ratingView.setFocusable(false);
	}

	MovieItem item;
	
	public void setMovieItem(MovieItem item) {
		this.item = item;
		titleView.setText(Html.fromHtml(item.title));
		directorView.setText(item.director);
		ratingView.setRating(item.userRating / 2);
	}
	

}
