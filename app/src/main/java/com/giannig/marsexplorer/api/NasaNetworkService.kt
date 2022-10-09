package com.giannig.marsexplorer.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*

object NasaNetworkService{
   
    private fun getClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.apply { interceptor.level = HttpLoggingInterceptor.Level.BODY }
        return OkHttpClient.Builder().addInterceptor(interceptor).build()
    }

    private val moshi = Moshi
        .Builder()
        .add(KotlinJsonAdapterFactory())
        .build()
    
    private val retrofit = Retrofit.Builder()
        .baseUrl(NasaApi.BASE_URL)
        .client(getClient())
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()
        .create(NasaApi::class.java)

    suspend fun getMarsRoverImagesFrom(rovers: SpaceRover) = retrofit.getMarsRoversImage(rovers.roverName())
}

enum class SpaceRover {
    CURIOSITY,
    OPPORTUNITY,
    SPIRIT,
    PERSEVERANCE;
    
    fun roverName(): String {
        return this.name.toLowerCase(Locale.ROOT)
    }
}