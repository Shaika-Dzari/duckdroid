/*
 * ResultFragment.java
 * 
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. 
 * See http://sam.zoy.org/wtfpl/COPYING for more details. 
 * 
 * @author shaika-dzari
 * @since 2012-09-30
 */ 
package net.nakama.duckdroid.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import net.nakama.duckquery.net.response.ZeroClickResponse;
import net.nakama.duckquery.net.response.api.ResponseType;
import net.nakama.duckquery.net.response.api.Result;
import net.nakama.duckquery.net.response.api.Topic;

import android.app.ListFragment;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class ResultFragment extends ListFragment {

	private ZeroClickResponse result;
	
	public ResultFragment(ZeroClickResponse result) {
		this.result = result;
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        List<String> l = new ArrayList<String>();
        
        if (this.result.getType() == ResponseType.D) {
        	
        	
        	for (Result r : this.result.getRelatedTopics().getResults()) {
        		l.add(r.getText() + " | " + r.getUrl());
        	}
        	
        	
        	for (Topic t : this.result.getRelatedTopics().getTopics()) {
        		l.add(t.getName() + " | " + t.getResults().size());
        	}
        }

        // We need to use a different list item layout for devices older than Honeycomb
        int layout = android.R.layout.simple_list_item_activated_1;

        // Create an array adapter for the list view, using the Ipsum headlines array
        setListAdapter(new ArrayAdapter<String>(getActivity(), layout, l));
    }
}
