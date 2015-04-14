package com.example.examplealarm;

import java.util.ArrayList;
import java.util.List;

import java.util.ArrayList;
import java.util.List;

public class AlarmData {
	long id;
	long time;
	String data;
	
	private static List<AlarmData> items = new ArrayList<AlarmData>();
	
	public static List<AlarmData> getProcessList(long time) {
		List<AlarmData> list = new ArrayList<AlarmData>();
		for (AlarmData d : items) {
			if (d.time < time) {
				list.add(d);
			}
		}
		return list;
	}
	
	public static void addAlarmData(long time, String data) {
		AlarmData d = new AlarmData();
		d.time = time;
		d.data = data;
		d.id = getLastId() + 1;
		items.add(d);
	}
	
	private static long getLastId() {
		long last = 0;
		for (AlarmData d : items) {
			if (d.id > last) {
				last = d.id;
			}
		}
		return last;
	}
	
	public static void updateList(List<AlarmData> list) {
		for (AlarmData s : list) {
			for (AlarmData d : items) {
				if (s.id == d.id) {
					d.time = s.time;
					d.data = s.data;
					break;
				}
			}
		}
		
		List<AlarmData> removeList = new ArrayList<AlarmData>();
		long current = System.currentTimeMillis();
		for (AlarmData d : items) {
			if (d.time < current) {
				removeList.add(d);
			}
		}
		items.removeAll(removeList);
	}
	
	public static long getNextAlarmTime() {
		long nexttime = Long.MAX_VALUE;
		for (AlarmData d : items) {
			if (d.time < nexttime) {
				nexttime = d.time;
			}
		}
		return nexttime;
	}
}
