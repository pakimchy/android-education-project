package com.example.examplenetworkmelon;

public interface XMLObjectHandler {
	public XMLObjectHandler createChildHandler(String tag);
	public void setData(String tag, Object value);
}
