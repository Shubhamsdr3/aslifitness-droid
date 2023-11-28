package com.aslifitness.fitrackers.assistant.widgets

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import com.aslifitness.fitrackers.R


/**
 * Implementation of App Widget functionality.
 */
class StatsWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            val currentWidget = StatsWidget(context,
                appWidgetManager,
                appWidgetId,
                R.layout.stats_widget)
            currentWidget.updateAppWidget()
        }
    }
}

