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

import android.appwidget.AppWidgetManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.MrGhostlyOrb.countdown.databinding.ActivityWidgetConfigureBinding
import kotlinx.serialization.StringFormat

/**
 * The configuration screen for the [CountdownWidget] widget.
 */
class CountdownWidgetConfigureActivity : AppCompatActivity() {
    var appWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID

    public override fun onCreate(icicle: Bundle?) {
        super.onCreate(icicle)

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED)

        val binding = ActivityWidgetConfigureBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = this.getSharedPreferences(CountdownWidget.PREFS_NAME, Context.MODE_PRIVATE)
        val targetTime = prefs.getLong(CountdownWidget.PREF_TARGET_TIME, 1694347200000)

        //Get the view and set text
        binding.timeView.text = buildString {
            append("Time: ")
            append(targetTime)
        }
        binding.placeView.text =
            buildString {
                append("Place: ")
                append(prefs.getString(CountdownWidget.PREF_TARGET_PLACE, "My Trip"))
            }

        binding.save.setOnClickListener {
            val targetTimeValue:Long;
            try {
                targetTimeValue = binding.editTextNumber.text.toString().toLong()
                CountdownSharedPrefsUtil.saveTargetTimePref(this, targetTimeValue)
            } catch (_: Exception) {

            }
            try {
                val targetPlaceValue = binding.targetPlace.text.toString()
                if (targetPlaceValue != ""){
                    CountdownSharedPrefsUtil.saveTargetPlacePref(this, targetPlaceValue)
                }
            } catch (_: Exception) {

            }

            onWidgetContainerClicked(R.layout.countdown)
        }

        // Find the widget id from the intent.
        appWidgetId = intent?.extras?.getInt(
            AppWidgetManager.EXTRA_APPWIDGET_ID,
            AppWidgetManager.INVALID_APPWIDGET_ID
        ) ?: AppWidgetManager.INVALID_APPWIDGET_ID

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (appWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish()
            return
        }
    }

    private fun onWidgetContainerClicked(@LayoutRes widgetLayoutResId: Int) {
        // Save the value to the widget preferences
        CountdownSharedPrefsUtil.saveWidgetLayoutIdPref(this, widgetLayoutResId)
        // It is the responsibility of the configuration activity to update the app widget
        val appWidgetManager = AppWidgetManager.getInstance(this)
        CountdownWidget.updateAppWidget(this, appWidgetManager, appWidgetId)

        // Make sure we pass back the original appWidgetId
        val resultValue = Intent()
        resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
        setResult(RESULT_OK, resultValue)
        finish()
    }
}
