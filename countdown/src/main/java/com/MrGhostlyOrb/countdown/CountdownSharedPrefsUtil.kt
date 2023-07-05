package com.MrGhostlyOrb.countdown

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.annotation.LayoutRes
import androidx.core.content.edit

object CountdownSharedPrefsUtil {
    private const val PREFS_NAME = "com.MrGhostlyOrb.countdown"
    private const val PREF_PREFIX_KEY = "countdown_"
    private const val PREF_TARGET_TIME = "_TIME"
    private const val PREF_TARGET_PLACE = "_PLACE"
    private const val PREFS_CHANGED_ACTION = "com.MrGhostlyOrb.countdown.PREFS_CHANGED"

    internal fun saveWidgetLayoutIdPref(
        context: Context,
        @LayoutRes layoutId: Int
    ) {
        context.getSharedPreferences(name = PREFS_NAME, mode = 0).edit {
            putInt(PREF_PREFIX_KEY, layoutId)
        }
        sendPrefsChangedBroadcast(context)
    }

    internal fun deleteWidgetLayoutIdPref(context: Context) {
        context.getSharedPreferences(name = PREFS_NAME, mode = 0).edit {
            remove(PREF_PREFIX_KEY)
        }
        sendPrefsChangedBroadcast(context)
    }

    private fun sendPrefsChangedBroadcast(context: Context) {
        val intent = Intent(PREFS_CHANGED_ACTION)
        context.sendBroadcast(intent)
    }

    private fun Context.getSharedPreferences(name: String, mode: Int): SharedPreferences {
        return getSharedPreferences(name, mode)
    }

    fun saveTargetTimePref(context: Context, targetTime: Long) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putLong(PREF_TARGET_TIME, targetTime)
        editor.apply()
        sendPrefsChangedBroadcast(context)
    }

    fun saveTargetPlacePref(context: Context, targetPlace: String) {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putString(PREF_TARGET_PLACE, targetPlace)
        editor.apply()
        sendPrefsChangedBroadcast(context)
    }
}
