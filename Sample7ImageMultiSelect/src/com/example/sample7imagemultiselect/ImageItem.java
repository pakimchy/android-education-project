package com.example.sample7imagemultiselect;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

public class ImageItem implements Parcelable {
	Uri uri;
	String name;
	String path;
	public ImageItem(Uri uri, String name, String path) {
		this.uri = uri;
		this.name = name;
		this.path = path;
	}
	public ImageItem(Parcel source) {
		uri = source.readParcelable(Uri.class.getClassLoader());
		name = source.readString();
		path = source.readString();
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(uri, flags);
		dest.writeString(name);
		dest.writeString(path);
	}
	
	public static Parcelable.Creator<ImageItem> CREATOR = new Parcelable.Creator<ImageItem>() {

		@Override
		public ImageItem createFromParcel(Parcel source) {
			return new ImageItem(source);
		}

		@Override
		public ImageItem[] newArray(int size) {
			return new ImageItem[size];
		}
	};
}
