package net.nakama.duckdroid.ui;


import net.nakama.duckdroid.ui.fragment.MyPreferenceFragment;
import android.os.Bundle;
import android.app.Activity;

public class MyPreferenceActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new MyPreferenceFragment())
                .commit();
    }
}
