package com.example.samplenetworknavermovie;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageRequest extends NetworkRequest<Bitmap> {
	
	String url;
	public ImageRequest(String url) {
		this.url = url;
	}

	@Override
	public URL getURL() throws MalformedURLException {
		return new URL(url);
	}

	@Override
	public Bitmap doParsing(InputStream is) {
		Bitmap bm = BitmapFactory.decodeStream(is);
		return bm;
	}

}
