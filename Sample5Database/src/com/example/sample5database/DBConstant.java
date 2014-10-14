package com.example.sample5database;

import android.provider.BaseColumns;

public class DBConstant {
	public interface PersonTable extends BaseColumns {
		public static final String TABLE_NAME = "persontbl";
		public static final String FIELD_NAME = "name";
		public static final String FIELD_AGE = "age";
	}
}
