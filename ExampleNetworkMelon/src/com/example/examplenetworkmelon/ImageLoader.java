package com.example.examplenetworkmelon;

import java.util.HashMap;
import java.util.Map;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.example.examplenetworkmelon.NetworkManager.OnResultListener;

public class ImageLoader {
	private static ImageLoader instance;
	public static ImageLoader getInstance() {
		if (instance == null) {
			instance = new ImageLoader();
		}
		return instance;
	}
	
	private ImageLoader() {
		
	}
	
	Map<ImageView, ImageRequest> mImageRequestMap = new HashMap<ImageView,ImageRequest>();
	
	public void displayImage(String url, ImageView imageView) {
		ImageRequest old = mImageRequestMap.get(imageView);
		if (old != null) {
			old.cancel();
		}
		if (url == null || url.equals("")) {
			imageView.setImageResource(R.drawable.ic_empty);
			return;
		}
		Bitmap bitmap = ImageCache.getInstance().getBitmapFromMemCache(url);
		if (bitmap == null) {
			bitmap = ImageCache.getInstance().getBitmapFromDiskCache(url);
			ImageCache.getInstance().addBitmapToCache(url, bitmap);
		}
		if (bitmap != null) {
			imageView.setImageBitmap(bitmap);
			return;
		}
		ImageRequest request = new ImageRequest(this, url);
		request.setImageView(imageView);
		mImageRequestMap.put(imageView, request);
		NetworkManager.getInstance().getNetworkData(imageView.getContext(), request, defaultListener);
		imageView.setImageResource(R.drawable.ic_stub);
	}
	
	OnResultListener<Bitmap> defaultListener = new OnResultListener<Bitmap>() {

		@Override
		public void onSuccess(NetworkRequest<Bitmap> request, Bitmap result) {
			ImageRequest ir = (ImageRequest)request;
			ImageRequest irMap = mImageRequestMap.get(ir.getImageView());
			if (irMap == ir) {
				ir.getImageView().setImageBitmap(result);
				ImageCache.getInstance().addBitmapToCache(ir.getUrlText(), result);
				mImageRequestMap.remove(ir.getImageView());
			}
		}

		@Override
		public void onFail(NetworkRequest<Bitmap> request, int code) {
			ImageRequest ir = (ImageRequest)request;
			ImageRequest irMap = mImageRequestMap.get(ir.getImageView());
			if (irMap == ir) {
				ir.getImageView().setImageResource(R.drawable.ic_error);
				mImageRequestMap.remove(ir.getImageView());
			}
		}
		
	};
	
	public void processCancel(ImageRequest imageRequest) {
		ImageRequest ir = mImageRequestMap.get(imageRequest.getImageView());
		if (ir == imageRequest) {
			mImageRequestMap.remove(imageRequest.getImageView());
		}
	}
}
