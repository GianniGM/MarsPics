package com.giannig.marsexplorer.api.weatherDto


import androidx.annotation.Keep

@Keep
data class WeatherDto(
    val sol_keys: List<String> = listOf(),
)