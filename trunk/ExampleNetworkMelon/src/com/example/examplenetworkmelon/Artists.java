package com.example.examplenetworkmelon;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Artists implements JSONObjectHandler, XMLObjectHandler {
	public ArrayList<Artist> artist = new ArrayList<Artist>();

	@Override
	public XMLObjectHandler createChildHandler(String tag) {
		if ("artist".equals(tag)) {
			return new Artist();
		}
		return null;
	}

	@Override
	public void setData(String tag, Object value) {
		if ("artist".equals(tag)) {
			artist.add((Artist)value);
		}
	}

	@Override
	public void setObject(JSONObject jobject) throws JSONException {
		if (!jobject.isNull("artist")) {
			JSONArray arrayArtist = jobject.getJSONArray("artist");
			artist = new ArrayList<Artist>();
			for (int i = 0; i < arrayArtist.length(); i++) {
				JSONObject item = arrayArtist.getJSONObject(i);
				Artist a = new Artist();
				a.setObject(item);
				artist.add(a);
			}
		}
	}
}
