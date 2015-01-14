package com.example.sample6navermovie;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import android.os.AsyncTask;

import com.begentgroup.xmlparser.XMLParser;

public class NetworkManager {
	private static NetworkManager instance;
	public static NetworkManager getInstnace() {
		if (instance == null) {
			instance = new NetworkManager();
		}
		return instance;
	}
	
	private NetworkManager() {
		
	}
	
	public interface OnResultListener<T> {
		public void onSuccess(T movie);
		public void onFail(int code);
	}
	
	public void getNaverMovie(String keyword, OnResultListener<NaverMovie> listener) {
		NaverMovieTask task = new NaverMovieTask();
		task.setOnResultListener(listener);
		task.execute(keyword);
	}
	
	class NaverMovieTask extends AsyncTask<String, Integer, NaverMovie> {
		OnResultListener<NaverMovie> mListener;
		public void setOnResultListener(OnResultListener<NaverMovie> listener) {
			mListener = listener;
		}
		@Override
		protected NaverMovie doInBackground(String... params) {
			String keyword = params[0];
			if (keyword != null && !keyword.equals("")) {
				try {
					String urlString = "http://openapi.naver.com/search?key=55f1e342c5bce1cac340ebb6032c7d9a&display=10&start=1&target=movie&query=" + URLEncoder.encode(keyword, "utf-8");
					URL url = new URL(urlString);
					HttpURLConnection conn = (HttpURLConnection)url.openConnection();
					int responseCode = conn.getResponseCode();
					if (responseCode == HttpURLConnection.HTTP_OK) {
						InputStream is = conn.getInputStream();
						XMLParser parser = new XMLParser();
						NaverMovie movie = parser.fromXml(is, "channel", NaverMovie.class);
						return movie;
					}
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			}
			return null;
		}
		
		@Override
		protected void onPostExecute(NaverMovie result) {
			super.onPostExecute(result);
			if (result != null) {
				if (mListener != null) {
					mListener.onSuccess(result);
				}
//				for (MovieItem item : result.items) {
//					mAdapter.add(item);
//				}
			}
		}
	}
	
}
