package com.MrGhostlyOrb.countdown

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.LayoutRes
import androidx.core.content.edit

object CountdownSharedPrefsUtil {
    private const val PREFS_NAME = "com.MrGhostlyOrb.countdown"
    private const val PREF_PREFIX_KEY = "countdown_"
    private const val PREF_TARGET_TIME = "_TIME"
    private const val PREF_TARGET_PLACE = "_PLACE"

    internal fun saveWidgetLayoutIdPref(
        context: Context,
        appWidgetId: Int,
        @LayoutRes layoutId: Int
    ) {
        context.getSharedPreferences(name = PREFS_NAME, mode = 0).edit {
            putInt(PREF_PREFIX_KEY + appWidgetId, layoutId)
        }
    }

    internal fun loadWidgetLayoutIdPref(context: Context, appWidgetId: Int): Int =
        context.getSharedPreferences(name = PREFS_NAME, mode = 0)
            .getInt(PREF_PREFIX_KEY + appWidgetId, R.layout.countdown)

    internal fun deleteWidgetLayoutIdPref(context: Context, appWidgetId: Int) {
        context.getSharedPreferences(name = PREFS_NAME, mode = 0).edit {
            remove(PREF_PREFIX_KEY + appWidgetId)
        }
    }

    private fun Context.getSharedPreferences(name: String, mode: Int): SharedPreferences {
        return getSharedPreferences(name, mode)
    }

    fun saveTargetTimePref(context: Context, appWidgetId: Int, targetTime: Long) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putLong(PREF_TARGET_TIME + appWidgetId, targetTime)
        editor.apply()
    }

    fun saveTargetPlacePref(context: Context, appWidgetId: Int, targetPlace: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(PREF_TARGET_PLACE + appWidgetId, targetPlace)
        editor.apply()
    }
}