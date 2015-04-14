package com.example.examplenetworkmelon;

import org.json.JSONException;
import org.json.JSONObject;

public class Artist implements JSONObjectHandler, XMLObjectHandler {
	public int artistId;
	public String artistName;
	@Override
	public XMLObjectHandler createChildHandler(String tag) {
		return null;
	}
	
	@Override
	public void setData(String tag, Object value) {
		if ("artistId".equals(tag)) {
			artistId = Integer.parseInt((String)value);
		} else if ("artistName".equals(tag)) {
			artistName = (String)value;
		}
	}

	@Override
	public void setObject(JSONObject jobject) throws JSONException {
		if (!jobject.isNull("artistId")) {
			artistId = jobject.getInt("artistId");
		}
		if (!jobject.isNull("artistName")) {
			artistName = jobject.getString("artistName");
		}
		
	}
}
