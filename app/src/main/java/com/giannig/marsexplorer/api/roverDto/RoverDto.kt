package com.giannig.marsexplorer.api.roverDto

import androidx.annotation.Keep

@Keep
data class RoverDto(
    val id: Int = 0, // 5
    val landing_date: String = "", // 2012-08-06
    val launch_date: String = "", // 2011-11-26
    val name: String = "", // Curiosity
    val status: String = "" // active
)