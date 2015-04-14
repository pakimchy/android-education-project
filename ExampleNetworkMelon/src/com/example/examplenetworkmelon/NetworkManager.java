package com.example.examplenetworkmelon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

public class NetworkManager {
	private static NetworkManager instance;
	public static NetworkManager getInstance() {
		if (instance == null) {
			instance = new NetworkManager();
		}
		return instance;
	}
	
	ThreadPoolExecutor mExecutor;
	public static final int CORE_POOL_SIZE = 5;
	public static final int MAXIMUN_POOL_SIZE = 64;
	public static final int KEEP_ALIVE_TIME = 5000;
	LinkedBlockingQueue<Runnable> mRequestQueue = new LinkedBlockingQueue<Runnable>();
	
	Map<Context, List<NetworkRequest>> mRequestMap = new HashMap<Context,List<NetworkRequest>>();
	
	private NetworkManager() {
		mExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, MAXIMUN_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS, mRequestQueue);
	}
	
	public interface OnResultListener<T> {
		public void onSuccess(NetworkRequest<T> request, T result);
		public void onFail(NetworkRequest<T> request, int code);
	}
	
	public static final int MESSAGE_SEND_SUCCESS = 1;
	public static final int MESSAGE_SEND_FAIL = 2;
	
	Handler mHandler = new Handler(Looper.getMainLooper()) {
		@Override
		public void handleMessage(Message msg) {
			NetworkRequest request = (NetworkRequest)msg.obj;
			switch(msg.what) {
			case MESSAGE_SEND_SUCCESS :
				request.sendSuccess();
				break;
			case MESSAGE_SEND_FAIL :
				request.sendFail();
				break;
			}
			removeMap(request);
		}
	};
	
	public void sendSuccess(NetworkRequest request) {
		mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SEND_SUCCESS, request));
	}
	
	public void sendFail(NetworkRequest request) {
		mHandler.sendMessage(mHandler.obtainMessage(MESSAGE_SEND_FAIL, request));
	}
	
	public <T> void getNetworkData(Context context, NetworkRequest<T> request, OnResultListener<T> listener) {
		request.setOnResultListener(listener);		
		getNetworkData(context, request);
	}
	
	public void cancelAll(Context context) {
		List<NetworkRequest> list = mRequestMap.get(context);
		if (list != null) {
			List<NetworkRequest> removelist = new ArrayList<NetworkRequest>(list);
			for (NetworkRequest req : removelist) {
				req.cancel();
			}
		}
	}
	
	private void addMap(NetworkRequest request) {
		Context context = request.getContext();
		List<NetworkRequest> list = mRequestMap.get(context);
		if (list == null) {
			list = new ArrayList<NetworkRequest>();
			mRequestMap.put(context, list);
		}
		list.add(request);		
	}
	
	private void removeMap(NetworkRequest request) {
		Context context = request.getContext();
		List<NetworkRequest> list = mRequestMap.get(context);
		if (list != null) {
			list.remove(request);
			if (list.size() == 0) {
				mRequestMap.remove(context);
			}
		}
	}
	
	public <T> void getNetworkData(Context context, NetworkRequest<T> request) {
		request.setNetworkManager(this);
		request.setContext(context);		
		addMap(request);
		mExecutor.execute(request);
	}
	
	public void processCancel(NetworkRequest request) {
		mRequestQueue.remove(request);
		removeMap(request);
	}
}
