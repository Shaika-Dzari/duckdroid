/*
 * DuckDroid.java
 * 
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. 
 * See http://sam.zoy.org/wtfpl/COPYING for more details. 
 * 
 * @since 2012-09-07
 */ 
package net.nakama.duckdroid.ui;

import java.util.Date;

import net.nakama.duckdroid.R;
import net.nakama.duckdroid.datamodel.HistoryEntry;
import net.nakama.duckdroid.net.DDGHttpClient;
import net.nakama.duckdroid.ui.fragment.HistoryFragment;
import net.nakama.duckdroid.ui.fragment.ResultFragment;
import net.nakama.duckdroid.ui.listeners.EventState;
import net.nakama.duckdroid.ui.listeners.ThreadCompletedListener;
import net.nakama.duckdroid.util.DateUtils;
import net.nakama.duckdroid.util.DuckDroidPreferenceKey;
import net.nakama.duckdroid.util.HistoryUtils;
import net.nakama.duckquery.net.request.Request;
//import net.nakama.duckquery.net.client.CallOption;
//import net.nakama.duckquery.net.client.DuckDuckGoOption;
//import net.nakama.duckquery.net.request.Request;
import net.nakama.duckquery.net.response.ZeroClickResponse;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Duckdroid extends FragmentActivity implements HistoryFragment.OnHistoryLineSelectedListener,
															   ThreadCompletedListener,
															   OnMenuItemClickListener {

	private static final String TAG = "Duckdroid";
	//private BangFragment bangFragment;
	private HistoryFragment historyFragment;
	private HistoryUtils historyUtils;
	
	// Preferences 
	private boolean prefWithHistory = true;
	private String prefBangProfile;
	private boolean prefBangSubmit = true;
	private boolean prefSafeoff = true;
	private int prefBangMenuId = -1;
	
	
	
	private OnMenuItemClickListener bangListener = new OnMenuItemClickListener() {
		
		
		@Override
		public boolean onMenuItemClick(MenuItem item) {
        	Log.i(TAG, "Doing bang query...");
        	String bang = null;
			switch (item.getItemId()) {
			case R.id.menu_bang_bakabt: bang = "!bakabt"; break;
	        case R.id.menu_bang_osub: bang = "!osub"; break;
	        case R.id.menu_bang_safeoff: bang = "!safeoff"; break;
	        case R.id.menu_bang_mdn: bang = "!mdn"; break;
	        case R.id.menu_bang_java: bang = "!java"; break;
	        case R.id.menu_bang_android: bang = "!android"; break;
	        case R.id.menu_bang_man: bang = "!man"; break;
	        case R.id.menu_bang_anidb: bang = "!anidb"; break;
	        case R.id.menu_bang_yt: bang = "!yt"; break;
	        case R.id.menu_bang_amca: bang = "!amca"; break;
	        case R.id.menu_bang_ncix: bang = "!ncix"; break;
			case R.id.menu_bang_metalstorm: bang = "!metalstorm"; break;
	        case R.id.menu_bang_xkcd: bang = "!xkcd"; break;
	        case R.id.menu_bang_gamespot: bang = "!gamespot"; break;
	        case R.id.menu_bang_minecraft: bang = "!minecraft"; break;
	        case R.id.menu_bang_slashdot: bang = "!/."; break;
	        case R.id.menu_bang_imdb: bang = "!imdb"; break;
	        case R.id.menu_bang_allmovie: bang = "!allmovie"; break;
	        case R.id.menu_bang_allocine: bang = "!allocine"; break;
	        case R.id.menu_bang_rt: bang = "!rt"; break;
	        case R.id.menu_bang_netflix: bang = "!netflix"; break;
	        case R.id.menu_bang_espn: bang = "!espn"; break;
	        case R.id.menu_bang_tvguide: bang = "!tvguide"; break;
	        case R.id.menu_bang_4chan: bang = "!4chan"; break;
	        case R.id.menu_bang_g: bang = "!g"; break;
	        case R.id.menu_bang_w: bang = "!w"; break;
	        case R.id.menu_bang_so: bang = "!so"; break;
	        case R.id.menu_bang_appledev: bang = "!appledev"; break;
	        case R.id.menu_bang_css: bang = "!css"; break;
	        case R.id.menu_bang_py: bang = "!py"; break;
	        case R.id.menu_bang_ruby: bang = "!ruby"; break;
	        case R.id.menu_bang_cpp: bang = "!cpp"; break;
	        case R.id.menu_bang_perl: bang = "!perl"; break;
	        case R.id.menu_bang_github: bang = "!github"; break;
	        case R.id.menu_bang_a: bang = "!a"; break;
	        case R.id.menu_bang_ebay: bang = "!ebay"; break;
	        case R.id.menu_bang_staples: bang = "!staples"; break;
	        case R.id.menu_bang_bestbuy: bang = "!bestbuy"; break;
	        case R.id.menu_bang_tigerdirect: bang = "!tigerdirect"; break;
	        case R.id.menu_bang_macys: bang = "!macys"; break;
	        case R.id.menu_bang_walmart: bang = "!walmart"; break;
	        case R.id.menu_bang_costco: bang = "!costco"; break;
	        case R.id.menu_bang_target: bang = "!target"; break;
	        case R.id.menu_bang_ikea: bang = "!ikea"; break;
	        case R.id.menu_bang_nasa: bang = "!nasa"; break;
	        case R.id.menu_bang_academic: bang = "!academic"; break;
	        case R.id.menu_bang_mathoverflow: bang = "!mathoverflow"; break;
	        case R.id.menu_bang_allrecipes: bang = "!allrecipes"; break;
	        case R.id.menu_bang_cdc: bang = "!cdc"; break;
	        case R.id.menu_bang_findlaw: bang = "!findlaw"; break;
	        case R.id.menu_bang_artist: bang = "!artist"; break;
	        case R.id.menu_bang_gutenberg: bang = "!gutenberg"; break;
	        case R.id.menu_bang_quotes: bang = "!quotes"; break;
	        case R.id.menu_bang_fd: bang = "!fd"; break;
	        case R.id.menu_bang_tripadvisor: bang = "!tripadvisor"; break;
	        case R.id.menu_bang_gt: bang = "!gt"; break;
	        case R.id.menu_bang_tw: bang = "!tw"; break;
	        case R.id.menu_bang_li: bang = "!li"; break;
	        case R.id.menu_bang_fb: bang = "!fb"; break;
	        case R.id.menu_bang_reddit: bang = "!reddit"; break;
	        case R.id.menu_bang_myspace: bang = "!myspace"; break;
	        case R.id.menu_bang_indeed: bang = "!indeed"; break;
	        case R.id.menu_bang_sh: bang = "!sh"; break;
	        case R.id.menu_bang_capost: bang = "!capost"; break;
	        case R.id.menu_bang_fedex: bang = "!fedex"; break;
	        case R.id.menu_bang_purolator: bang = "!purolator"; break;
	        case R.id.menu_bang_ups: bang = "!ups"; break;
	        case R.id.menu_bang_cbc: bang = "!cbc"; break;
	        case R.id.menu_bang_bbc: bang = "!bbc"; break;
	        case R.id.menu_bang_cnn: bang = "!cnn"; break;
	        case R.id.menu_bang_foxnews: bang = "!foxnews"; break;
	        case R.id.menu_bang_msnbc: bang = "!msnbc"; break;
	        case R.id.menu_bang_guardian: bang = "!guardian"; break;
	        case R.id.menu_bang_nyt: bang = "!nyt"; break;
	        case R.id.menu_bang_reuters: bang = "!reuters"; break;
	        case R.id.menu_bang_dailymotion: bang = "!dailymotion"; break;
	        case R.id.menu_bang_hulu: bang = "!hulu"; break;
	        case R.id.menu_bang_crunchyroll: bang = "!crunchyroll"; break;
	        case R.id.menu_bang_allmusic: bang = "!allmusic"; break;
	        case R.id.menu_bang_7digital: bang = "!7digital"; break;
	        case R.id.menu_bang_amazonmp3: bang = "!amazonmp3"; break;
	        case R.id.menu_bang_jamendo: bang = "!jamendo"; break;
	    	}
			setSearchQuery(bang, prefBangSubmit, true);
			return true;
		}
	};
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duckdroid);
        
        // Editext event
        EditText search = (EditText) findViewById(R.id.txt_search);
        search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_SEARCH) {
					String userQuery = v.getText().toString();
					search(userQuery, true);
					
		            return true;
		        }
		        return false;

			}
		});
        
        historyUtils = new HistoryUtils(this);
        
    }
    
    @Override
    public void onResume() {
    	super.onResume();
    	
    	SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
		this.prefWithHistory = sharedPref.getBoolean(DuckDroidPreferenceKey.PREFERENCE_HISTORY, true);
		this.prefBangProfile = ("menu_bang_" + sharedPref.getString(DuckDroidPreferenceKey.PREFERENCE_BANGPROFILE, "DuckDroid")).toLowerCase();
		this.prefBangSubmit = sharedPref.getBoolean(DuckDroidPreferenceKey.PREFERENCE_BANGSUBMIT, true);
		this.prefSafeoff = sharedPref.getBoolean(DuckDroidPreferenceKey.PREFERENCE_SAFEOFF, true);
		this.prefBangMenuId = getResources().getIdentifier(this.prefBangProfile, "menu", "net.nakama.duckdroid");
		
		
		// Fragment setup
        FragmentManager manager = getFragmentManager();
        FragmentTransaction trx = manager.beginTransaction();
       
        // Add History
        if (prefWithHistory && historyFragment == null) {
        	
        	historyFragment = new HistoryFragment(this.historyUtils.select());
        	trx.add(R.id.lt_main, historyFragment);        	
        } else if (!prefWithHistory && historyFragment != null) {
        	trx.remove(historyFragment);
        	historyFragment = null;
        }
        
        trx.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_duckdroid, menu);
      
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	
    	switch (item.getItemId()) {
        case R.id.menu_settings_s:
        	
            Intent settingIten = new Intent(this, MyPreferenceActivity.class);
            startActivity(settingIten);
            return true;
       
        default:
            return super.onOptionsItemSelected(item);
    	}
    }

    public void btnSearch(View view) {
        EditText txtQuery = (EditText) findViewById(R.id.txt_search);
        String userQuery = txtQuery.getText().toString();
        search(userQuery, true);
    }
    
    public void btnshowPopupSetting(View view) {
    	PopupMenu popup = new PopupMenu(this, view);
        MenuInflater inflater = popup.getMenuInflater();
        popup.setOnMenuItemClickListener(this);
        inflater.inflate(R.menu.activity_duckdroid, popup.getMenu());
        popup.show();
    }
    
    public void btnshowPopupBang(View view) {
    	
    	PopupMenu p = new PopupMenu(this,view);
    	MenuInflater inflater = p.getMenuInflater();
    	p.setOnMenuItemClickListener(bangListener);
    	
    	
        inflater.inflate(this.prefBangMenuId, p.getMenu());
        p.show();
    	
    }
    
    public void btnBangDdg(View view) {
    	setSearchQuery("!ddg", prefBangSubmit, true);
    }
    
    private void search(String query, boolean logToHistory) {
    	toggleLoading(true);
    	DDGHttpClient httpClient = new DDGHttpClient(this);
    	Request r = createRequest(query);
    	
    	httpClient.execute(r);
    	
    	if (logToHistory) 
    		addToHistory(r);    	
    }
    
    private Request createRequest(String query) {
    	Request r;
    	// TODO: This is ugly, DuckQuery should manage this part
    	if (query.toLowerCase().indexOf("!safeoff") == -1) {
    		r = Request.stdRequest(query);
    	} else {
    		query = query.toLowerCase().replace("!safeoff", "");
    		r = Request.stdRequest(query);
    		r.setSafeOff(true);
    	}
    	
    	return r;
    }
    
    
    private void addToHistory(Request r) {
    	
    	if (prefWithHistory) {
    		boolean ok = true;
    		
    		if (prefSafeoff && r.isSafeOff()) 
    			ok = false;
    		
    		if (ok) {
    			ContentValues v = new ContentValues();
    			v.put(HistoryEntry.COLUMN_QUERY, r.getQuery());
    			v.put(HistoryEntry.COLUMN_INSERTDATE, DateUtils.format(new Date()));
    			
    			historyUtils.insert(v);    		    			
    		}
    	}
    	
    }

	private void toggleLoading(boolean visible) {
		ProgressBar loading = (ProgressBar) findViewById(R.id.pb_loading);
		loading.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
	}

	/**
	 * 
	 * @param result
	 */
	private void manageResult(ZeroClickResponse result) {
		
		if (result.isBang() && result.getRedirect() != null && !result.getRedirect().equals("")) {
				startBrowserIntent(result.getRedirect());
				
		} else if (result.getFlatResults().size() == 0) {
			FragmentManager manager = getFragmentManager();
			FragmentTransaction trx = manager.beginTransaction();
			trx.remove(historyFragment);
			trx.commit();			
			
			TextView nt = new TextView(this);
			nt.setText("^_^ No result...");
			LinearLayout ll = (LinearLayout) findViewById(R.id.lt_main);
			ll.removeAllViews();
			ll.addView(nt);
			
		} else { 
			// Fragment setup
			LinearLayout ll = (LinearLayout) findViewById(R.id.lt_main);
			ll.removeAllViews();
			FragmentManager manager = getFragmentManager();
			FragmentTransaction trx = manager.beginTransaction();
			ResultFragment rf = new ResultFragment(result);
			//trx.replace(R.id.lt_main, rf);
			trx.add(R.id.lt_main, rf);
			trx.commit();			
		}
	}
	
	private void startBrowserIntent(String url) {
		Uri uri = Uri.parse(url);
		Intent browser = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(browser);
	}
	
	
	/* (non-Javadoc)
	 * @see net.nakama.duckdroid.ui.listeners.ThreadCompletedListener#onThreadCompleted(net.nakama.duckdroid.ui.listeners.EventState, java.lang.Object)
	 */
	@Override
	public void onThreadCompleted(EventState state, Object result) {
		if (result instanceof ZeroClickResponse) {
			manageResult((ZeroClickResponse)result);
		}
		toggleLoading(false);
	}
	
	private void setSearchQuery(String query, boolean submit, boolean addToCurrentValue) {
		EditText txtQuery = (EditText) findViewById(R.id.txt_search);
		String v = query;
		if (addToCurrentValue) {
			v = txtQuery.getText().toString() + " " + v;
		}
		txtQuery.setText(v);
		if (submit)
			search(v, false);
	}

	/* (non-Javadoc)
	 * @see net.nakama.duckdroid.ui.fragment.HistoryFragment.OnHistoryLineSelectedListener#onHistorySelect(java.lang.String)
	 */
	@Override
	public void onHistorySelect(String userQuery) {
		setSearchQuery(userQuery, true, false);
	}

	/* (non-Javadoc)
	 * @see android.widget.PopupMenu.OnMenuItemClickListener#onMenuItemClick(android.view.MenuItem)
	 */
	@Override
	public boolean onMenuItemClick(MenuItem item) {
		switch (item.getItemId()) {
        case R.id.menu_settings_s:
            Intent settingIten = new Intent(this, MyPreferenceActivity.class);
            startActivity(settingIten);
            break;
        case R.id.menu_clear_hist :
        	this.historyUtils.deleteAll();
            FragmentTransaction trx = getFragmentManager().beginTransaction();
            trx.remove(historyFragment);
            historyFragment = null;
            trx.commit();
            
        	break;
        case R.id.menu_about :
        	Intent about = new Intent(this, AboutActivity.class);
            startActivity(about);
        	break;
    	}
		
		return true;
	}
}
