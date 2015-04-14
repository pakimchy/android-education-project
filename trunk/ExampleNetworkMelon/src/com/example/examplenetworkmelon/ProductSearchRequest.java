package com.example.examplenetworkmelon;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.Gson;

public class ProductSearchRequest extends NetworkRequest<ProductSearchResponse> {

	private static final String SERVER = "http://apis.skplanetx.com";
	private static final String PRODUCT_SEARCH_URL = SERVER + "/11st/common/products";
	private static final String PRODUCT_SEARCH_PARAMS = "?version=%s&searchKeyword=%s";
	private static final String HEADER_ACCEPT_XML = "application/xml";
	private static final String HEADER_APPKEY = "458a10f5-c07e-34b5-b2bd-4a891e024c2a";
	private static final String HEADER_ACCEPT_JSON = "application/json";
	private static final int VERSION = 1;
	
	String mKeyword;
	public ProductSearchRequest(String keyword) {
		mKeyword = keyword;
	}
	
	@Override
	public URL getURL() throws MalformedURLException {
		try {
			return new URL(PRODUCT_SEARCH_URL + String.format(PRODUCT_SEARCH_PARAMS, VERSION, URLEncoder.encode(mKeyword, "utf-8")));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public void setRequestHeader(HttpURLConnection conn) {
		super.setRequestHeader(conn);
		conn.setRequestProperty("Accept", HEADER_ACCEPT_JSON);
		conn.setRequestProperty("appKey", HEADER_APPKEY);
	}	

	@Override
	public ProductSearchResponse parse(InputStream is) throws IOException {
		Gson gson = new Gson();
		ProductSearchResult result = gson.fromJson(new InputStreamReader(is), ProductSearchResult.class);
		return result.productSearchResponse;
	}

}
