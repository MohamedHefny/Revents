package com.mohamedhefny.revent.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.mohamedhefny.revent.dataSource.entities.Event
import com.mohamedhefny.revent.dataSource.entities.Weather
import com.mohamedhefny.revent.dataSource.repositories.EventsRepo

class EventsViewModel : ViewModel() {

    /**
     * @return the events list related to the current user in a LiveData object
     **/
    fun getEvents(): LiveData<List<Event>>? {
        return EventsRepo.getEvents()
    }

    fun getWeather(): LiveData<Weather> {
        return EventsRepo.getWeatherForecast()
    }

    fun loadEvents() = EventsRepo.loadEvents()

    fun loadWeatherData(cityName: String) = EventsRepo.loadWeatherForecast(cityName)

    fun updateEventsWeatherStatus() = EventsRepo.updateEventsWithWeather()

    fun getLoadingStatus(): LiveData<Boolean> {
        return EventsRepo.isLoading()
    }
}