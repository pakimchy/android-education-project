package com.example.sampleyoutube;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;

import android.content.Context;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

public class NetworkManager {
	private static NetworkManager instance;
	public static NetworkManager getInstance() {
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
	
	Gson gson = new Gson();
	
	public interface OnResultListener<T> {
		public void onSuccess(T result);
		public void onFail(int code);
	}

	public HttpClient getHttpClient() {
		return client.getHttpClient();
	}
	
	private static final String KEY = "AIzaSyA06E0xNLoiOG4pevKrsnVNjz2Uoy9ITvM";
	private static final String URL_YOUTUBE_SEARCH = "https://www.googleapis.com/youtube/v3/search";
	public void getYoutubeSearch(Context context, String keyword, final OnResultListener<YouTubeData> listener) {
		RequestParams params = new RequestParams();
		params.put("key", KEY);
		params.put("type", "video");
		params.put("part", "id,snippet");
		params.put("fields", "items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
		params.put("q", keyword);
		client.get(context, URL_YOUTUBE_SEARCH, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				YouTubeData result = gson.fromJson(responseString, YouTubeData.class);
				listener.onSuccess(result);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}
	
}
