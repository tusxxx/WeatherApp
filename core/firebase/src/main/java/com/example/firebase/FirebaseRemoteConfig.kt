package com.example.firebase

import com.example.firebase.model.FirebaseCities
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.serialization.json.Json

class FirebaseRemote() {
    private val remoteConfig = Firebase.remoteConfig

    init {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.fetchAndActivate()
    }

    fun fetchCities(): Flow<FirebaseCities> {
        return callbackFlow {
            remoteConfig.fetchAndActivate()
                .addOnSuccessListener {
                    val cities =
                        Json.decodeFromString<FirebaseCities>(remoteConfig.getString("cities"))
                    trySend(cities)
                }.addOnCanceledListener {
                    cancel()
                }.addOnCompleteListener {
                    channel.close()
                }
            awaitClose()
        }
    }
}
