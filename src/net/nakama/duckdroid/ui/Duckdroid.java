package net.nakama.duckdroid.ui;

import net.nakama.duckdroid.R;
import net.nakama.duckdroid.net.DDGHttpClient;
import net.nakama.duckdroid.ui.fragment.BangFragment;
import net.nakama.duckdroid.ui.fragment.HistoryFragment;
import net.nakama.duckdroid.ui.fragment.ResultFragment;
import net.nakama.duckdroid.ui.listeners.EventState;
import net.nakama.duckdroid.ui.listeners.ListSelectedListener;
import net.nakama.duckdroid.ui.listeners.ThreadCompletedListener;
import net.nakama.duckquery.net.response.ZeroClickResponse;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

public class Duckdroid extends FragmentActivity implements ListSelectedListener, ThreadCompletedListener {

	private static final String TAG = "Duckdroid";
	
	private class MySearchViewOnQueryListener implements SearchView.OnQueryTextListener {

		@Override
		public boolean onQueryTextChange(String newText) {
			return false;
		}

		@Override
		public boolean onQueryTextSubmit(String query) {
			search(query);
			return true;
		}
		
		
	}
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duckdroid);
        
        initialLoad();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_duckdroid, menu);
        
        MySearchViewOnQueryListener querySubmitListener = new MySearchViewOnQueryListener();
        
        // Set focus in searchwidget
        //ActionBar actionBar = getActionBar();
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setIconifiedByDefault(false);
        searchView.setFocusable(true);
        //searchView.requestFocus();
        
        //searchView.setOn
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

    private void search(String query) {
    	DDGHttpClient httpClient = new DDGHttpClient(this);
    	httpClient.execute(new String[]{query});
    }
    
	/* (non-Javadoc)
	 * @see net.nakama.duckdroid.ui.listeners.ListSelectedListener#onListSelected(int)
	 */
	@Override
	public void onListSelected(int position) {
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see net.nakama.duckdroid.ui.listeners.ThreadCompletedListener#onThreadCompleted(net.nakama.duckdroid.ui.listeners.EventState, java.lang.Object)
	 */
	@Override
	public void onThreadCompleted(EventState state, Object result) {
		if (result instanceof ZeroClickResponse) {
			//result = (ZeroClickResponse) result;
			
			displayResult((ZeroClickResponse)result);
		}
	}

	/**
	 * 
	 * @param result
	 */
	private void displayResult(ZeroClickResponse result) {
		// Fragment setup
        FragmentManager manager = getFragmentManager();
        FragmentTransaction trx = manager.beginTransaction();
        
        ResultFragment rf = new ResultFragment(result);
		
        trx.replace(R.id.rightpane, rf);
        trx.commit();
	}
	
	
	/**
	 * initialLoad
	 * 
	 * Add bang and history on load.
	 */
	private void initialLoad() {
		
		// Fragment setup
        FragmentManager manager = getFragmentManager();
        FragmentTransaction trx = manager.beginTransaction();
        
        // Add Bang fragment
        Fragment bang = new BangFragment();
        //manager.beginTransaction()
        
        // Add History
        Fragment hist = new HistoryFragment();
        
        
        trx.add(R.id.leftpane, bang);
        trx.add(R.id.rightpane, hist);
        trx.commit();
        
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
	}
}
