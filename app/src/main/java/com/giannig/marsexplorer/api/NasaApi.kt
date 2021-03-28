package com.giannig.marsexplorer.api

import com.giannig.marsexplorer.api.roverDto.RoverDto
import com.giannig.marsexplorer.api.weatherDto.WeatherDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


const val MARS_WEATHER_ELYSIUM = "https://api.nasa.gov/insight_weather/?api_key=DEMO_KEY&feedtype=json&ver=1.0"
const val MARS_ROVERS_API = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1000&api_key=DEMO_KEY"

// TODO: 28.03.21  
interface NasaApi {

    // TODO: 28.03.21  
    @GET("mars-photos/api/v1/rovers/{roverName}/photos")
    fun getMarsRoversImage(
        @Path("roverName") roverName: String,
        @Query("sol") solDistance: Int = 100,
        @Query("api_key") apiKey: String = API_KEY
    ) : RoverDto

    // TODO: 28.03.21  
    @GET("insight_weather")
    fun getMarsWeather(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("feedtype") feedType: String = "json",
        @Query("ver") version: String = "1.0",
    ): WeatherDto

    companion object {

        // TODO: 28.03.21  
        const val BASE_URL = "api.nasa.gov"
    }
}