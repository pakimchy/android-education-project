package com.example.sample7melon;

import org.json.JSONException;
import org.json.JSONObject;

public class Song implements JSONParseHandler {
	int songId;
	String songName;
	int albumId;
	String albumName;
	int currentRank;
	
	@Override
	public String toString() {
		return "["+currentRank + "]"+songName + "\n(" + albumName + ")";
	}

	@Override
	public void parseJson(JSONObject jobject) throws JSONException {
		songId = jobject.getInt("songId");
		songName = jobject.getString("songName");
		albumId = jobject.optInt("albumId");
		albumName = jobject.optString("albumName");
		currentRank = jobject.optInt("currentRank");
	}
}
