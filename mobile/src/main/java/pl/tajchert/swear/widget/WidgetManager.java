package pl.tajchert.swear.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.widget.RemoteViews;

import pl.tajchert.swear.R;

/**
 * Created by tajchert on 18.04.15.
 */
public class WidgetManager {
    private static final String TAG = "WidgetManager";

    public static void updateAppWidget(String swearText, Context context, AppWidgetManager appWidgetManager, int appWidgetId) {
        PendingIntent pendingIntent;
        RemoteViews views;
        if(swearText == null|| swearText.length() == 0) {
            swearText = "No access to location or Internet.";
        }
        views = new RemoteViews(context.getPackageName(), R.layout.widget_basic_medium);
        int textSize = (int) Math.floor(35-(swearText.length()*1.2-15));
        if(swearText.length() > 25) {
            textSize = 20;
        }
        views.setFloat(R.id.textView, "setTextSize", textSize);
        views.setTextViewText(R.id.textView, swearText);
        Intent weatherIntent = getWeatherAppIntent(context);
        if(weatherIntent != null) {
            pendingIntent = PendingIntent.getActivity(context, 0, weatherIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            views.setOnClickPendingIntent(R.id.mainLayout, pendingIntent);
            views.setOnClickPendingIntent(R.id.textView, pendingIntent);
        }
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    public static Intent getWeatherAppIntent(Context context) {
        String [] appPackageNames = {"com.google.android.apps.genie.geniewidget", "com.accuweather.android", "com.devexpert.weather", "com.weather.Weather", "com.yahoo.mobile.client.android.weather", "com.macropinch.swan"};
        PackageManager manager = context.getPackageManager();
        for(String packageName : appPackageNames) {
            Intent i = manager.getLaunchIntentForPackage(packageName);
            if(i != null) {
                return i;
            }
        }
        return null;
    }
}
