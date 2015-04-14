package com.example.sample7melon;

import org.json.JSONException;
import org.json.JSONObject;

public class MelonResult implements JSONParseHandler {

	public Melon melon;

	@Override
	public void parseJson(JSONObject jobject) throws JSONException {
		JSONObject jmelon = jobject.optJSONObject("melon");
		melon = new Melon();
		melon.parseJson(jmelon);	
	}
}
