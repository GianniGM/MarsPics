package com.example.marsexplorer.api.roverDto


import androidx.annotation.Keep

@Keep
data class PhotoDto(
    val camera: CameraDto,
    val earth_date: String = "", // 2015-05-30
    val id: Int = 0, // 102693
    val img_src: String = "", // http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01000/opgs/edr/fcam/FLB_486265257EDR_F0481570FHAZ00323M_.JPG
    val rover: RoverDto,
    val sol: Int = 0 // 1000
)