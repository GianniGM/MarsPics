package com.giannig.marsexplorer.api.roverDto


import androidx.annotation.Keep

@Keep
data class CameraDto(
    val full_name: String = "", // Front Hazard Avoidance Camera
    val id: Int = 0, // 20
    val name: String = "", // FHAZ
    val rover_id: Int = 0 // 5
)