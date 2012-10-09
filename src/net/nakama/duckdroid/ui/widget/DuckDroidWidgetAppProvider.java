/*
 * DuckDroidWidgetAppProvider.java
 * 
 * This program is free software. It comes without any warranty, to
 * the extent permitted by applicable law. You can redistribute it
 * and/or modify it under the terms of the Do What The Fuck You Want
 * To Public License, Version 2, as published by Sam Hocevar. 
 * See http://sam.zoy.org/wtfpl/COPYING for more details. 
 * 
 * @author shaika-dzari
 * @since 2012-10-08
 */ 
package net.nakama.duckdroid.ui.widget;

import net.nakama.duckdroid.R;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

public class DuckDroidWidgetAppProvider extends AppWidgetProvider {
	
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		final int N = appWidgetIds.length;
		for (int i=0; i<N; i++) {
			int appWidgetId = appWidgetIds[i];
			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_duckdroid);
			
			appWidgetManager.updateAppWidget(appWidgetId, views);
		}
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}
	
}
