package com.example.sample7database;

import android.provider.BaseColumns;

public class DBConstant {
	public interface AddressTable extends BaseColumns {
		public static final String TABLE = "addrTbl";
		public static final String NAME = "name";
		public static final String EMAIL = "email";
		public static final String PHONE = "phone";
		public static final String ADDRESS = "address";
	}
}
