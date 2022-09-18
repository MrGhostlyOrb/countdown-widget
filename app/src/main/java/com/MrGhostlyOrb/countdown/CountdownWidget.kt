package com.MrGhostlyOrb.countdown

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews

/**
 * Implementation of App Widget functionality.
 */
class CountdownWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Enter relevant functionality for when the first widget is created
    }

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    // Get the time difference between now and September 28 2022
    val currentTime = System.currentTimeMillis()
    var targetTime = 1664373600000 // September 28 2022
    var timeDifference = targetTime - currentTime
    //Time difference in days, hours and minutes
    var days = timeDifference / 86400000
    var hours = (timeDifference % 86400000) / 3600000
    // Make values positive
    var daysPositive = Math.abs(days)
    var hoursPositive = Math.abs(hours)

    //If value is 1 change to singular
    var daysString = if (daysPositive == 1L) "day" else "days"
    var hoursString = if (hoursPositive == 1L) "hour" else "hours"
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.countdown_widget)
    views.setTextViewText(R.id.appwidget_text, "America : \n$daysPositive $daysString $hoursPositive $hoursString")


    targetTime = 1664737200000 // October 2 2022
    timeDifference = targetTime - currentTime
    //Time difference in days, hours and minutes
    days = timeDifference / 86400000
    hours = (timeDifference % 86400000) / 3600000
    // Make values positive
    daysPositive = Math.abs(days)
    hoursPositive = Math.abs(hours)

    //If value is 1 change to singular
    daysString = if (daysPositive == 1L) "day" else "days"
    hoursString = if (hoursPositive == 1L) "hour" else "hours"
    // Construct the RemoteViews object
    views.setTextViewText(R.id.appwidget_text2,
        "Disney : \n$daysPositive $daysString $hoursPositive $hoursString"
    )

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}