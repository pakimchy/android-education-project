package com.example.sample6customlist;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
	private static DataManager instance;
	public static DataManager getInstance() {
		if (instance == null) {
			instance = new DataManager();
		}
		return instance;
	}
	
	private DataManager() {	
	}
	
	public List<ItemData> getItemDataList() {
		ArrayList<ItemData> items = new ArrayList<ItemData>();
		for (int i = 0; i < 10 ; i++) {
			ItemData id = new ItemData();
			id.iconId = R.drawable.ic_launcher;
			id.title = "title" + i;
			id.desc = "desc" + i;
			id.like = i;
			items.add(id);
		}
		return items;
	}
	
	
}
