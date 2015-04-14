package com.example.examplenetworkmelon;

import com.example.examplenetworkmelon.NetworkManager.OnResultListener;

public class RequestFactory {
	public static NetworkRequest getMelonRequest() {
		return new MelonRequest();
	}
	
	public static NetworkRequest getMelonRequest(OnResultListener<Melon> listener) {
		MelonRequest request = new MelonRequest();
		request.setOnResultListener(listener);
		return request;
	}
	
	public static NetworkRequest getSearchProductRequest(String keyword) {
		return new ProductSearchRequest(keyword);
	}
}
