package com.example.exampleaddressbook;

import android.provider.BaseColumns;

public class AddressBookConstant {
	public interface AddressTable extends BaseColumns {
		public static final String TABLE = "addresstable";
		public static final String NAME = "name";
		public static final String PHONE = "phone";
		public static final String HOME = "home";
		public static final String OFFICE = "office";
	}
}
