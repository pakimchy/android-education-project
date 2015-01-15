package com.example.sample6database;

import android.provider.BaseColumns;

public class DBConstant {
	public interface PersonTable extends BaseColumns {
		public static final String TABLE = "personTbl";
		public static final String FIELD_NAME = "name";
		public static final String FIELD_AGE = "age";
		public static final String FIELD_PHONE = "phone";
	}
}
