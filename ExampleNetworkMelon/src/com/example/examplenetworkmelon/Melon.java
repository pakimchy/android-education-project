package com.example.examplenetworkmelon;

import org.json.JSONException;
import org.json.JSONObject;

public class Melon implements JSONObjectHandler, XMLObjectHandler {
	public int menuId;
	public int count;
	public int page;
	public int totalPages;
	public String rankDay;
	public String rankHour;
	public Songs songs;
	@Override
	public XMLObjectHandler createChildHandler(String tag) {
		if ("songs".equals(tag)) {
			return new Songs();
		}
		return null;
	}
	@Override
	public void setData(String tag, Object value) {
		if ("menuId".equals(tag)) {
			menuId = Integer.parseInt((String)value);
		} else if ("count".equals(tag)) {
			count = Integer.parseInt((String)value);
		} else if ("page".equals(tag)) {
			page = Integer.parseInt((String)value);
		} else if ("totalPages".equals(tag)) {
			totalPages = Integer.parseInt((String)value);
		} else if ("rankDay".equals(tag)) {
			rankDay = (String)value;
		} else if ("rankHour".equals(tag)) {
			rankHour = (String)value;
		} else if ("songs".equals(tag)) {
			songs = (Songs)value;
		}
	}
	@Override
	public void setObject(JSONObject jobject) throws JSONException {
		if (!jobject.isNull("menuId")) {
			menuId = jobject.getInt("menuId");
		}
		if (!jobject.isNull("count")) {
			count = jobject.getInt("count");
		}
		if (!jobject.isNull("page")) {
			page = jobject.getInt("page");
		}
		if (!jobject.isNull("totalPages")) {
			totalPages = jobject.getInt("totalPages");
		}
		if (!jobject.isNull("rankDay")) {
			rankDay = jobject.getString("rankDay");
		}
		if (!jobject.isNull("rankHour")) {
			rankHour = jobject.getString("rankHour");
		}
		if (!jobject.isNull("songs")) {
			songs = new Songs();
			JSONObject jsongs = jobject.getJSONObject("songs");
			songs.setObject(jsongs);
		}
	}
}
