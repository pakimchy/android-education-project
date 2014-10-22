package com.example.sample5googlemap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
	
	private AsyncHttpClient client;
	
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

	public interface OnResultListener<T> {
		public void onSuccess(T result);
		public void onFail(int code);
	}
	
	public static final String URL_TEXT = "https://apis.skplanetx.com/tmap/pois";
	public static final String APP_KEY = "458a10f5-c07e-34b5-b2bd-4a891e024c2a";
	
	Header[] headers = new Header[] {
		new BasicHeader("Accept", "application/json"),
		new BasicHeader("appKey", APP_KEY)
	};

	public void getTMapPOI(Context context, String keyword, final OnResultListener<POIResult> listener) {
		RequestParams params = new RequestParams();
		params.put("version", "1");
		params.put("resCoordType" , "WGS84GEO");
		params.put("reqCoordType", "WGS84GEO");
		params.put("searchKeyword", keyword);
		
		client.get(context, URL_TEXT, headers, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				Gson gson = new Gson();
				POIResult result = gson.fromJson(responseString, POIResult.class);
				listener.onSuccess(result);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
		
	}
	
	private static final String URL_ROUTE = "https://apis.skplanetx.com/tmap/routes";
	
	public void searchRoute(Context context, double startLat, double startLng, double endLat, double endLng, final OnResultListener<CarRouteInfo> listener) {
		RequestParams params = new RequestParams();
		params.put("version", "1");
		params.put("resCoordType", "WGS84GEO");
		params.put("reqCoordType", "WGS84GEO");
		params.put("startX", ""+startLng);
		params.put("startY", ""+startLat);
		params.put("endX", ""+endLng);
		params.put("endY", ""+endLat);
		client.post(context, URL_ROUTE, headers, params, null ,new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				Gson gson = new GsonBuilder().registerTypeAdapter(Geometry.class, new GeometryDeserializer()).create();
				CarRouteInfo result = gson.fromJson(responseString, CarRouteInfo.class);
				listener.onSuccess(result);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}
	
	public void uploadMapFile(Context context, String title, File file, File file2, final OnResultListener<String> listener) {
		RequestParams params = new RequestParams();
		params.put("title", title);
		try {
			params.put("myfile", file);
			params.put("myfile", file2);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		client.post(context,  "http://...." , params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				listener.onSuccess(responseString);
			}
			
			@Override
			public void onProgress(int bytesWritten, int totalSize) {
				super.onProgress(bytesWritten, totalSize);
				
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}
	
}
