package com.giannig.marsexplorer

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.giannig.marsexplorer.RoversImagesData.*
import com.giannig.marsexplorer.api.SpaceRovers
import com.giannig.marsexplorer.api.SpaceRovers.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

// TODO: 28.03.21  
class MarsPicturesViewModel(
    private val nasaRoversRepository: NasaRoversRepository,
) : ViewModel() {

    val mutableRoverState: MutableState<RoversImagesData> = mutableStateOf(Loading)
    private var loadImagesJob: Job? = null

    // TODO: 28.03.21
    fun getPicturesFrom(rover: SpaceRovers) {
        loadImagesJob.cancelIfActive()
        loadImagesJob = viewModelScope.launch {
            nasaRoversRepository
                .loadPicturesFromApi(rover)
                .collect {
                    mutableRoverState.value = it
                }
        }
    }

}


