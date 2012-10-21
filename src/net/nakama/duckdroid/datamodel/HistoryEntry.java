/*
 * HistoryEntry.java
 * 
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. 
 * See http://sam.zoy.org/wtfpl/COPYING for more details. 
 * 
 * @since 2012-10-07
 */ 
package net.nakama.duckdroid.datamodel;



import android.provider.BaseColumns;

public class HistoryEntry implements BaseColumns {
	public static final String TABLE_NAME = "duckdroidhistory";
	public static final String COLUMN_INSERTDATE = "insertdate";
	public static final String COLUMN_QUERY = "userquery";
	
	public String insertdate;
	public String userQuery;
	
	public HistoryEntry(String insertdate, String userQuery) {
		this.insertdate = insertdate;
		this.userQuery = userQuery;
	}
}
