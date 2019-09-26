package com.mohamedhefny.revent.dataSource.entities

data class EventsData(var summary: String, var items: List<Event>)

data class Event(
    var id: String,
    var status: String,
    var created: String,
    var updated: String,
    var summary: String,
    var description: String,
    var location: String
)