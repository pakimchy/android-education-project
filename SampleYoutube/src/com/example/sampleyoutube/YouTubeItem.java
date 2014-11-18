package com.example.sampleyoutube;

public class YouTubeItem {
	public YouTubeId id;
	public Snippet snippet;
	@Override
	public String toString() {
		return snippet.title;
	}
}
