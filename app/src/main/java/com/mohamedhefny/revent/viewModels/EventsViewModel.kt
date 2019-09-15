package com.mohamedhefny.revent.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mohamedhefny.revent.dataSource.entities.Event

class EventsViewModel : ViewModel() {

    fun getEvents(): LiveData<List<Event>>? {
        //TODO return events object from repository later.
        return null
    }
}