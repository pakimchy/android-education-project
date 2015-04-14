package com.example.sample7networkmodel;

import java.util.ArrayList;

import com.begentgroup.xmlparser.SerializedName;

public class NaverMovies {
	String title;
	String link;
	int total;
	int start;
	int display;
	@SerializedName("item")
	ArrayList<MovieItem> movielist;
}
