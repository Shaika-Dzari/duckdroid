/*
 * HistoryFragment.java
 * 
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. 
 * See http://sam.zoy.org/wtfpl/COPYING for more details. 
 * 
 * @author shaika-dzari
 * @since 2012-09-26
 */ 
package net.nakama.duckdroid.ui.fragment;

import java.util.List;

import net.nakama.duckdroid.R;
import net.nakama.duckdroid.datamodel.HistoryEntry;
import net.nakama.duckdroid.ui.adapter.HistoryRowAdapter;
import net.nakama.duckdroid.ui.fragment.BangFragment.OnBangLineSelectedListener;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class HistoryFragment extends ListFragment {
	private List<HistoryEntry> historyList;
	private OnHistoryLineSelectedListener aCallback;
	
	public interface OnHistoryLineSelectedListener {
		public void onHistorySelect(String userQuery);
	}
	
	public HistoryFragment(List<HistoryEntry> historyList) {
		this.historyList = historyList;
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // We need to use a different list item layout for devices older than Honeycomb
        //int layout = android.R.layout.simple_list_item_activated_1;

        // Create an array adapter for the list view, using the Ipsum headlines array
        setListAdapter(new HistoryRowAdapter(getActivity(), R.layout.historyrow, this.historyList));
        
    }
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		try {
			aCallback = (OnHistoryLineSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnHistoryLineSelectedListener");
        }
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		HistoryEntry he = this.historyList.get(position);
		aCallback.onHistorySelect(he.userQuery);
	}
}
