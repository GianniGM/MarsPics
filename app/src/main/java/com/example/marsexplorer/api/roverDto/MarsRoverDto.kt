package com.example.marsexplorer.api.roverDto


import androidx.annotation.Keep

@Keep
data class MarsRoverDto(
    val photos: List<PhotoDto> = listOf()
)