package com.mohamedhefny.revent.dataSource.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mohamedhefny.revent.dataSource.entities.Event

/**
 * A singleton class acts as a single point of truth.
 * And it holds the data loaded from the remote or local source.
 **/
object EventsRepo {

    private var eventsList: MutableLiveData<List<Event>> = MutableLiveData()

    //This variable will be used later to update events evey 30 seconds.
    private val updateSyncTime = 30000

    fun getEvents(): LiveData<List<Event>> {
        //TODO Load events from Google Calendar API
        return eventsList
    }
}