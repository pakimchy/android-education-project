package com.example.samplenetworkmelon;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.annotations.SerializedName;

public class Songs implements JSONParser {
	
	@SerializedName("song")
	public ArrayList<Song> songlist = new ArrayList<Song>();

	@Override
	public void parseJSON(JSONObject object) {
		try {
			JSONArray array = object.getJSONArray("song");
			for (int i = 0 ; i < array.length(); i++) {
				JSONObject obj = array.getJSONObject(i);
				Song s = new Song();
				s.parseJSON(obj);
				songlist.add(s);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
