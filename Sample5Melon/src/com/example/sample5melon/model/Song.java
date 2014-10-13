package com.example.sample5melon.model;

public class Song {
	public String songName;
	public String albumName;
	@Override
	public String toString() {
		return songName+"("+albumName+")";
	}
}
