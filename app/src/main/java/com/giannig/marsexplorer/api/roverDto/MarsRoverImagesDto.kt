package com.giannig.marsexplorer.api.roverDto


import androidx.annotation.Keep

@Keep
data class MarsRoverImagesDto(
    val photos: List<PhotoDto> = listOf()
)