package com.example.sample6melon;

public class Song {
	public int songId;
	public String songName;
	public String albumName;
	
	@Override
	public String toString() {
		return songName+"("+albumName+")";
	}
}
