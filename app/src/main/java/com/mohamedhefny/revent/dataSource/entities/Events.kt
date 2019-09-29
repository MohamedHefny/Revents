package com.mohamedhefny.revent.dataSource.entities

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * This file represents a google event entity as a data class
 **/

data class Events(
    @SerializedName("summary")
    var summary: String,
    @SerializedName("items")
    var eventsList: List<Event>
)

data class Event(
    @SerializedName("id")
    var eventId: String,
    var status: String,
    var created: String,
    var updated: String,
    @SerializedName("summary")
    var eventTitle: String,
    var description: String,
    var location: String,
    @SerializedName("start")
    var startTime: EventTime,
    @SerializedName("end")
    var endTime: EventTime,
    @SerializedName("creator")
    var eventCreator: EventCreator,
    @Expose(serialize = false, deserialize = false)
    var weatherForecast: DayForecast?
)

data class EventTime(@SerializedName("dateTime") var eventTime: String)

data class EventCreator(
    @SerializedName("email") var creatorEmail: String,
    @SerializedName("email") var creatorName: String,
    @SerializedName("email") var self: Boolean
)