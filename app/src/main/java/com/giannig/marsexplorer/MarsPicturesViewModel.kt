package com.giannig.marsexplorer

import androidx.lifecycle.*
import com.giannig.marsexplorer.api.roverDto.MarsRoverImagesDto
import com.giannig.marsexplorer.api.roverDto.PhotoDto
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

// TODO: 28.03.21  
class MarsPicturesViewModel(
    private val nasaRoversRepository: NasaRoversRepository,
) : ViewModel() {

    private val mutableRoverImagesLiveData = MutableLiveData<RoversImagesData>()
    private var loadImagesJob: Job? = null

    val roverImagesLiveData: LiveData<RoversImagesData> = mutableRoverImagesLiveData

    // TODO: 28.03.21
    fun getRoverImages() {
        loadImagesJob.cancelIfActive()
        loadImagesJob = viewModelScope.launch {
            nasaRoversRepository.getRovers().collect {
                mutableRoverImagesLiveData.value = it
            }
        }
    }

}


