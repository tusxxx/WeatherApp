package com.example.settings

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.hilt.work.HiltWorkerFactory
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SettingsFragment : PreferenceFragmentCompat() {
    @Inject
    lateinit var workerFactory: HiltWorkerFactory

    private val postNotificationsPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            scheduleNotification()
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.main_preference, rootKey)

        preferenceManager.findPreference<Preference>("notification_preference")
            ?.setOnPreferenceChangeListener { preference, newValue ->
                if (newValue == true) {
                    if (!canPostNotifications()) {
                        requestNotificationPermission()
                    } else {
                        scheduleNotification()
                    }
                }
                true
            }
    }

    private fun scheduleNotification() {
        WorkManager.getInstance(requireContext()).apply {
            cancelAllWork()
            val request = PeriodicWorkRequestBuilder<WeatherNotificationWorkManager>(
                1,
                TimeUnit.DAYS
            ).build()
            enqueue(request)
        }
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            postNotificationsPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    private fun canPostNotifications(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true
        }
    }
}