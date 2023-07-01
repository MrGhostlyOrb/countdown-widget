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
        @LayoutRes layoutId: Int
    ) {
        context.getSharedPreferences(name = PREFS_NAME, mode = 0).edit {
            putInt(PREF_PREFIX_KEY, layoutId)
        }
    }

    internal fun loadWidgetLayoutIdPref(context: Context): Int =
        context.getSharedPreferences(name = PREFS_NAME, mode = 0)
            .getInt(PREF_PREFIX_KEY, R.layout.countdown)

    internal fun deleteWidgetLayoutIdPref(context: Context) {
        context.getSharedPreferences(name = PREFS_NAME, mode = 0).edit {
            remove(PREF_PREFIX_KEY)
        }
    }

    private fun Context.getSharedPreferences(name: String, mode: Int): SharedPreferences {
        return getSharedPreferences(name, mode)
    }

    fun saveTargetTimePref(context: Context, targetTime: Long) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putLong(PREF_TARGET_TIME, targetTime)
        editor.apply()
    }

    fun saveTargetPlacePref(context: Context, targetPlace: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(PREF_TARGET_PLACE, targetPlace)
        editor.apply()
    }
}