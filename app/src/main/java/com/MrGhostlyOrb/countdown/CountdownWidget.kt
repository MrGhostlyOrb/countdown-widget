package com.MrGhostlyOrb.countdown

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import kotlin.math.abs

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
    val daysPositive1 = abs(days)
    val hoursPositive1 = abs(hours)

    //If value is 1 change to singular
    val daysString1 = if (daysPositive1 == 1L) "day" else "days"
    val hoursString1 = if (hoursPositive1 == 1L) "hour" else "hours"
    val location1 = "America"
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.countdown_widget)


    targetTime = 1664737200000 // October 2 2022
    timeDifference = targetTime - currentTime
    //Time difference in days, hours and minutes
    days = timeDifference / 86400000
    hours = (timeDifference % 86400000) / 3600000
    // Make values positive
    val daysPositive2 = Math.abs(days)
    val hoursPositive2 = Math.abs(hours)

    //If value is 1 change to singular
    val daysString2 = if (daysPositive2 == 1L) "day" else "days"
    val hoursString2 = if (hoursPositive2 == 1L) "hour" else "hours"
    val location2 = "Disney"
    // Construct the RemoteViews object
    views.setTextViewText(R.id.appwidget_text,
        location1 + " : " + daysPositive1 + " " + daysString1 + " " + hoursPositive1 + " " + hoursString1 + "\n\n" +
                location2 + " : " + daysPositive2 + " " + daysString2 + " " + hoursPositive2 + " " + hoursString2
    )

    // Instruct the widget manager to update the widget
    appWidgetManager.updateAppWidget(appWidgetId, views)
}