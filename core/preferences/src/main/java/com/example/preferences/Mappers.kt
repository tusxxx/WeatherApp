package com.example.preferences

import com.example.preferences.model.Languages
import com.example.preferences.model.TemperatureUnits
import com.example.preferences.model.WindUnits

fun String.toLanguages() = when (this) {
    "0" -> Languages.ENGLISH
    "1" -> Languages.FRENCH
    else -> Languages.ENGLISH
}

fun String.t0TemperatureUnits() = when (this) {
    "0" -> TemperatureUnits.CELSIUS
    "1" -> TemperatureUnits.FAHRENHEIT
    "2" -> TemperatureUnits.KELVIN
    else -> TemperatureUnits.CELSIUS
}

fun String.toWindUnits() = when (this) {
    "0" -> WindUnits.KMPH
    "1" -> WindUnits.MPH
    else -> WindUnits.KMPH
}