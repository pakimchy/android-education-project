package com.example.samplenetworknavermovie;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import com.begentgroup.xmlparser.XMLParser;

public class NaverMovieRequest extends NetworkRequest<NaverMovies> {

	private static final String KEY = "55f1e342c5bce1cac340ebb6032c7d9a";
	
	String keyword;
	public NaverMovieRequest(String keyword) {
		this.keyword = keyword;
	}
	@Override
	public URL getURL() throws MalformedURLException  {
		String urlText = null;
		try {
			urlText = String.format("http://openapi.naver.com/search?key=%s&query=%s&display=50&start=1&target=movie",KEY,URLEncoder.encode(keyword, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new URL(urlText);
	}

	@Override
	public NaverMovies doParsing(InputStream is) {
		XMLParser parser = new XMLParser();
		NaverMovies movies = parser.fromXml(is, "channel", NaverMovies.class);
		return movies;
	}

}
