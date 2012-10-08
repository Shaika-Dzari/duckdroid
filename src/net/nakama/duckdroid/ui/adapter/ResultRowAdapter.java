/*
 * ResultRowAdapter.java
 * 
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. 
 * See http://sam.zoy.org/wtfpl/COPYING for more details. 
 * 
 * @author shaika-dzari
 * @since 2012-10-06
 */ 
package net.nakama.duckdroid.ui.adapter;

import java.util.List;

import net.nakama.duckdroid.R;
import net.nakama.duckdroid.datamodel.ResultRow;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ResultRowAdapter extends ArrayAdapter<ResultRow> {

	private List<ResultRow> rows;
	
	/**
	 * @param context
	 * @param resource
	 * @param textViewResourceId
	 * @param objects
	 */
	public ResultRowAdapter(Context context, int textViewResourceId, List<ResultRow> objects) {
		super(context, textViewResourceId, objects);
		
		this.rows = objects;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		if (view == null) {
			LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = li.inflate(R.layout.resultrow, null);
		} 
		
		ResultRow r = this.rows.get(position);
		
		if (r != null) {
			TextView text = (TextView) view.findViewById(R.id.text);
			TextView url = (TextView) view.findViewById(R.id.url);
			
			text.setText(r.text != null ? r.text : "");
			url.setText(r.url != null ? r.url : "");
		}
		
		return view;
	}

}
