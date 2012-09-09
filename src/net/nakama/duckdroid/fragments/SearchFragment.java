/**
 * 
 */
package net.nakama.duckdroid.fragments;

import net.nakama.duckdroid.R;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author shaika-dzari
 *
 */
public class SearchFragment extends Fragment {
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }
	
	public void doQuery(View view) {
    	
    	System.out.println("doQuery");
    }
}
