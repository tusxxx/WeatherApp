package com.example.settings

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationCompat.VISIBILITY_PUBLIC
import androidx.core.app.NotificationManagerCompat
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.cities.CitiesRepository
import com.example.preferences.AppPreferenceManager
import com.example.preferences.model.TemperatureUnits
import com.example.weather.WeatherRepository
import com.example.weather.model.Units
import com.example.weather.model.Weather
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.firstOrNull


@HiltWorker
class WeatherNotificationWorkManager @AssistedInject constructor(
    @Assisted appContext: Context,
    @Assisted params: WorkerParameters,
    private val weatherRepository: WeatherRepository,
    private val cityRepository: CitiesRepository,
    private val preferences: AppPreferenceManager
) : CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        return try {
            createReminderNotification()
            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }

    private suspend fun createReminderNotification() {
        createNotificationChannel(applicationContext) // This won't create a new channel everytime, safe to call
        val weather = getWeatherForFirstCity()
        val notification = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(com.google.android.material.R.drawable.mtrl_ic_error)
            .setContentText("Today in ${weather?.cityName} will be ${weather?.temperature}")
            .setContentTitle("Weather notification")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setVisibility(VISIBILITY_PUBLIC)
            .build()

        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            throw RuntimeException("Notification permission not granted")
        }
        NotificationManagerCompat
            .from(applicationContext)
            .notify(1, notification)
    }

    private fun createNotificationChannel(context: Context) {
        val name = "Daily Weather"
        val descriptionText = "This channel sends daily weather"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        val notificationManager: NotificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private suspend fun getWeatherForFirstCity(): Weather? {
        val firstCity = cityRepository
            .getCities()
            .firstOrNull()
            ?.firstOrNull()
            ?: return null
        val units = when (preferences.temperature) {
            TemperatureUnits.CELSIUS -> Units.METRIC
            TemperatureUnits.FAHRENHEIT -> Units.IMPERIAL
            TemperatureUnits.KELVIN -> Units.METRIC
        }
        return weatherRepository.getWeather(firstCity.name, units).firstOrNull()
    }

    companion object {
        private const val CHANNEL_ID = "transactions_reminder_channel"
    }
}