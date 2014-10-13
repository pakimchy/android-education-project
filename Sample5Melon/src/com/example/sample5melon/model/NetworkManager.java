package com.example.sample5melon.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;

import android.os.AsyncTask;

public class NetworkManager {
	private static NetworkManager instance;
	
	public static NetworkManager getInstance() {
		if (instance == null) {
			instance = new NetworkManager();
		}
		return instance;
	}
	
	private NetworkManager() {
		
	}
	
	public interface OnResultListener<T> {
		public void onSuccess(T result);
		public void onFail(int code);
	}
	
	public void getMelonChart(int page, int count, OnResultListener<MelonObject> listener) {
		new MyMelonTask(listener).execute(page,count);
	}
	
	public static final String URL_TEXT = "http://apis.skplanetx.com/melon/charts/realtime";
	public static final String APP_KEY = "458a10f5-c07e-34b5-b2bd-4a891e024c2a";
	
	public class MyMelonTask extends AsyncTask<Integer,Integer,MelonObject> {
		OnResultListener<MelonObject> mListener;
		public MyMelonTask(OnResultListener<MelonObject> listener) {
			super();
			mListener = listener;
		}
		@Override
		protected MelonObject doInBackground(Integer... params) {
			int page = params[0];
			int count = params[1];

			String urlText = URL_TEXT + "?version=1&page=" + page + "&count="
					+ count;
			try {
				URL url = new URL(urlText);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "application/json");
				conn.setRequestProperty("appKey", APP_KEY);
				conn.setConnectTimeout(30000);
				conn.setReadTimeout(30000);
				int code = conn.getResponseCode();
				if (code == HttpURLConnection.HTTP_OK) {
					InputStream is = conn.getInputStream();
					Gson gson = new Gson();
					MelonResult result = gson.fromJson(
							new InputStreamReader(is), MelonResult.class);
					return result.melon;
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
		protected void onPostExecute(MelonObject result) {
			super.onPostExecute(result);
			if (result != null) {
				mListener.onSuccess(result);
			} else {
				mListener.onFail(0);
			}
		}
	}
	
	
}
