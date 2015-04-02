package com.example.sample7applicationcomponent;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {
	public String name;
	public int age;
	public boolean isVisible;
	
	public Person() {
		
	}
	
	public Person(Parcel source) {
		name = source.readString();
		age = source.readInt();
		isVisible = (source.readInt() == 1) ? true : false;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeInt(age);
		dest.writeInt((isVisible)?1:0);
	}
	
	public static Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {

		@Override
		public Person createFromParcel(Parcel source) {
			return new Person(source);
		}

		@Override
		public Person[] newArray(int size) {
			return new Person[size];
		}
	};
}
