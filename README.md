# Countdown Widget

This is a simple countdown widget for Android 12.

## How to use

The countdown target time can be set in CountdownWidget.kt in epoch time.

```kotlin
var targetTime = 1630000000L
```

## Design

The app makes use of the **Material You** system accent colors to color the widget.
```
<TextView
    <!--Background Colour (Adaptable Light/Dark mode)-->
    android:background="@drawable/app_widget_background"
    <!--Text Colour-->
    android:textColor="@android:color/system_accent1_200"
/>
```

## Development

The app is developed using Android Studio Arctic Fox 2020.3.1 Canary 12.

