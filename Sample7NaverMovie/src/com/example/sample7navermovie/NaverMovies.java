package com.example.sample7navermovie;

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
