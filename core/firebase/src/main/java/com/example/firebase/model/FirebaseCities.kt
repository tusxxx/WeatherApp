package com.example.firebase.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FirebaseCities(
    @SerialName("cities")
    val cities: List<City>
) {
    @Serializable
    data class City(
        @SerialName("name")
        val name: String
    )
}