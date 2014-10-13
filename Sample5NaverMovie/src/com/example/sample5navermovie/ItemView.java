package com.example.sample5navermovie;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sample5navermovie.NetworkManager.OnImageListener;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ItemView extends FrameLayout {

	public ItemView(Context context) {
		super(context);
		init();
	}

	ImageView iconView;
	TextView titleView;
	TextView actorView;
	MovieItem mItem;
	ImageLoader mLoader;

	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_layout, this);
		iconView = (ImageView) findViewById(R.id.icon);
		titleView = (TextView) findViewById(R.id.title);
		actorView = (TextView) findViewById(R.id.actor);
		mLoader = ImageLoader.getInstance();
	}

	public void setMovieItem(MovieItem item) {
		mItem = item;
		titleView.setText(Html.fromHtml(item.title));
		actorView.setText(item.actor);
		mLoader.displayImage(item.image, iconView);
//		iconView.setImageResource(R.drawable.ic_launcher);
//		if (item.image != null && !item.image.equals("")) {
//			NetworkManager.getInstance().getNetworkImage(item.image,
//					new OnImageListener() {
//
//						@Override
//						public void onSuccess(String url, Bitmap result) {
//							if (url.equals(mItem.image)) {
//								iconView.setImageBitmap(result);
//							}
//						}
//
//						@Override
//						public void onFail(String url, int code) {
//
//						}
//					});
//		}
	}
}
