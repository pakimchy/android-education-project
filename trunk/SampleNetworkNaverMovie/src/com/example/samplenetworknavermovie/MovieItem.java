package com.example.samplenetworknavermovie;

public class MovieItem implements XMLObjectHandler {
	public String title;
	public String link;
	public String image;
	public String subtitle;
	public String pubDate;
	public String director;
	public String actor;
	public float userRating;
	@Override
	public void setData(String tag, Object value) {
		if (tag.equals("title")) {
			title = (String)value;
		} else if (tag.equals("link")) {
			link = (String)value;
		}
		
	}
	@Override
	public void startElement(XMLParserHandler parser, String tag) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public String toString() {
		return title;
	}
}
