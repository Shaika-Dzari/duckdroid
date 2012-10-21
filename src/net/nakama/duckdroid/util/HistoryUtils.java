/*
 * HistoryUtils.java
 * 
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. 
 * See http://sam.zoy.org/wtfpl/COPYING for more details. 
 * 
 * @since 2012-10-06
 */ 
package net.nakama.duckdroid.util;

import java.util.ArrayList;
import java.util.List;

import net.nakama.duckdroid.datamodel.HistoryEntry;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HistoryUtils extends SQLiteOpenHelper {

	public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "DuckDroid.db";
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    
    private static final String SQL_CREATE = "create table " + HistoryEntry.TABLE_NAME + "(" +
    											HistoryEntry._ID + " INTEGER PRIMARY KEY," + 
    											HistoryEntry.COLUMN_INSERTDATE + TEXT_TYPE + COMMA_SEP + 
    											HistoryEntry.COLUMN_QUERY + TEXT_TYPE + ")";
    private static final String SQL_DROP = "DROP TABLE IF EXISTS " + HistoryEntry.TABLE_NAME;
	
	
	/**
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 */
	public HistoryUtils(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE);
	}

	/* (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL(SQL_DROP);
		onCreate(db);
	}
	
	public long insert(ContentValues entry) {
		SQLiteDatabase db = getWritableDatabase();
		long key = db.insert(HistoryEntry.TABLE_NAME, "null", entry);
		db.close();
		return key;
	}
	
	public List<HistoryEntry> select() {
		SQLiteDatabase db = getReadableDatabase();
		String[] projection = {HistoryEntry.COLUMN_INSERTDATE, HistoryEntry.COLUMN_QUERY};
		List<HistoryEntry> lst = new ArrayList<HistoryEntry>();
		String uqy, insd;
		Cursor c = db.query(HistoryEntry.TABLE_NAME, projection, null, null, null, null, "" + HistoryEntry.COLUMN_INSERTDATE + " DESC", "30");
		
		if (c.moveToFirst()) {
			do {
				uqy = c.getString(c.getColumnIndexOrThrow(HistoryEntry.COLUMN_QUERY));
				insd = c.getString(c.getColumnIndexOrThrow(HistoryEntry.COLUMN_INSERTDATE));
				lst.add(new HistoryEntry(insd, uqy));				
			} while(c.moveToNext());
		}
		
		c.close();
		db.close();
		
		return lst;
	}
	
	public long delete(String id) {
		SQLiteDatabase db = getWritableDatabase();
		long rowcount = db.delete(HistoryEntry.TABLE_NAME, HistoryEntry.TABLE_NAME + "=?" + id, new String[]{id});
		db.close();
		return rowcount;
	}
	
	public long deleteAll() {
		SQLiteDatabase db = getWritableDatabase();
		long rowcount = db.delete(HistoryEntry.TABLE_NAME, null, null);
		db.close();
		return rowcount;
	}
}
