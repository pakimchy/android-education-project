package com.example.examplenetworkmelon;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

public class ImageRequest extends NetworkRequest<Bitmap> {

	String url;
	ImageView imageView;
	ImageLoader mLoader;
	
	public ImageRequest(ImageLoader loader, String url) {
		mLoader = loader;
		this.url = url;
	}
	
	public String getUrlText() {
		return url;
	}
	
	public void setImageView(ImageView imageView) {
		this.imageView = imageView;
	}
	
	public ImageView getImageView() {
		return imageView;
	}
	
	@Override
	public void cancel() {
		super.cancel();
		mLoader.processCancel(this);
	}
	
	@Override
	public URL getURL() throws MalformedURLException {
		return new URL(url);
	}

	@Override
	public Bitmap parse(InputStream is) throws IOException {
		Bitmap bm = BitmapFactory.decodeStream(is);
		return bm;
	}

}
