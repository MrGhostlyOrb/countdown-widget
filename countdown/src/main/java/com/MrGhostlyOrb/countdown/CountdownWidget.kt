/*
 * Copyright (C) 2021 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.MrGhostlyOrb.countdown

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import androidx.annotation.LayoutRes
import androidx.core.util.SizeFCompat
import androidx.core.widget.createResponsiveSizeAppWidget
import kotlin.math.abs

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

    override fun onDeleted(context: Context, appWidgetIds: IntArray) {
        // When the user deletes the widget, delete the preference associated with it.
        for (appWidgetId in appWidgetIds) {
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

            // Make values positive
            val daysPositive = abs(days)
            val hoursPositive = abs(hours)

            // If value is 1, change to singular
            val daysString = if (daysPositive == 1L) "day" else "days"
            val hoursString = if (hoursPositive == 1L) "hour" else "hours"
            val targetPlace = prefs.getString(PREF_TARGET_PLACE, "My Trip")

            // Construct the RemoteViews object
            val views = RemoteViews(context.packageName, R.layout.countdown)

            // Set the text in the widget
            views.setTextViewText(
                R.id.countdown_text,
                "$targetPlace: $daysPositive $daysString $hoursPositive $hoursString"
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
