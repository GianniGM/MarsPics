package com.giannig.marsexplorer.api

import com.giannig.marsexplorer.api.roverDto.MarsRoverImagesDto
import com.giannig.marsexplorer.api.weatherDto.API_KEY
import com.giannig.marsexplorer.api.weatherDto.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


const val MARS_WEATHER_ELYSIUM = "https://api.nasa.gov/insight_weather/?api_key=DEMO_KEY&feedtype=json&ver=1.0"
const val MARS_ROVERS_API = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&api_key=DEMO_KEY"

interface NasaApi {

    @GET("mars-photos/api/v1/rovers/{roverName}/photos")
    suspend fun getMarsRoversImage(
        @Path("roverName") roverName: String,
        @Query("sol") solDistance: Int = 100,
        @Query("api_key") apiKey: String = API_KEY
    ) : MarsRoverImagesDto

    @GET("insight_weather")
    suspend fun getMarsWeather(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("feedtype") feedType: String = "json",
        @Query("ver") version: String = "1.0",
    ): WeatherDto

    companion object {
        const val BASE_URL = "https://api.nasa.gov/"
    }
}