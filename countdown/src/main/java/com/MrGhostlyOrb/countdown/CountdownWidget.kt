package com.MrGhostlyOrb.countdown

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import kotlin.math.abs

class CountdownWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        appWidgetIds.forEach { appWidgetId ->
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        appWidgetIds.forEach { _ ->
            CountdownSharedPrefsUtil.deleteWidgetLayoutIdPref(context)
        }
    }

    companion object {

        private const val REQUEST_CODE_OPEN_ACTIVITY = 1
        const val PREFS_NAME = "com.MrGhostlyOrb.countdown"
        const val PREF_TARGET_TIME = "_TIME"
        const val PREF_TARGET_PLACE = "_PLACE"

        internal fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            val targetTime = prefs.getLong(PREF_TARGET_TIME, 1694347200)
            val currentTime = System.currentTimeMillis() / 1000
            val timeDifference = targetTime - currentTime

            val days = timeDifference / 86400
            val hours = (timeDifference % 86400) / 3600

            // If value is 1, change to singular
            val daysString = if (days == 1L) "day" else "days"
            val hoursString = if (hours == 1L) "hour" else "hours"
            val targetPlace = prefs.getString(PREF_TARGET_PLACE, "My Trip")

            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.countdown)

            // Set the text in the widget
            views.setTextViewText(
                R.id.countdown_text,
                "$targetPlace: ${abs(days)} $daysString ${abs(hours)} $hoursString"
            )

            val activityIntent = Intent(context, CountdownWidget::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            val pendingIntent = PendingIntent.getActivity(
                context,
                REQUEST_CODE_OPEN_ACTIVITY,
                activityIntent,
                PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.countdown_text, pendingIntent)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}
