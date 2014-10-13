package com.example.sample5navermovie;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemView extends FrameLayout {

	public ItemView(Context context) {
		super(context);
		init();
	}

	ImageView iconView;
	TextView titleView;
	TextView actorView;
	MovieItem mItem;
	
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_layout, this);
		iconView = (ImageView)findViewById(R.id.icon);
		titleView = (TextView)findViewById(R.id.title);
		actorView = (TextView)findViewById(R.id.actor);
	}

	public void setMovieItem(MovieItem item) {
		mItem = item;
		titleView.setText(Html.fromHtml(item.title));
		actorView.setText(item.actor);
	}
}
