package com.example.sample5melon.model;

import com.google.gson.annotations.SerializedName;

public class MelonObject {
	public int menuId;
	public int count;
	public int page;
	public int totalPages;
	public String rankDay;
	
	@SerializedName("songs")
	public Songs songList;
}
