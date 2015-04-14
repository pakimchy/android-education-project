package com.example.examplenetworkmelon;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Songs implements JSONObjectHandler, XMLObjectHandler {
	public ArrayList<Song> song = new ArrayList<Song>();

	@Override
	public XMLObjectHandler createChildHandler(String tag) {
		if ("song".equals(tag)) {
			return new Song();
		}
		return null;
	}

	@Override
	public void setData(String tag, Object value) {
		if ("song".equals(tag)) {
			song.add((Song)value);
		}
	}

	@Override
	public void setObject(JSONObject jobject) throws JSONException {
		if (!jobject.isNull("song")) {
			song = new ArrayList<Song>();
			JSONArray songArray = jobject.getJSONArray("song");
			for (int i = 0 ; i < songArray.length(); i++) {
				JSONObject jsong = songArray.getJSONObject(i);
				Song s = new Song();
				s.setObject(jsong);
				song.add(s);
			}
		}
		
	}
}
