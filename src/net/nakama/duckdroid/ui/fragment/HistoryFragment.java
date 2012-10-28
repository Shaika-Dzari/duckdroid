/*
 * HistoryFragment.java
 * 
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. 
 * See http://sam.zoy.org/wtfpl/COPYING for more details. 
 * 
 * @since 2012-09-26
 */ 
package net.nakama.duckdroid.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import net.nakama.duckdroid.R;
import net.nakama.duckdroid.datamodel.HistoryEntry;
import net.nakama.duckdroid.ui.adapter.HistoryRowAdapter;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ListView;

public class HistoryFragment extends ListFragment {
	private static final String HISTORYKEY = "hk";
	private List<HistoryEntry> historyList;
	private OnHistoryLineSelectedListener aCallback;
	private HistoryRowAdapter adapter;
	
	public interface OnHistoryLineSelectedListener {
		public void onHistorySelect(String userQuery);
	}
	
	public HistoryFragment() {
		historyList = new ArrayList<HistoryEntry>();
	}
	
	public HistoryFragment(List<HistoryEntry> historyList) {
		this.historyList = historyList;
	}
	
	public void clear() {
		if (this.historyList != null) {
			adapter.clear();
		}
	}
	
	public void add(HistoryEntry h) {
		adapter.add(h);
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
        	historyList = savedInstanceState.getParcelableArrayList(HISTORYKEY);
        }
        adapter = new HistoryRowAdapter(getActivity(), R.layout.historyrow, this.historyList);
        setListAdapter(adapter);
        
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
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putParcelableArrayList(HISTORYKEY, (ArrayList<? extends Parcelable>) this.historyList);
	}
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		HistoryEntry he = this.historyList.get(position);
		aCallback.onHistorySelect(he.userQuery);
	}
}
