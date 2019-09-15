package com.mohamedhefny.revent.dataSource.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mohamedhefny.revent.dataSource.entities.Event

object EventsRepo {

    private var eventsList: MutableLiveData<List<Event>> = MutableLiveData()

    fun getEvents(): LiveData<List<Event>> {
        //TODO Load events from Google Calendar API
        return eventsList
    }
}