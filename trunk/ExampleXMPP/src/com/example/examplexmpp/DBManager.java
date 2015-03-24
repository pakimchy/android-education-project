package com.example.examplexmpp;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class DBManager extends SQLiteOpenHelper {

	private static final String DB_NAME = "chatting";
	private static final int DB_VERSION = 1;
	
	public interface ChatTable extends BaseColumns {
		public static final String TABLE = "chattbl";
		public static final String COLUMN_MATE_USER_ID = "mateid";
		public static final String COLUMN_MESSAGE = "message";
		public static final String COLUMN_TIME = "created";
		public static final String COLUMN_RECEIVED = "received";
	}
	
	private static DBManager instance;
	public static DBManager getInstance() {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}
	
	private DBManager() {
		super(MyApplication.getContext(), DB_NAME, null, DB_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "CREATE TABLE " + ChatTable.TABLE + "(" +
				     ChatTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
				     ChatTable.COLUMN_MATE_USER_ID + " TEXT NOT NULL," +
				     ChatTable.COLUMN_MESSAGE + " TEXT," +
				     ChatTable.COLUMN_RECEIVED + " INT," +
				     ChatTable.COLUMN_TIME + " DATETIME DEFAULT CURRENT_TIMESTAMP);";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}

	public void addChatMessage(ChatMessage message) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(ChatTable.COLUMN_MATE_USER_ID, message.mateId);
		values.put(ChatTable.COLUMN_MESSAGE, message.message);
		int received = (message.bReceived)? 1 : 0;
		values.put(ChatTable.COLUMN_RECEIVED, received);
		db.insert(ChatTable.TABLE, null, values);
		db.close();
	}
	
	public List<ChatMessage> getChatMessageList(String userid) {
		List<ChatMessage> list = new ArrayList<ChatMessage>();
		String[] columns = {ChatTable.COLUMN_MATE_USER_ID, ChatTable.COLUMN_MESSAGE, ChatTable.COLUMN_RECEIVED, ChatTable.COLUMN_TIME};
		String selection = ChatTable.COLUMN_MATE_USER_ID + " = ?";
		String[] selectionArgs = {userid};
		String orderBy = ChatTable.COLUMN_TIME + " ASC";
		SQLiteDatabase db = getReadableDatabase();
		Cursor c = db.query(ChatTable.TABLE, columns, selection, selectionArgs, null, null, orderBy);
		int mateid = c.getColumnIndex(ChatTable.COLUMN_MATE_USER_ID);
		int messageid = c.getColumnIndex(ChatTable.COLUMN_MESSAGE);
		int receivedid = c.getColumnIndex(ChatTable.COLUMN_RECEIVED);
		int timeid = c.getColumnIndex(ChatTable.COLUMN_TIME);
		while(c.moveToNext()) {
			ChatMessage message = new ChatMessage();
			message.mateId = c.getString(mateid);
			message.message = c.getString(messageid);
			int received = c.getInt(receivedid);
			message.bReceived = (received == 1)? true : false;
			String time = c.getString(timeid);
			message.created = c.getLong(timeid);
			list.add(message);
		}
		return list;
	}
	
	
	
}
