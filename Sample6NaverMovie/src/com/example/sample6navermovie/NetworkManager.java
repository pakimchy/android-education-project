package com.example.sample6navermovie;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;

import android.content.Context;
import android.os.AsyncTask;

import com.begentgroup.xmlparser.XMLParser;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

public class NetworkManager {
	private static NetworkManager instance;
	public static NetworkManager getInstnace() {
		if (instance == null) {
			instance = new NetworkManager();
		}
		return instance;
	}
	
	AsyncHttpClient client;
	private NetworkManager() {
		
		try {
			KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
			trustStore.load(null, null);
			MySSLSocketFactory socketFactory = new MySSLSocketFactory(trustStore);
			socketFactory.setHostnameVerifier(MySSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			client = new AsyncHttpClient();
			client.setSSLSocketFactory(socketFactory);			
			client.setCookieStore(new PersistentCookieStore(MyApplication.getContext()));
			client.setTimeout(30000);
		} catch (KeyStoreException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (CertificateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (UnrecoverableKeyException e) {
			e.printStackTrace();
		}
	}
	
	public HttpClient getHttpClient() {
		return client.getHttpClient();
	}
	
	public interface OnResultListener<T> {
		public void onSuccess(T movie);
		public void onFail(int code);
	}
	
	public static final String SERVER = "http://openapi.naver.com";
	public static final String MOVIE_URL = SERVER + "/search";
	public void getNaverMovie(Context context, String keyword, int start, final OnResultListener<NaverMovie> listener) {
		RequestParams params = new RequestParams();
		params.put("key", "55f1e342c5bce1cac340ebb6032c7d9a");
		params.put("display", "100");
		params.put("start", ""+start);
		params.put("target", "movie");
		params.put("query", keyword);
		
		client.get(context, MOVIE_URL, params, new AsyncHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
				InputStream is = new ByteArrayInputStream(responseBody);
				XMLParser parser = new XMLParser();
				NaverMovie movie = parser.fromXml(is, "channel", NaverMovie.class);
				listener.onSuccess(movie);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					byte[] responseBody, Throwable error) {
				listener.onFail(statusCode);
			}
		});
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
					String urlString = "http://openapi.naver.com/search?key=55f1e342c5bce1cac340ebb6032c7d9a&display=100&start=1&target=movie&query=" + URLEncoder.encode(keyword, "utf-8");
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
