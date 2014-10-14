package com.example.sample5database;

public class Person {
	public long _id = -1L;
	public String name;
	public int age;
	@Override
	public String toString() {
		return name + "(" + age + ")";
	}
}
