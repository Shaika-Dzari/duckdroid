package net.nakama.duckdroid.ui.fragment;

import net.nakama.duckdroid.R;
import net.nakama.duckdroid.R.layout;
import net.nakama.duckdroid.R.menu;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;

public class MyPreferenceFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        addPreferencesFromResource(R.xml.preferences);
        
        //setContentView(R.layout.activity_preference);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
