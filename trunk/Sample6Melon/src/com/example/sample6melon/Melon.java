package com.example.sample6melon;

import com.google.gson.annotations.SerializedName;

public class Melon {
	public int menuId;
	public int count;
	public int page;
	public int totalPages;
	public String rankDay;
	
	@SerializedName("songs")
	public SongList songlist;
}
