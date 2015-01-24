package com.example.samplenetworknavermovie;

import java.util.ArrayList;

import com.begentgroup.xmlparser.SerializedName;

public class NaverMovies implements XMLObjectHandler {
	public String title;
	public String link;
	public String description;
	public String lastBuildDate;
	public int total;
	public int start;
	public int display;
	@SerializedName("item")
	public ArrayList<MovieItem> items = new ArrayList<MovieItem>();
	
	@Override
	public void setData(String tag, Object value) {
		if (tag.equals("title")) {
			title = (String)value;
		} else if (tag.equals("item")) {
			items.add((MovieItem)value);
		}
	}
	
	@Override
	public void startElement(XMLParserHandler parser, String tag) {
		if (tag.equals("item")) {
			MovieItem item = new MovieItem();
			parser.push(item);
		}
		
	}
}
