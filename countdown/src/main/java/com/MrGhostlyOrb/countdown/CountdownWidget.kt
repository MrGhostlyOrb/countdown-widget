package com.MrGhostlyOrb.countdown

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.*
import android.widget.RemoteViews
import kotlin.math.abs

class CountdownWidget : AppWidgetProvider() {

    private lateinit var preferenceChangeListener: BroadcastReceiver

    override fun onEnabled(context: Context) {
        super.onEnabled(context)
        registerPreferenceChangeListener(context)
    }

    override fun onDisabled(context: Context) {
        super.onDisabled(context)
        unregisterPreferenceChangeListener(context)
    }

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            CountdownSharedPrefsUtil.deleteWidgetLayoutIdPref(context)
        }
    }

    private fun registerPreferenceChangeListener(context: Context) {
        preferenceChangeListener = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val action = intent.action
                if (action == PREFS_CHANGED_ACTION) {
                    val appWidgetManager = AppWidgetManager.getInstance(context)
                    val appWidgetIds =
                        appWidgetManager.getAppWidgetIds(ComponentName(context, CountdownWidget::class.java))
                    onUpdate(context, appWidgetManager, appWidgetIds)
                }
            }
        }
        val intentFilter = IntentFilter(PREFS_CHANGED_ACTION)
        context.registerReceiver(preferenceChangeListener, intentFilter)
    }

    private fun unregisterPreferenceChangeListener(context: Context) {
        context.unregisterReceiver(preferenceChangeListener)
    }

    companion object {
        private const val REQUEST_CODE_OPEN_ACTIVITY = 1
        const val PREFS_NAME = "com.MrGhostlyOrb.countdown"
        const val PREF_TARGET_TIME = "_TIME"
        const val PREF_TARGET_PLACE = "_PLACE"
        private const val PREFS_CHANGED_ACTION = "com.MrGhostlyOrb.countdown.PREFS_CHANGED"

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

            val daysPositive = abs(days)
            val hoursPositive = abs(hours)

            val daysString = if (daysPositive == 1L) "day" else "days"
            val hoursString = if (hoursPositive == 1L) "hour" else "hours"
            val targetPlace = prefs.getString(PREF_TARGET_PLACE, "My Trip")

            val views = RemoteViews(context.packageName, R.layout.countdown)
            views.setTextViewText(
                R.id.countdown_text,
                "$targetPlace: $daysPositive $daysString $hoursPositive $hoursString"
            )

            val activityIntent = Intent(context, MainActivity::class.java).apply {
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
