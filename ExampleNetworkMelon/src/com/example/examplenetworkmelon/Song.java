package com.example.examplenetworkmelon;

import org.json.JSONException;
import org.json.JSONObject;

public class Song implements JSONObjectHandler, XMLObjectHandler {
	public int songId;
	public String songName;
	public Artists artists;
	public int albumId;
	public String albumName;
	public int currentRank;
	public int pastRank;
	public int playTime;
	public String issueDate;
	public String isTitleSong;
	public String isHitSong;
	public String isAdult;
	public String isFree;
	@Override
	public String toString() {
		return "("+currentRank+")"+songName;
	}
	@Override
	public XMLObjectHandler createChildHandler(String tag) {
		if ("artists".equals(tag)) {
			return new Artists();
		}
		return null;
	}
	@Override
	public void setData(String tag, Object value) {
		if ("songId".equals(tag)) {
			songId = Integer.parseInt((String)value);
		} else if ("songName".equals(tag)) {
			songName = (String)value;
		} else if ("artists".equals(tag)) {
			artists = (Artists)value;
		} else if ("albumId".equals(tag)) {
			albumId = Integer.parseInt((String)value);
		} else if ("albumName".equals(tag)) {
			albumName = (String)value;
		} else if ("currentRank".equals(tag)) {
			currentRank = Integer.parseInt((String)value);
		} else if ("pastRank".equals(tag)) {
			pastRank = Integer.parseInt((String)value);
		} else if ("playTime".equals(tag)) {
			playTime = Integer.parseInt((String)value);
		} else if ("issueDate".equals(tag)) {
			issueDate = (String)value;
		} else if ("isTitleSong".equals(tag)) {
			isTitleSong = (String)value;
		} else if ("isHitSong".equals(tag)) {
			isHitSong = (String)value;
		} else if ("isAdult".equals(tag)) {
			isAdult = (String)value;
		} else if ("isFree".equals(tag)) {
			isFree = (String)value;
		}
	}
	@Override
	public void setObject(JSONObject jobject) throws JSONException {
		if (!jobject.isNull("songId")) {
			songId = jobject.getInt("songId");
		}
		if (!jobject.isNull("songName")) {
			songName = jobject.getString("songName");
		}
		if (!jobject.isNull("artists")) {
			artists = new Artists();
			JSONObject jartists = jobject.getJSONObject("artists");
			artists.setObject(jartists);
		}
		if (!jobject.isNull("albumId")) {
			albumId = jobject.getInt("albumId");
		}
		if (!jobject.isNull("albumName")) {
			albumName = jobject.getString("albumName");
		}
		if (!jobject.isNull("currentRank")) {
			currentRank = jobject.getInt("currentRank");
		}
		if (!jobject.isNull("pastRank")) {
			pastRank = jobject.getInt("pastRank");
		}
		if (!jobject.isNull("playTime")) {
			playTime = jobject.getInt("playTime");
		}
		if (!jobject.isNull("issueDate")) {
			issueDate = jobject.getString("issueDate");
		}
		if (!jobject.isNull("isTitleSong")) {
			isTitleSong = jobject.getString("isTitleSong");
		}
		if (!jobject.isNull("isHitSong")) {
			isHitSong = jobject.getString("isHitSong");
		}
		if (!jobject.isNull("isAdult")) {
			isAdult = jobject.getString("isAdult");
		}
		if (!jobject.isNull("isFree")) {
			isFree = jobject.getString("isFree");
		}
	}
}
