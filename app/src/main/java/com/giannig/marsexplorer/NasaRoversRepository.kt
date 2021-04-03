package com.giannig.marsexplorer

import com.giannig.marsexplorer.api.NasaNetworkService
import com.giannig.marsexplorer.api.roverDto.PhotoDto
import kotlinx.coroutines.flow.*

// TODO: 28.03.21
class NasaRoversRepository {

    // https://proandroiddev.com/using-coroutines-and-flow-with-mvvm-architecture-796142dbfc2f

    // TODO: 28.03.21 persistency in db
    fun getRovers(): Flow<RoversImagesData> = flow {
        emit(getRoversFromApi())
    }.catch {
        emit(RoversImagesData.Error("unable to fetch Mars Pictures"))
    }

    private suspend fun getRoversFromApi(): RoversImagesData = NasaNetworkService
        .getMarsRoverImages()
        .run {
            return if (photos.isEmpty()) {
                RoversImagesData.NoData
            } else {
                RoversImagesData.ShowImage(photos)
            }
        }
}

sealed class RoversImagesData {
    data class ShowImage(val roversImageList: List<PhotoDto>) : RoversImagesData()
    object NoData : RoversImagesData()
    data class Error(val errorMessage: String) : RoversImagesData()
}