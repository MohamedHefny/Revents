package com.mohamedhefny.revent.dataSource.repositories

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mohamedhefny.revent.dataSource.entities.Event
import com.mohamedhefny.revent.dataSource.entities.EventsData

/**
 * A singleton class acts as a single point of truth.
 * And it holds the data loaded from the remote or local source.
 **/
object EventsRepo {

    private var eventsList: MutableLiveData<List<Event>> = MutableLiveData()
    private val loadingState: MutableLiveData<Boolean> = MutableLiveData()

    //This variable will be used later to update events evey 30 seconds.
    private val updateSyncTime = 30000

    fun getEvents(): LiveData<List<Event>> {
        //TODO: Load events from Google Calendar API
        loadingState.postValue(true)

        Handler(Looper.getMainLooper()).postDelayed({
            val eventsSummary = "EmailAddress@domain.com"
            val eventsData = mutableListOf<Event>()
            val events = EventsData(eventsSummary, eventsData)

            //Create dummy events and add it to eventsList
            for (i in 1..7) {
                val event = Event(
                    i.toString(),
                    if (i % 2 == 1) "confirmed" else "denied",
                    "2019-0$i-22T1$i:00:49.000Z",
                    "2019-0$i-22T1$i:00:49.000Z",
                    "Event dummy title $i",
                    "This is a dummy description of the event to mock the data and test the app functionality",
                    "Cairo, Egypt"
                )
                eventsData.add(event)
            }

//            val eventsData = Gson().fromJson(dummyResponse, EventsData::class.java)
//            eventsList.postValue(eventsData.items)

            eventsList.postValue(events.items)
            loadingState.postValue(false)

        }, 1000)

        return eventsList
    }

    fun updateEvents() {
        //TODO: Load events list and compare it with the current list, if their are equal, then don't update ui
    }

    fun isLoading(): LiveData<Boolean> {
        return loadingState
    }
}