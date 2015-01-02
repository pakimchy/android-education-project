package com.example.sample6applicationcomponent;

import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {
	public String name;
	public int age;
	public int height;
	public int weight;
	public boolean isMarry;
	public Person() {
		
	}
	
	public Person(Parcel source) {
		name = source.readString();
		age = source.readInt();
		height = source.readInt();
		weight = source.readInt();
		byte b = source.readByte();
		isMarry = b==1?true:false;
	}

	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(name);
		dest.writeInt(age);
		dest.writeInt(height);
		dest.writeInt(weight);
		byte b = isMarry?(byte)1:0;
		dest.writeByte(b);
		
	}
	
	public static Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {

		@Override
		public Person createFromParcel(Parcel source) {
//			Person p = new Person();
//			p.name = source.readString();
//			p.age = source.readInt();
//			p.height = source.readInt();
//			p.weight = source.readInt();
			return new Person(source);
		}

		@Override
		public Person[] newArray(int size) {
			return new Person[size];
		}
	};
}
