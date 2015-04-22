package com.example.sample7alarm;

import java.util.ArrayList;
import java.util.List;

public class DBManager {
	static List<AlarmData> list = new ArrayList<AlarmData>();
	static int add(AlarmData data) {
		if (data.id == -1) {
			data.id = list.size();
			list.add(data);
		}
		return data.id;
	}
	
	static AlarmData nearData() {
		AlarmData d = null;
		long currentTime = System.currentTimeMillis();
		for (int i = 0; i < list.size(); i++) {
			AlarmData data = list.get(i);
			if (data.time > currentTime) {
				if (d == null) {
					d = data;
				} else if (d.time > data.time) {
					d = data;
				}
			}
		}
		return d;
	}
	
	static List<AlarmData> processData() {
		List<AlarmData> processList = new ArrayList<AlarmData>();
		long currentTime = System.currentTimeMillis();
		for (int i = 0; i < list.size(); i++) {
			AlarmData data = list.get(i);
			if (data.time < currentTime) {
				processList.add(data);
			}
		}
		return processList;
	}
	
	static void setAlarmData(AlarmData data) {
		for (int i = 0; i < list.size(); i++) {
			AlarmData d = list.get(i);
			if (d.id == data.id) {
				d.message = data.message;
				d.time = data.time;
			}
		}
	}
}
