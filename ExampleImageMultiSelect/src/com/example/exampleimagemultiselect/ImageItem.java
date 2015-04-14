package com.example.exampleimagemultiselect;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.os.ParcelableCompat;

public class ImageItem implements Parcelable {
	public Uri uri;
	public String title;
	
	public ImageItem() {
	}

	public ImageItem(Parcel source) {
		uri = source.readParcelable(Uri.class.getClassLoader());
		title = source.readString();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(uri, flags);
		dest.writeString(title);
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
