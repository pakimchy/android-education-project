package com.example.samplenetworknavermovie;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
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

	HashMap<Context, List<NetworkRequest>> mRequestMap = new HashMap<Context, List<NetworkRequest>>();

	private NetworkManager() {

	}

	public interface OnResultListener<T> {
		public void onSuccess(NetworkRequest request, T result);

		public void onFail(NetworkRequest request, int code);
	}

	public void getNaverMovie(Context context, NaverMovieRequest request,
			OnResultListener<NaverMovies> listener) {
		List<NetworkRequest> list = mRequestMap.get(context);
		if (list == null) {
			list = new ArrayList<NetworkRequest>();
			mRequestMap.put(context, list);
		}
		list.add(request);

		new Thread(new NetworkTask(context, request, listener)).start();
	}

	public void cancel(Context context) {
		List<NetworkRequest> list = mRequestMap.get(context);
		if (list != null) {
			for (NetworkRequest req : list) {
				req.setCancel(true);
			}
		}
	}
	
	public void removeRequest(Context context, NetworkRequest request) {
		List<NetworkRequest> list = mRequestMap
				.get(context);
		if (list != null) {
			list.remove(request);
			if (list.size() == 0) {
				mRequestMap.remove(context);
			}
		}
	}

	private static final String KEY = "55f1e342c5bce1cac340ebb6032c7d9a";

	class NetworkTask<T> implements Runnable {
		NetworkRequest<T> mRequest;
		OnResultListener<T> mListener;
		Context mContext;

		public NetworkTask(Context context, NetworkRequest<T> request,
				OnResultListener<T> listener) {
			mContext = context;
			mRequest = request;
			mListener = listener;
		}

		@Override
		public void run() {
			int retryCount = 3;
			while (retryCount > 0 && !mRequest.isCanceled()) {
				try {
					URL url = mRequest.getURL();
					HttpURLConnection conn = (HttpURLConnection) url
							.openConnection();
					mRequest.setConfiguration(conn);
					conn.setRequestMethod(mRequest.getRequestMethod());
					mRequest.writeOutput(conn);

					if (mRequest.isCanceled()) continue;
					int responseCode = conn.getResponseCode();
					if (mRequest.isCanceled()) continue;
					
					mRequest.setConnection(conn);

					if (responseCode == HttpURLConnection.HTTP_OK) {
						InputStream is = conn.getInputStream();
						if (mRequest.isCanceled())
							return;
						final T result = mRequest.doParsing(is);
						mainHandler.post(new Runnable() {

							@Override
							public void run() {
								if (mListener != null && !mRequest.isCanceled()) {
									mListener.onSuccess(mRequest, result);
								}
								removeRequest(mContext, mRequest);
							}
						});
					} else {
						mainHandler.post(new Runnable() {
							
							@Override
							public void run() {
								if (mListener != null && !mRequest.isCanceled()) {
									mListener.onFail(mRequest, -1);
								}
								removeRequest(mContext, mRequest);
							}
						});
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
			mainHandler.post(new Runnable() {
				
				@Override
				public void run() {
					if (mListener != null && !mRequest.isCanceled()) {
						mListener.onFail(mRequest, -2);
					}
					removeRequest(mContext, mRequest);
				}
			});

		}
	}

}
