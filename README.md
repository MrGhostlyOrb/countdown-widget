# Countdown Widget

This is a simple countdown widget for Android 12.

## Pre-requisites

* Android 12 (API level 31)
* Android Studio Chipmunk or newer

## Getting Started

This project uses the Gradle build system. To build this project, use the
"gradlew build" command or use "Import Project" in Android Studio.

The countdown target time can be set in CountdownWidget.kt in UNIX timestamp format or entered in the widget's configuration settings at runtime. The default is set using the third parameter `1694347200000`

`val targetTime = prefs.getLong(PREF_TARGET_TIME + appWidgetId, 1694347200000)`

## Design
The app makes use of the Material You system accent colors to color the widget.


## Support

I encourage patches. You may submit them by forking this project and submitting a pull request
through GitHub. Please see CONTRIBUTING.md for more details.
