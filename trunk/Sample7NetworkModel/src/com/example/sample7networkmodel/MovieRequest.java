package com.example.sample7networkmodel;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import com.begentgroup.xmlparser.XMLParser;

public class MovieRequest extends NetworkRequest<NaverMovies> {

	String mKeyword;
	private String URL = "http://openapi.naver.com/search?key=c1b406b32dbbbbeee5f2a36ddc14067f&query=%s&display=10&start=1&target=movie";
	public MovieRequest(String keyword) {
		mKeyword = keyword;
	}
	@Override
	public URL getURL() throws MalformedURLException {
		try {
			String url = String.format(URL, URLEncoder.encode(mKeyword, "utf-8"));
			return new URL(url);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public NaverMovies parse(InputStream is) throws IOException {
		XMLParser parser = new XMLParser();
		NaverMovies movies = parser.fromXml(is, "channel", NaverMovies.class);
		return movies;
	}

}
