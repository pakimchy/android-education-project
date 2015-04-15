package com.example.sample7market;

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
	

	public static final String SERVER = "http://apis.skplanetx.com";
	public static final String SEARCH_URL = SERVER + "/11st/common/products";
	
	public void searchProduct(Context context, String keyword, int page, int count, final OnResultListener<ProductList> listener) {
		RequestParams params = new RequestParams();
		params.put("searchKeyword", keyword);
		params.put("page", ""+page);
		params.put("count", ""+count);
		params.put("version", "1");
		
		Header[] headers = new Header[2];
		headers[0] = new BasicHeader("Accept", "application/json");
		headers[1] = new BasicHeader("appKey", "48c9c3df-13a2-34cd-ba58-4cc714287efe");
		
		client.get(context, SEARCH_URL, headers, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				Gson gson = new Gson();
				ProductSearchResult result = gson.fromJson(responseString, ProductSearchResult.class);
				ProductList list = result.productSearchResponse.products;
				for (Product p : list.productList) {
					if (p.productImage != null) {
						p.productImage = p.productImage.replace("\\\\", "");
					}
				}
				listener.onSuccess(list);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
		
	}
	
	public static final String DETAIL_URL = SERVER + "/11st/common/products/%s";
	
	public void getProductDetail(Context context, int productCode, final OnResultListener<ProductDetail> listener) {
		String url = String.format(DETAIL_URL, ""+productCode);
		RequestParams params = new RequestParams();
		params.put("version", "1");
		
		Header[] headers = new Header[2];
		headers[0] = new BasicHeader("Accept", "application/json");
		headers[1] = new BasicHeader("appKey", "48c9c3df-13a2-34cd-ba58-4cc714287efe");
		
		client.get(context, url, headers, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				Gson gson = new Gson();
				ProductInfoResult result = gson.fromJson(responseString, ProductInfoResult.class);
				listener.onSuccess(result.productInfo.product);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
		
	}
}
