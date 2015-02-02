package com.example.sampleyoutube;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;

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
	

	public static final String OAUTH_LOGIN_URL = "https://accounts.google.com/o/oauth2/auth";
	public static final String PARAM_FORMAT = "redirect_uri=%s&response_type=%s&client_id=%s&scope=%s&approval_prompt=%s&access_type=%s&pageId=%s";
	public static final String PARAM_REDIRECT = "http://localhost";
	public static final String PARAM_RESPONSE_TYPE = "code";
	public static final String PARAM_CLIENT_ID = "972646024433.apps.googleusercontent.com";
	public static final String PARAM_CLIENT_SECRET = "By5eGC_7t7TkSd-tipfP3KdN";
	public static final String PARAM_SCOPE = "https://www.googleapis.com/auth/youtubepartner";
	public static final String PARAM_APPROVAL_PROMPT = "force";
	public static final String PARAM_ACCESS_TYPE = "offline";
	public static final String PARAM_PAGEID = "none";
	public static final String PARAM_GRANT_TYPE_CODE = "authorization_code";
	public static final String PARAM_GRANT_TYPE_REFRESH_TOKEN = "refresh_token";
	
	public String getOAuthLoginURL() {
		String url = null;
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("redirect_uri", PARAM_REDIRECT));
		params.add(new BasicNameValuePair("response_type", PARAM_RESPONSE_TYPE));
		params.add(new BasicNameValuePair("client_id", PARAM_CLIENT_ID));
		params.add(new BasicNameValuePair("scope", PARAM_SCOPE));
		params.add(new BasicNameValuePair("approval_prompt", PARAM_APPROVAL_PROMPT));
		params.add(new BasicNameValuePair("access_type", PARAM_ACCESS_TYPE));
		params.add(new BasicNameValuePair("pageId", PARAM_PAGEID));
		String queryParameter = URLEncodedUtils.format(params, "UTF-8");
		url = OAUTH_LOGIN_URL + "?" + queryParameter;
		return url;
	}

	Gson gson = new Gson();
//	AccessToken mToken;
	
	public static final String OAUTH_TOKEN_URL = "https://accounts.google.com/o/oauth2/token";
	public void getAccessToken(Context context, String code, final OnResultListener<AccessToken> listener) {
		RequestParams params = new RequestParams();
		params.put("code", code);
		params.put("redirect_uri", PARAM_REDIRECT);
		params.put("client_id", PARAM_CLIENT_ID);
		params.put("client_secret", PARAM_CLIENT_SECRET);
		params.put("grant_type", PARAM_GRANT_TYPE_CODE);
		
		client.post(context, OAUTH_TOKEN_URL, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				AccessToken token = gson.fromJson(responseString, AccessToken.class);
				token.setExpiresTime();
				PropertyManager.getInstance().setAccessToken(token);
				listener.onSuccess(token);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}
	
	public Header getAuthorizeHeader() {
		AccessToken token = PropertyManager.getInstance().getAccessToken();
		if (token != null && token.isValidToken() && !token.isExired()) {
			Header header = new BasicHeader("Authorization", token.tokenType + " " + token.accessToken);
			return header;
		}
		return null;
	}
	public static final String YOUTUBE_PLAYLIST = "https://www.googleapis.com/youtube/v3/playlists?part=id,snippet&mine=true";
	public void getYoutubePlayList(final Context context, final OnResultListener<String> listener) {
		Header[] headers = new Header[1];
		headers[0] = getAuthorizeHeader();
		if (headers[0] != null) {
			client.get(context, YOUTUBE_PLAYLIST,headers, null, new TextHttpResponseHandler() {
				
				@Override
				public void onSuccess(int statusCode, Header[] headers,
						String responseString) {
					listener.onSuccess(responseString);
				}
				
				@Override
				public void onFailure(int statusCode, Header[] headers,
						String responseString, Throwable throwable) {
					if (statusCode == HttpURLConnection.HTTP_UNAUTHORIZED) {
						getRefreshToken(context, new OnResultListener<AccessToken>() {
							@Override
							public void onSuccess(AccessToken result) {
								getYoutubePlayList(context, listener);
							}
							@Override
							public void onFail(int code) {
								listener.onFail(code);
							}
						});
					}
				}
			} );
		}
	}
	
	public void getRefreshToken(Context context, final OnResultListener<AccessToken> listener) {
		AccessToken token = PropertyManager.getInstance().getAccessToken();
		RequestParams params = new RequestParams();
		params.put("client_secret", PARAM_CLIENT_SECRET);
		params.put("grant_type", PARAM_GRANT_TYPE_REFRESH_TOKEN);
		params.put("refresh_token", token.refreshToken);
		params.put("client_id", PARAM_CLIENT_ID);
		client.post(context, OAUTH_TOKEN_URL, params, new TextHttpResponseHandler() {
			
			@Override
			public void onSuccess(int statusCode, Header[] headers,
					String responseString) {
				AccessToken savedToken = PropertyManager.getInstance().getAccessToken();
				AccessToken token = gson.fromJson(responseString, AccessToken.class);
				token.refreshToken = savedToken.refreshToken;
				token.setExpiresTime();
				PropertyManager.getInstance().setAccessToken(token);
			}
			
			@Override
			public void onFailure(int statusCode, Header[] headers,
					String responseString, Throwable throwable) {
				listener.onFail(statusCode);
			}
		});
	}
}
