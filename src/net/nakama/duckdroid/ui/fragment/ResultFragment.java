/*
 * ResultFragment.java
 * 
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. 
 * See http://sam.zoy.org/wtfpl/COPYING for more details. 
 * 
 * @since 2012-09-30
 */ 
package net.nakama.duckdroid.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import net.nakama.duckdroid.R;
import net.nakama.duckdroid.datamodel.ResultRow;
import net.nakama.duckdroid.ui.adapter.ResultRowAdapter;
import net.nakama.duckquery.net.response.ZeroClickResponse;
import net.nakama.duckquery.net.response.api.Result;

import android.app.ListFragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

public class ResultFragment extends ListFragment {

	private ZeroClickResponse result;
	
	public ResultFragment(ZeroClickResponse result) {
		this.result = result;
		setRetainInstance(true);
	}
	
	public ResultFragment() {
		this.result = new ZeroClickResponse(null);
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<ResultRow> rows = new ArrayList<ResultRow>();
        
        for (Result r : this.result.getFlatResults()) {
        	rows.add(new ResultRow(r.getText(), r.getUrl()));
        }
        
        setListAdapter(new ResultRowAdapter(getActivity(), R.layout.resultrow, rows));
    }
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		Result r = this.result.getFlatResults().get(position);
		Uri uri = Uri.parse(r.getUrl());
		Intent browser = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(browser);
	}
}
