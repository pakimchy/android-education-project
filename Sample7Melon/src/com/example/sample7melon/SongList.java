package com.example.sample7melon;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SongList implements JSONParseHandler {
	ArrayList<Song> song;

	@Override
	public void parseJson(JSONObject jobject) throws JSONException {
		JSONArray jarray = jobject.optJSONArray("song");
		song = new ArrayList<Song>();
		for (int i = 0; i < jarray.length(); i++) {
			JSONObject jsong = jarray.getJSONObject(i);
			Song s = new Song();
			s.parseJson(jsong);
			song.add(s);
		}
		
	}
}
