package com.example.samplenetworknavermovie;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;

public class ImageRequest extends NetworkRequest<Bitmap> {
	
	ImageView imageView;
	String url;
	public ImageRequest(String url) {
		this.url = url;
	}
	
	public String getKey() {
		return "key_"+url.hashCode();
	}

	public void setImageView(ImageView iv) {
		imageView = iv;
	}
	
	public ImageView getImageView() {
		return imageView;
	}
	
	@Override
	public URL getURL() throws MalformedURLException {
		return new URL(url);
	}

	@Override
	public Bitmap doParsing(InputStream is) {
		Bitmap bm = BitmapFactory.decodeStream(is);
		if (bm != null) {
			CacheManager.getInstance().saveCache(getKey(), bm);
		}
		return bm;
	}

	public void setAndSendResult(Bitmap bitmap) {
		result = bitmap;
		sendSuccess();
	}
}
