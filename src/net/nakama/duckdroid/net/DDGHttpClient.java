/*
 * DDGHttpClient.java
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
package net.nakama.duckdroid.net;

import net.nakama.duckdroid.ui.listeners.EventState;
import net.nakama.duckdroid.ui.listeners.ThreadCompletedListener;
import net.nakama.duckquery.DuckQuery;
import net.nakama.duckquery.net.response.ZeroClickResponse;
import android.os.AsyncTask;
import android.util.Log;


public class DDGHttpClient extends AsyncTask<String, Void, ZeroClickResponse> {

	private static final String TAG = "DDGHttpClient";
	private ThreadCompletedListener mainCallbackListener;
	
	
	public DDGHttpClient(ThreadCompletedListener listener) {
		this.mainCallbackListener = listener;
	}
	
	/* (non-Javadoc)
	 * @see android.os.AsyncTask#doInBackground(Params[])
	 */
	@Override
	protected ZeroClickResponse doInBackground(String... params) {
		ZeroClickResponse response = new ZeroClickResponse();
		
		DuckQuery query = new DuckQuery();
		
		try {
			response = query.doQuery(params[0]);
			
		} catch (Exception e) {
			Log.e(TAG, e.getMessage());
		}
		
		return response;
	}
	
	@Override
    protected void onPostExecute(ZeroClickResponse result) {
        //In here, call back to Activity or other listener that things are done
		this.mainCallbackListener.onThreadCompleted(EventState.SUCCESS, result);
    }

	
}
