/*
 * HistoryRowAdapter.java
 * 
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. 
 * See http://sam.zoy.org/wtfpl/COPYING for more details. 
 * 
 * @since 2012-10-08
 */ 
package net.nakama.duckdroid.ui.adapter;

import java.util.List;

import net.nakama.duckdroid.R;
import net.nakama.duckdroid.datamodel.HistoryEntry;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class HistoryRowAdapter extends ArrayAdapter<HistoryEntry> {

	private List<HistoryEntry> historyEntries;
	
	/**
	 * @param context
	 * @param resource
	 * @param textViewResourceId
	 * @param objects
	 */
	public HistoryRowAdapter(Context context, int textViewResourceId, List<HistoryEntry> historyEntries) {
		super(context, textViewResourceId, historyEntries);
		this.historyEntries = historyEntries;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		View view = convertView;
		if (view == null) {
			LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = li.inflate(R.layout.historyrow, null);
		} 
		
		HistoryEntry he = this.historyEntries.get(position);
		
		if (he != null) {
			((TextView)view.findViewById(R.id.hr_userquery)).setText(he.userQuery);
			((TextView)view.findViewById(R.id.hr_insertdate)).setText(he.insertdate);
		}
		
		return view;
	}
	
	public void clear() {
		this.historyEntries.clear();
		this.notifyDataSetChanged();
	}
	
	public void add(HistoryEntry h) {
		if (this.historyEntries != null) {
			this.historyEntries.add(h);
			this.notifyDataSetChanged();
		}
	}
}
