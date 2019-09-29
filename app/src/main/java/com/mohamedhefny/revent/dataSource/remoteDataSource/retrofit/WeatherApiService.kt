package com.mohamedhefny.revent.dataSource.remoteDataSource.retrofit

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mohamedhefny.revent.dataSource.entities.Weather
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "ddb5dc9ab7513c5f4ae9e464f19ec7c4"

interface WeatherApiService {

    /**
     * @param cityName is the name of the city you want to request the weather data for.
     * @param daysNum id the number of days returned.
     * @return Weather forecast related to the next 16 days or {cnt} dayes.
     **/
    @GET("forecast/daily")
    fun getForثcastWeather(
        @Query("q") cityName: String,
        @Query("cnt") daysNum: Int = 16,
        @Query("units") unit: String = "metric"
    ): Deferred<Weather>

    companion object {
        operator fun invoke(): WeatherApiService {
            val requestInterceptor = Interceptor { chain ->

                val url = chain.request().url().newBuilder()
                    .addQueryParameter("appid", API_KEY).build()

                val request = chain.request().newBuilder().url(url).build()

                return@Interceptor chain.proceed(request)
            }

            val okHttpClient = OkHttpClient.Builder().addInterceptor(requestInterceptor).build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl("http://api.openweathermap.org/data/2.5/")
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherApiService::class.java)
        }
    }
}