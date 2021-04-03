package com.giannig.marsexplorer

import androidx.lifecycle.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

// TODO: 28.03.21  
class MarsPicturesViewModel(
    private val nasaRoversRepository: NasaRoversRepository,
) : ViewModel() {

    private val mutableRoverImagesLiveData = MutableLiveData<UIState>()
    private var loadImagesJob: Job? = null

    val roverImagesLiveData: LiveData<UIState> = mutableRoverImagesLiveData

    // TODO: 28.03.21
    fun getRoverImages() {
        loadImagesJob.cancelIfActive()
        loadImagesJob = viewModelScope.launch {
            nasaRoversRepository
                .getRovers()
                .map { it.toUIState() }
                .collect {
                    mutableRoverImagesLiveData.value = it
                }
        }
    }

    private fun RoversImagesData.toUIState(): UIState = when (this) {
        is RoversImagesData.Error -> UIState.Error(errorMessage)
        RoversImagesData.NoData -> UIState.ShowList(emptyList())
        is RoversImagesData.ShowImage -> UIState.ShowList(
            roversImageList.map {photo ->
                UIImage(
                    photo.img_src.replace("http://", "https://"),
                    photo.rover.name
                )
            }
        )
    }
}

sealed class UIState {
    data class Error(val errorMessage: String) : UIState()
    object Loading : UIState()
    data class ShowList(val imageList: List<UIImage>) : UIState()
}

data class UIImage(
    val url: String,
    val roverName: String
)


