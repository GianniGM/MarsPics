package com.giannig.marsexplorer

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

/**
 * Factory Implementation for View Models,
 * it injects database into MainViewModel
 *
 * NB: remove after dependency injection
 */
class ViewModelFactory : ViewModelProvider.Factory{

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MarsPicturesViewModel::class.java)){
            return MarsPicturesViewModel(NasaRoversRepository()) as T
        }
        throw IllegalArgumentException("Unknown Model class")
    }
}