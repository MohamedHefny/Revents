package com.mohamedhefny.revent.dataSource.entities

import com.google.gson.annotations.SerializedName

/**
 * This file represents a weather entity to ued for events data
 **/

data class Weather(
    @SerializedName("list") val forecastList: List<DayForecast>
)

data class DayForecast(
    @SerializedName("dt") val date: Long,
    @SerializedName("temp") val temp: Temp,
    @SerializedName("pressure") val pressure: Float,
    @SerializedName("humidity") val humidity: Float,
    @SerializedName("weather") val condition: List<Condition>
)

data class Temp(
    @SerializedName("day") val dayTemp: Float,
    @SerializedName("min") val minTemp: Float,
    @SerializedName("max") val maxTemp: Float,
    @SerializedName("night") val nightTemp: Float,
    @SerializedName("eve") val eveTemp: Float,
    @SerializedName("morn") val mornTemp: Float
)

data class Condition(
    @SerializedName("id") val conditionId: Long,
    @SerializedName("main") val condition: String,
    @SerializedName("description") val conditionDescription: String,
    @SerializedName("icon") val icon: String
)