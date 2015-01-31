package com.example.samplenetworknavermovie;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.Handler;
import android.os.Looper;

public class NetworkManager {
	private static NetworkManager instance;

	public static NetworkManager getInstance() {
		if (instance == null) {
			instance = new NetworkManager();
		}
		return instance;
	}

	Handler mainHandler = new Handler(Looper.getMainLooper());

	private NetworkManager() {

	}

	public interface OnResultListener<T> {
		public void onSuccess(NetworkRequest request, T result);

		public void onFail(NetworkRequest request, int code);
	}

	public void getNaverMovie(NaverMovieRequest request,
			OnResultListener<NaverMovies> listener) {
		new Thread(new NetworkTask(request, listener)).start();
	}

	private static final String KEY = "55f1e342c5bce1cac340ebb6032c7d9a";

	class NetworkTask<T> implements Runnable {
		NetworkRequest<T> mRequest;
		OnResultListener<T> mListener;

		public NetworkTask(NetworkRequest<T> request,
				OnResultListener<T> listener) {
			mRequest = request;
			mListener = listener;
		}

		@Override
		public void run() {
			try {
				URL url = mRequest.getURL();
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				mRequest.setConfiguration(conn);
				conn.setRequestMethod(mRequest.getRequestMethod());
				mRequest.writeOutput(conn);

				int responseCode = conn.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {
					InputStream is = conn.getInputStream();
					final T result = mRequest.doParsing(is);
					mainHandler.post(new Runnable() {

						@Override
						public void run() {
							if (mListener != null) {
								mListener.onSuccess(mRequest, result);
							}
						}
					});
				}

			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

}
