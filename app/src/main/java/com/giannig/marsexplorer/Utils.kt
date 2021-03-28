package com.giannig.marsexplorer

import kotlinx.coroutines.Job

fun Job?.cancelIfActive() {
    if(this != null){
        if (this.isActive){
            this.cancel()
        }
    }
}
