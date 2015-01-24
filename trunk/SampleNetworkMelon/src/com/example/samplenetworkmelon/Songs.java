package com.example.samplenetworkmelon;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Songs implements JSONParser {
	public ArrayList<Song> song = new ArrayList<Song>();

	@Override
	public void parseJSON(JSONObject object) {
		try {
			JSONArray array = object.getJSONArray("song");
			for (int i = 0 ; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				Song s = new Song();
				s.parseJSON(obj);
				song.add(s);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
