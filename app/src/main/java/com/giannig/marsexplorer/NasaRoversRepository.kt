package com.giannig.marsexplorer

import com.giannig.marsexplorer.api.NasaNetworkService
import com.giannig.marsexplorer.api.SpaceRovers
import com.giannig.marsexplorer.api.SpaceRovers.*
import com.giannig.marsexplorer.api.roverDto.PhotoDto
import kotlinx.coroutines.flow.*

// TODO: 28.03.21
class NasaRoversRepository {

    // https://proandroiddev.com/using-coroutines-and-flow-with-mvvm-architecture-796142dbfc2f

    // TODO: 28.03.21 persistency in db
    fun loadPicturesFromApi(rovers: SpaceRovers): Flow<RoversImagesData> = flow {
        emit(getPicturesFromRoverApi(rovers))
    }.catch {
        emit(RoversImagesData.Error("unable to fetch Mars Pictures"))
    }.onStart {
        emit(RoversImagesData.Loading)
    }

    private suspend fun getPicturesFromRoverApi(rovers: SpaceRovers): RoversImagesData =
        NasaNetworkService
            .getMarsRoverImagesFrom(CURIOSITY)
            .run {
                return if (photos.isEmpty()) {
                    RoversImagesData.EmptyData
                } else {
                    RoversImagesData.ShowImage(
                        roversImages = photos.replaceToHttps(),
                        roverName = rovers.roverName()
                    )
                }
            }

    /**
     * WTF NASA!!! you accept only http but you provide pics with http 😂
     */
    private fun List<PhotoDto>.replaceToHttps(): List<PhotoDto> = map {
        it.copy(
            img_src = it.img_src.replace(
                "http://",
                "https://"
            )
        )
    }
}


sealed class RoversImagesData {
    object Loading : RoversImagesData()
    data class ShowImage(
        val roversImages: List<PhotoDto>,
        val roverName: String
    ) : RoversImagesData()

    object EmptyData : RoversImagesData()
    data class Error(val errorMessage: String) : RoversImagesData()
}