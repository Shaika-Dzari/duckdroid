package net.nakama.duckdroid.ui;

import java.util.Date;

import net.nakama.duckdroid.R;
import net.nakama.duckdroid.datamodel.HistoryEntry;
import net.nakama.duckdroid.net.DDGHttpClient;
import net.nakama.duckdroid.ui.fragment.BangFragment;
import net.nakama.duckdroid.ui.fragment.HistoryFragment;
import net.nakama.duckdroid.ui.fragment.ResultFragment;
import net.nakama.duckdroid.ui.listeners.EventState;
import net.nakama.duckdroid.ui.listeners.ThreadCompletedListener;
import net.nakama.duckdroid.util.DateUtils;
import net.nakama.duckdroid.util.DuckDroidPreferenceKey;
import net.nakama.duckdroid.util.HistoryUtils;
import net.nakama.duckquery.net.response.ZeroClickResponse;
import android.app.ActionBar;
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
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

public class Duckdroid extends FragmentActivity implements BangFragment.OnBangLineSelectedListener, 
															   HistoryFragment.OnHistoryLineSelectedListener,
															   ThreadCompletedListener {

	private static final String TAG = "Duckdroid";
	private MenuItem loadingItem;
	private BangFragment bangFragment;
	private HistoryFragment historyFragment;
	private SharedPreferences sharedPref;
	private HistoryUtils historyUtils;
	private boolean withHistory = true;
	private String bangProfile;
	private boolean bangSubmit = true;
	
	private class MySearchViewOnQueryListener implements SearchView.OnQueryTextListener {

		@Override
		public boolean onQueryTextChange(String newText) {
			return false;
		}

		@Override
		public boolean onQueryTextSubmit(String query) {
			search(query, true);
			return true;
		}
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duckdroid);
        
        historyUtils = new HistoryUtils(this);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        
        initialLoad();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_duckdroid, menu);
        loadingItem = menu.findItem(R.id.menu_loading);
        
        MySearchViewOnQueryListener querySubmitListener = new MySearchViewOnQueryListener();
        
        // Set focus in searchwidget
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setIconifiedByDefault(false);
        searchView.setFocusable(true);
        searchView.setOnQueryTextListener(querySubmitListener);
     
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

    private void search(String query, boolean logToHistory) {
    	loadingItem.setVisible(true);
    	DDGHttpClient httpClient = new DDGHttpClient(this);
    	httpClient.execute(new String[]{query});
    	
    	if (logToHistory) 
    		addToHistory(query);
    }
    
    private void addToHistory(String userQuery) {
    	ContentValues v = new ContentValues();
    	v.put(HistoryEntry.COLUMN_QUERY, userQuery);
    	v.put(HistoryEntry.COLUMN_INSERTDATE, DateUtils.format(new Date()));
    	
    	historyUtils.insert(v);
    }

	

	/**
	 * 
	 * @param result
	 */
	private void manageResult(ZeroClickResponse result) {
		
		if (result.isBang() && result.getRedirect() != null) {
				startBrowserIntent(result.getRedirect());
				
		} else {
			// Fragment setup
			FragmentManager manager = getFragmentManager();
			FragmentTransaction trx = manager.beginTransaction();
			ResultFragment rf = new ResultFragment(result);
			trx.replace(R.id.rightpane, rf);
			trx.commit();			
		}
	}
	
	private void startBrowserIntent(String url) {
		Uri uri = Uri.parse(url);
		Intent browser = new Intent(Intent.ACTION_WEB_SEARCH, uri);
		startActivity(browser);
	}
	
	/**
	 * initialLoad
	 * 
	 * Add bang and history on load.
	 */
	private void initialLoad() {
		
		this.withHistory = sharedPref.getBoolean(DuckDroidPreferenceKey.PREFERENCE_HISTORY, true);
		this.bangProfile = "bang_" + sharedPref.getString(DuckDroidPreferenceKey.PREFERENCE_BANGPROFILE, "DuckDroid");
		this.bangSubmit = sharedPref.getBoolean(DuckDroidPreferenceKey.PREFERENCE_BANGSUBMIT, true);
		
		// Fragment setup
        FragmentManager manager = getFragmentManager();
        FragmentTransaction trx = manager.beginTransaction();
        
        // Add Bang fragment
        
        int aid = getResources().getIdentifier(bangProfile, "array", getPackageName());
        
        bangFragment = new BangFragment(getResources().getStringArray(aid));
        
        trx.add(R.id.leftpane, bangFragment);
        //manager.beginTransaction()
        
        // Add History
        if (withHistory) {
        	
        	historyFragment = new HistoryFragment(this.historyUtils.select());
        	trx.add(R.id.rightpane, historyFragment);        	
        }
        
        trx.commit();
        
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
	}
	
	
	/* (non-Javadoc)
	 * @see net.nakama.duckdroid.ui.listeners.ThreadCompletedListener#onThreadCompleted(net.nakama.duckdroid.ui.listeners.EventState, java.lang.Object)
	 */
	@Override
	public void onThreadCompleted(EventState state, Object result) {
		if (result instanceof ZeroClickResponse) {
			manageResult((ZeroClickResponse)result);
		}
		loadingItem.setVisible(false);
	}

	/* (non-Javadoc)
	 * @see net.nakama.duckdroid.ui.fragment.BangFragment.OnBangLineSelectedListener#onBangSelected(java.lang.String)
	 */
	@Override
	public void onBangSelect(String bang) {
		setSearchQuery(bang, bangSubmit, true);
	}
	
	private void setSearchQuery(String query, boolean submit, boolean addToCurrentValue) {
		SearchView search = (SearchView) findViewById(R.id.menu_search);
		String v = query;
		if (addToCurrentValue) {
			v = search.getQuery().toString() + " " + v;
		}
		search.setQuery(v, submit);
	}

	/* (non-Javadoc)
	 * @see net.nakama.duckdroid.ui.fragment.HistoryFragment.OnHistoryLineSelectedListener#onHistorySelect(java.lang.String)
	 */
	@Override
	public void onHistorySelect(String userQuery) {
		setSearchQuery(userQuery, true, false);
	}
}
