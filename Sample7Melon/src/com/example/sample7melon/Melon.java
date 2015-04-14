package com.example.sample7melon;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.annotations.SerializedName;

public class Melon implements JSONParseHandler {
	int menuId;
	int count;
	int page;
	int totalPages;
	String rankDay;
	String rankHour;
	
	@SerializedName("songs")
	SongList songs;
	@Override
	public void parseJson(JSONObject jobject) throws JSONException {
		menuId = jobject.getInt("menuId");
		count = jobject.getInt("count");
		page = jobject.optInt("page");
		totalPages = jobject.optInt("totalPages");
		rankDay = jobject.optString("rankDay");
		rankHour = jobject.optString("rankHour");
		JSONObject jsongs = jobject.optJSONObject("songs");
		songs = new SongList();
		songs.parseJson(jsongs);	
	}
}
