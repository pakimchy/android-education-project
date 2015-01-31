package com.example.samplenetworknavermovie;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class NetworkRequest<T> {
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
}
