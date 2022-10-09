package com.giannig.marsexplorer.mocks

import com.giannig.marsexplorer.api.roverDto.CameraDto
import com.giannig.marsexplorer.api.roverDto.PhotoDto
import com.giannig.marsexplorer.api.roverDto.RoverDto

//todo: what if compose starts to check images from an URL?
val photo = PhotoDto(
    camera = CameraDto(),
    earth_date = "", // 2015-05-30
    id = 102693, // 102693
    img_src = "http://mars.jpl.nasa.gov/msl-raw-images/proj/msl/redops/ods/surface/sol/01000/opgs/edr/fcam/FLB_486265257EDR_F0481570FHAZ00323M_.JPG",
    rover = RoverDto(),
    sol = 0 // 1000
)

fun generatePhotoList(count: Int): List<PhotoDto> =  (0..count).map { photo }