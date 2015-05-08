package com.example.samplephotoview;

import uk.co.senab.photoview.PhotoViewAttacher;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.LoadedFrom;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;

public class FragmentContent extends Fragment {
	ImageItem item;
	public static final String EXTRA_ITEM = "item";
	ImageLoader mLoader;
	ImageView contentView;
	PhotoViewAttacher mAttacher;
	DisplayImageOptions options;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle b = getArguments();
		if (b != null) {
			item = (ImageItem)b.getSerializable(EXTRA_ITEM);
		}
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
				imageAware.setImageBitmap(bitmap);
				mAttacher.update();
			}
		})
		.build();
		mLoader = ImageLoader.getInstance();
	}
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_item, container, false);
		contentView = (ImageView)view.findViewById(R.id.content);
		mLoader.displayImage(item.thumbnail, contentView, options);
		contentView.setScaleType(ScaleType.CENTER_CROP);
		mAttacher = new PhotoViewAttacher(contentView);
//		mAttacher.setAllowParentInterceptOnEdge(false);
		return view;
	}
}
