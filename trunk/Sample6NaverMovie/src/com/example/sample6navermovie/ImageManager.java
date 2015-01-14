package com.example.sample6navermovie;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

public class ImageManager {
	private static ImageManager instance;

	public static ImageManager getInstance() {
		if (instance == null) {
			instance = new ImageManager();
		}
		return instance;
	}

	Bitmap failImage;

	Handler mHandler = new Handler(Looper.getMainLooper());
	
	private ImageManager() {
		Context context = MyApplication.getContext();
		failImage = ((BitmapDrawable) context.getResources().getDrawable(
				R.drawable.ic_error)).getBitmap();
	}

	public interface OnImageListener {
		public void onLoadImage(String url, Bitmap bitmap);
	}

	public void getImage(String url, OnImageListener listener) {
		new Thread(new DownloadRunnable(url, listener)).start();
//		ImageDownloadTask task = new ImageDownloadTask();
//		task.setOnImageListener(listener);
//		task.execute(url);
	}
	
	class DownloadRunnable implements Runnable {
		String mUrl;
		OnImageListener mListener;
		
		public DownloadRunnable(String url, OnImageListener listener) {
			mUrl = url;
			mListener = listener;
		}
		
		@Override
		public void run() {
			try {
				URL url = new URL(mUrl);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setConnectTimeout(30000);
				conn.setReadTimeout(30000);
				int responseCode = conn.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {
					InputStream is = conn.getInputStream();
					final Bitmap bm = BitmapFactory.decodeStream(is);
					mHandler.post(new Runnable() {
						
						@Override
						public void run() {
							if (mListener != null) {
								mListener.onLoadImage(mUrl, bm);
							}							
						}
					});
					return;
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			mHandler.post(new Runnable() {
				
				@Override
				public void run() {
					if (mListener != null) {
						mListener.onLoadImage(mUrl, failImage);
					}
				}
			});
			return;
		}
	}

	class ImageDownloadTask extends AsyncTask<String, Integer, Bitmap> {
		String mUrl;
		OnImageListener mListener;

		public void setOnImageListener(OnImageListener listener) {
			mListener = listener;
		}

		@Override
		protected Bitmap doInBackground(String... params) {
			String urlString = params[0];
			mUrl = urlString;
			try {
				URL url = new URL(urlString);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setConnectTimeout(30000);
				conn.setReadTimeout(30000);
				int responseCode = conn.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {
					InputStream is = conn.getInputStream();
					Bitmap bm = BitmapFactory.decodeStream(is);
					return bm;
				}
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Bitmap result) {
			super.onPostExecute(result);
			if (result != null) {
				if (mListener != null) {
					mListener.onLoadImage(mUrl, result);
				}
			} else {
				if (mListener != null) {
					mListener.onLoadImage(mUrl, failImage);
				}
			}
		}
	}

}
