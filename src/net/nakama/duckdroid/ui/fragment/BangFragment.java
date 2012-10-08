/*
 * BangFragment.java
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


import net.nakama.duckdroid.R;
import android.app.ListFragment;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class BangFragment extends ListFragment {
	private String[] bangArray = null;
	
	public BangFragment(String[] bangArray) {
		this.bangArray = bangArray;
	}
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // We need to use a different list item layout for devices older than Honeycomb
        int layout = android.R.layout.simple_list_item_activated_1;
        //int layout2 = R.layout.bangrow;

        // Create an array adapter for the list view, using the Ipsum headlines array
        setListAdapter(new ArrayAdapter<String>(getActivity(), layout, bangArray));
    }
}
