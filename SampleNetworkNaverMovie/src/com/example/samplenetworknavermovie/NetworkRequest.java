package com.example.samplenetworknavermovie;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;

import com.example.samplenetworknavermovie.NetworkManager.OnResultListener;

public abstract class NetworkRequest<T> implements Runnable {
	abstract public URL getURL() throws MalformedURLException;
	public void setConfiguration(HttpURLConnection conn) {
		conn.setRequestProperty("User-Agent", "Android...");
		conn.setConnectTimeout(30000);
		conn.setReadTimeout(30000);
	}
	
	private HttpURLConnection mConnection;
	public void setConnection(HttpURLConnection conn) {
		mConnection = conn;
	}
	
	public void writeOutput(HttpURLConnection out) {
		
	}
	private boolean isCanceled;
	public boolean isCanceled() {
		return isCanceled;
	}
	
	public void setCancel(boolean cancel) {
		isCanceled = cancel;
		if (mConnection != null) {
			mConnection.disconnect();
		}
	}
	
	public String getRequestMethod() {
		return "GET";
	}
	
	abstract public T doParsing(InputStream is);

	NetworkManager mNM;
	OnResultListener<T> mListener;
	Context mContext;
	
	public void setNetworkManager(NetworkManager nm) {
		mNM = nm;
	}
	
	public void setContext(Context context) {
		mContext = context;
	}
	
	public Context getContext() {
		return mContext;
	}
	
	public void setOnResultListener(OnResultListener<T> listener) {
		mListener = listener;
	}
	T result;
	
	@Override
	public void run() {
		int retryCount = 3;
		while (retryCount > 0 && !isCanceled()) {
			try {
				URL url = getURL();
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				setConfiguration(conn);
				conn.setRequestMethod(getRequestMethod());
				writeOutput(conn);

				if (isCanceled()) continue;
				int responseCode = conn.getResponseCode();
				if (isCanceled()) continue;
				
				setConnection(conn);

				if (responseCode == HttpURLConnection.HTTP_OK) {
					InputStream is = conn.getInputStream();
					if (isCanceled())
						return;
					result = doParsing(is);
					mNM.sendSuccessMessage(this);
				} else {
					mNM.sendFailMessage(this);
				}
				return;

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				retryCount = 0;
			} catch (MalformedURLException e) {
				e.printStackTrace();
				retryCount = 0;
			} catch (IOException e) {
				e.printStackTrace();
				retryCount--;
			}
		}
		mNM.sendFailMessage(this);
		
	}
	
	public void sendSuccess() {
		if (mListener != null && !isCanceled()) {
			mListener.onSuccess(this, result);
		}
	}
	
	public void sendFail() {
		if (mListener != null && !isCanceled()) {
			mListener.onFail(this, -2);
		}
	}
}
