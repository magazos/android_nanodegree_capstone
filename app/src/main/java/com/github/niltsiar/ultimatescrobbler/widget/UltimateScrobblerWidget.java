package com.github.niltsiar.ultimatescrobbler.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import com.github.niltsiar.ultimatescrobbler.R;

public class UltimateScrobblerWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager manager, int appWidgetId) {
        CharSequence widgetText = context.getString(R.string.app_name);

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget_info);
        views.setTextViewText(R.id.appwidget_text, widgetText);
        views.setRemoteAdapter(R.id.widget_list, new Intent(context, UltimateScrobblerWidgetService.class));

        manager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        //Update all possible widgets
        for (int id : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, id);
        }
    }
}
