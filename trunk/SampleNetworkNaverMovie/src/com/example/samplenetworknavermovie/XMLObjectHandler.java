package com.example.samplenetworknavermovie;

public interface XMLObjectHandler {
	public void startElement(XMLParserHandler parser, String tag);
	public void setData(String tag, Object value);
}
