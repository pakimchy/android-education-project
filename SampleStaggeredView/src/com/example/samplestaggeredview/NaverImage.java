package com.example.samplestaggeredview;

import java.util.ArrayList;

import com.begentgroup.xmlparser.SerializedName;

public class NaverImage {
	public String title;
	public int total;
	public int start;
	public int display;
	@SerializedName("item")
	ArrayList<ImageItem> items;
}
