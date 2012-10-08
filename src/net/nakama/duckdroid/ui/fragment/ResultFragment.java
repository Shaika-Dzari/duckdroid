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

import net.nakama.duckdroid.R;
import net.nakama.duckdroid.datamodel.ResultRow;
import net.nakama.duckdroid.ui.adapter.ResultRowAdapter;
import net.nakama.duckquery.net.response.ZeroClickResponse;
import net.nakama.duckquery.net.response.api.RelatedTopic;
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
        ResultRowAdapter adapter;
        List<ResultRow> rows = new ArrayList<ResultRow>();
        //ResultRow r;
        
        if (this.result.getAbstractText() != null) {
        	rows.add(new ResultRow(this.result.getAbstractText(), this.result.getAbstractURL()));
        }
        
        if (this.result.getResults() != null && this.result.getResults().size() > 0) {
        	for (Result r : this.result.getResults()) {
        		rows.add(new ResultRow(r.getText(), r.getUrl()));
        	}
        }
        
        if (this.result.getRelatedTopics() != null) {
        	RelatedTopic rt = this.result.getRelatedTopics();
        	
        	if (rt.getResults() != null && rt.getResults().size() > 0) {
            	for (Result r : rt.getResults()) {
            		rows.add(new ResultRow(r.getText(), r.getUrl()));
            	}
            }
        	
        	if (rt.getTopics() != null && rt.getTopics().size() > 0) {
        		
        		for (Topic t : rt.getTopics()) {
        			for (Result r : t.getResults()) {
                		rows.add(new ResultRow(r.getText(), r.getUrl()));
                	}
        		}
        		
        	}
        }
        
        adapter = new ResultRowAdapter(getActivity(), R.layout.resultrow, rows);
        
        setListAdapter(adapter);

        // Create an array adapter for the list view, using the Ipsum headlines array
        //setListAdapter(new ArrayAdapter<String>(getActivity(), layout, l));
    }
}
