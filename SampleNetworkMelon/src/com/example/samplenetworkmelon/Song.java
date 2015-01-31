package com.example.samplenetworkmelon;

import org.json.JSONException;
import org.json.JSONObject;

public class Song implements JSONParser {
	public int songId;
	public String songName;
	public String currentRank;
	@Override
	public void parseJSON(JSONObject object) {
		try {
			songId = object.getInt("songId");
			songName = object.getString("songName");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Override
	public String toString() {
		return "("+currentRank+")"+songName;
	}
}
