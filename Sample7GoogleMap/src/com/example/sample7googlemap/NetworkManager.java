package com.example.sample7googlemap;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.apache.http.Header;
import org.apache.http.client.HttpClient;
import org.apache.http.message.BasicHeader;

import android.content.Context;

import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.MySSLSocketFactory;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

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
		public void onSuccess(T result);
		public void onFail(int code);
	}
	
	private static final String SERVER = "https://apis.skplanetx.com";
	private static final String URL_POI = SERVER+"/tmap/pois";
	
	public void searchPOI(Context context, String keyword,
			final OnResultListener<SearchPoiResult> listener) {
		RequestParams params = new RequestParams();
		params.put("version", "1");
		params.put("searchKeyword", keyword);
		params.put("resCoordType", "WGS84GEO");
		params.put("reqCoordType", "WGS84GEO");
		
		Header[] headers = new Header[2];
		headers[0] = new BasicHeader("Accept", "application/json");
		headers[1] = new BasicHeader("appKey", "458a10f5-c07e-34b5-b2bd-4a891e024c2a");
		
		client.get(context, URL_POI, headers, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				Gson gson = new Gson();
				SearchPoiResult result = gson.fromJson(responseString, SearchPoiResult.class);
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
