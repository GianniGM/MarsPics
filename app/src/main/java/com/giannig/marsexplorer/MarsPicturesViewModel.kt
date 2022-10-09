package com.giannig.marsexplorer

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.giannig.marsexplorer.RoversImagesData.*
import com.giannig.marsexplorer.api.SpaceRover
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MarsPicturesViewModel(
    private val nasaRoversRepository: NasaRoversRepository,
) : ViewModel() {

    val mutableRoverState: MutableState<RoversImagesData> = mutableStateOf(Loading)
    private var loadImagesJob: Job? = null

    fun getPicturesFrom(rover: SpaceRover) {
        loadImagesJob.cancelIfActive()
        loadImagesJob = viewModelScope
            .launch {
                nasaRoversRepository
                    .loadPicturesFromApi(rover)
                    .collect {
                        mutableRoverState.value = it
                    }
            }
    }

}


