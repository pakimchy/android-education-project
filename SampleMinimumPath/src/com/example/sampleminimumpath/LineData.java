package com.example.sampleminimumpath;

public class LineData implements Comparable<LineData> {
	public int start;
	public int end;
	public int distance;
	@Override
	public int compareTo(LineData another) {
		return distance - another.distance;
	}
	
	@Override
	public String toString() {
		return "("+start+","+end+")"+distance;
	}
}
