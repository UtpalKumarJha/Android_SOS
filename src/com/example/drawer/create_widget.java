package com.example.drawer;



import android.graphics.Typeface;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.Button;
import android.widget.RemoteViews;

public class create_widget extends AppWidgetProvider
{

	//DBADapter db5;
	Button bt;
	RemoteViews jj;
	
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds)
    {

		RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.widget_layout);
		remoteViews.setOnClickPendingIntent(R.id.button3, buildButtonPendingIntent(context));       
        remoteViews.setOnClickPendingIntent(R.id.button1, buildButtonPendingIntent3(context));
        
		pushWidgetUpdate(context, remoteViews);
	}

	public static PendingIntent buildButtonPendingIntent(Context context)
    {
		Intent intent = new Intent();
	    intent.setAction("hellyeahh");
	    return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
	}
	
	
	public static PendingIntent buildButtonPendingIntent3(Context context)
    {
		Intent intent = new Intent(context,MainActivity.class);
	    return PendingIntent.getActivity(context, 0, intent,0);
	}

	public static void pushWidgetUpdate(Context context, RemoteViews remoteViews)
	 {
		ComponentName myWidget = new ComponentName(context, create_widget.class);
	    AppWidgetManager manager = AppWidgetManager.getInstance(context);
	    manager.updateAppWidget(myWidget, remoteViews);		
	 }
	
	
}
