package com.example.preferences

import android.content.Context
import androidx.preference.PreferenceManager

private const val DEFAULT_PREF_VALUE = "0"
private const val NOTIFICATION_KEY = "notification_preference"
private const val LANGUAGE_KEY = "language_preference"
private const val TEMPERATURE_KEY = "temperature_preference"
private const val WIND_KEY = "wind_preference"

class AppPreferenceManager(private val context: Context) {
    val notification
        get() = getNotificationPreference()
    val language
        get() = getPreferenceByKey(LANGUAGE_KEY).toLanguages()

    val temperature
        get() = getPreferenceByKey(TEMPERATURE_KEY).t0TemperatureUnits()
    val wind
        get() = getPreferenceByKey(WIND_KEY).toWindUnits()

    private fun getPreferenceByKey(key: String) =
        PreferenceManager.getDefaultSharedPreferences(context).getString(key, DEFAULT_PREF_VALUE)
            ?: DEFAULT_PREF_VALUE

    private fun getNotificationPreference() =
        PreferenceManager.getDefaultSharedPreferences(context).getBoolean(NOTIFICATION_KEY, true)
}