/*
 * ArticleFragment.java
 * 
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. 
 * See http://sam.zoy.org/wtfpl/COPYING for more details. 
 * 
 * @since 2012-10-04
 */ 
package net.nakama.duckdroid.ui.fragment;


import net.nakama.duckdroid.R;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class AbstractFragment extends Fragment {
	
	public static final String ABSTRACT_TEXT = "text";
	public static final String ABSTRACT_URL = "url";
	
	private String abstractText = null;
	private String abstractUrl = null;
	
	public AbstractFragment() {
	}
	
	public AbstractFragment(Bundle bundle) {
		this.abstractText = bundle.getString(ABSTRACT_TEXT);
		this.abstractUrl = bundle.getString(ABSTRACT_URL);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
       
		
        View v = inflater.inflate(R.layout.fragment_abstract, container, false);
		//TextView article = (TextView) getActivity().findViewById(R.id.article);
		//article.setText(Ipsum.Articles[position]);
		
		
		
		TextView textview = (TextView) v.findViewById(R.id.abstracttext);
		textview.setText(abstractText);
		textview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Uri webpage = Uri.parse(abstractUrl);
				Intent i = new Intent(Intent.ACTION_VIEW, webpage);
				startActivity(i);
			}
		});
		
		return v;
	}
}
