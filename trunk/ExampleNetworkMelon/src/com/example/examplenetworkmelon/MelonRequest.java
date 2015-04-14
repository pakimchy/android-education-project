package com.example.examplenetworkmelon;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.google.gson.Gson;

public class MelonRequest extends NetworkRequest<Melon> {

	private static final String SERVER = "http://apis.skplanetx.com";
	private static final String MELON_CHART_URL = SERVER + "/melon/charts/realtime";
	private static final String MELON_CHART_PARAMS = "?version=%s&page=%s&count=%s";
	private static final String HEADER_ACCEPT_XML = "application/xml";
	private static final String HEADER_APPKEY = "458a10f5-c07e-34b5-b2bd-4a891e024c2a";
	private static final String HEADER_ACCEPT_JSON = "application/json";
	
	private static final int VERSION = 1;
	
	int page;
	int count;
	public MelonRequest() {
		this(1,10);
	}
	public MelonRequest(int page, int count) {
		this.page = page;
		this.count = count;
	}
	
	@Override
	public URL getURL() throws MalformedURLException {
		String urlText = MELON_CHART_URL + String.format(MELON_CHART_PARAMS, VERSION, page, count);
		return new URL(urlText);
	}
	
	@Override
	public void setRequestHeader(HttpURLConnection conn) {
		super.setRequestHeader(conn);
		conn.setRequestProperty("Accept", HEADER_ACCEPT_JSON);
		conn.setRequestProperty("appKey", HEADER_APPKEY);
	}

	@Override
	public Melon parse(InputStream is) throws IOException {
		Gson gson = new Gson();
		MelonResult result = gson.fromJson(new InputStreamReader(is), MelonResult.class);
		return result.melon;
	}

}
