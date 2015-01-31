package com.example.samplenetworkmelon;

import org.json.JSONException;
import org.json.JSONObject;

public class Melon implements JSONParser {
	public int melonId;
	public String rankDay;
	public Songs songs;
	@Override
	public void parseJSON(JSONObject object) {
		try {
			if (!object.isNull("melonId")) {
				melonId = object.getInt("melonId");
			}
			Songs songs = new Songs();
			JSONObject json = object.getJSONObject("songs");
			songs.parseJSON(json);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}
