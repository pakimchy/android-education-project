package com.example.sample6navermovie;

import java.util.ArrayList;

import com.begentgroup.xmlparser.SerializedName;

public class NaverMovie {
	public String title;
	public String link;
	public int total;
	public int start;
	public int display;
	
	@SerializedName("item")
	public ArrayList<MovieItem> items;
}
