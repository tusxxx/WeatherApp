package com.example.home


data class HomeScreenUiState(
    val query: String = "",
    val cities: List<CityUi> = emptyList(),
    val filteredCityNames: List<String> = emptyList(),
    val isLoading: Boolean = true
) {

    data class CityUi(
        val name: String,
        val temperature: Temperature,
        val iconUrl: String = ""
    ) {
        sealed class Temperature(open val value: Double) {
            data class Celsius(override val value: Double) : Temperature(value)
            data class Fahrenheit(override val value: Double) : Temperature(value)
        }
    }

    companion object {
        fun mockState() = HomeScreenUiState(
            query = "",
            cities = listOf(
                CityUi("Minsk", CityUi.Temperature.Celsius(10.0)),
                CityUi("Moscow", CityUi.Temperature.Fahrenheit(-20.0)),
                CityUi("Kiev", CityUi.Temperature.Celsius(10.0)),
                CityUi("London", CityUi.Temperature.Fahrenheit(-31.134)),
                CityUi("Paris", CityUi.Temperature.Celsius(120.0)),
                CityUi("Paris", CityUi.Temperature.Celsius(120.0)),
                CityUi("Paris", CityUi.Temperature.Celsius(120.0)),
                CityUi("Paris", CityUi.Temperature.Celsius(120.0)),
                CityUi("Paris", CityUi.Temperature.Celsius(120.0)),
                CityUi("Paris", CityUi.Temperature.Celsius(120.0)),
                CityUi("Paris", CityUi.Temperature.Celsius(120.0)),
                CityUi("Paris", CityUi.Temperature.Celsius(120.0)),
                CityUi("Paris", CityUi.Temperature.Celsius(120.0)),
                CityUi("Paris", CityUi.Temperature.Celsius(120.0)),
                CityUi("Paris", CityUi.Temperature.Celsius(120.0)),
            )
        )
    }
}
