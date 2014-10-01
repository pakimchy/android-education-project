package com.example.sample4staggeredgridview;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.Checkable;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemView extends FrameLayout implements Checkable {

	public ItemView(Context context) {
		super(context);
		init();
	}

	DisplayImageOptions options;
	int imageWidth;
	ImageView iconView;
	TextView urlView;
	ImageLoader mLoader;
	private void init() {
		LayoutInflater.from(getContext()).inflate(R.layout.item_layout, this);
		setPadding(20, 20, 20, 20);
		iconView = (ImageView)findViewById(R.id.image_view);
		urlView = (TextView)findViewById(R.id.url_view);
		mLoader = ImageLoader.getInstance();
		imageWidth = (int)getContext().getResources().getDimension(R.dimen.image_width);
		options = new DisplayImageOptions.Builder()
		.showImageOnLoading(R.drawable.ic_stub)
		.showImageForEmptyUri(R.drawable.ic_empty)
		.showImageOnFail(R.drawable.ic_error)
		.cacheInMemory(true)
		.cacheOnDisc(true)
		.considerExifParams(true)
		.displayer(new BitmapDisplayer() {
			
			@Override
			public void display(Bitmap bitmap, ImageAware imageAware,
					LoadedFrom loadedFrom) {
				int imageHeight = bitmap.getHeight() * imageWidth / bitmap.getWidth();
				Bitmap bm = Bitmap.createScaledBitmap(bitmap, imageWidth, imageHeight, false);
//				bitmap.recycle();
				imageAware.setImageBitmap(bm);
			}
		})
		.build();
		drawChecked();
	}
	
	public void setUrl(String url) {
		urlView.setText(url);
		mLoader.displayImage(url, iconView, options);
	}

	boolean isChecked = false;
	
	private void drawChecked() {
		if (isChecked) {
			setBackgroundColor(Color.DKGRAY);
		} else {
			setBackgroundColor(Color.WHITE);
		}
	}
	@Override
	public void setChecked(boolean checked) {
		if (isChecked != checked) {
			isChecked = checked;
			drawChecked();
		}
	}

	@Override
	public boolean isChecked() {
		return isChecked;
	}

	@Override
	public void toggle() {
		setChecked(!isChecked);
	}

}
