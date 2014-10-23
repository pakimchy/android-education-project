package com.example.samplelistpopupwindow;

import java.util.ArrayList;

import com.begentgroup.xmlparser.SerializedName;

public class NaverMovies {
	public String title;
	public int total;
	public int start;
	public int display;
	
	@SerializedName("item")
	public ArrayList<MovieItem> items;
}
