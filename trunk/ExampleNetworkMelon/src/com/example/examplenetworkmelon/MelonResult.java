package com.example.examplenetworkmelon;

import org.json.JSONException;
import org.json.JSONObject;

public class MelonResult implements JSONObjectHandler {

	public Melon melon;
	
	@Override
	public void setObject(JSONObject jobject) throws JSONException {
		melon = new Melon();
		JSONObject jmelon = jobject.getJSONObject("melon");
		melon.setObject(jmelon);
	}

}
