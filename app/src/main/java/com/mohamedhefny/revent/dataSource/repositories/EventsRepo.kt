package com.mohamedhefny.revent.dataSource.repositories

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mohamedhefny.revent.dataSource.entities.*
import com.mohamedhefny.revent.dataSource.remoteDataSource.retrofit.WeatherApiService
import com.mohamedhefny.revent.util.dateFormatter
import com.mohamedhefny.revent.util.dateToMillis
import com.mohamedhefny.revent.util.timeInRange
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * A singleton class acts as a single point of truth.
 * And it holds the data loaded from the remote or local source.
 **/
object EventsRepo {

    private var eventsList: MutableLiveData<List<Event>> = MutableLiveData()
    private var weatherForecast: MutableLiveData<Weather> = MutableLiveData()
    private val loadingState: MutableLiveData<Boolean> = MutableLiveData()

    /**
     * This function loads the events from the CalandarAPI
     **/
    fun loadEvents() {
        loadingState.postValue(true) //Start loading

        //TODO: Load events from Google Calendar API

        //Handler used to simulate the CalendarAPI calling.
        Handler(Looper.getMainLooper()).postDelayed({

            //Create dummy EventData object
            val eventsSummary = "EmailAddress@domain.com"
            val eventsData = mutableListOf<Event>()
            val events = Events(eventsSummary, eventsData)

            //Create dummy events and add it to eventsList
            for (i in 1..7) {
                val event = Event(
                    i.toString(),
                    if (i % 2 == 1) "confirmed" else "denied",
                    "2019-0$i-22T1$i:00:49.000Z",
                    "2019-0$i-22T1$i:00:49.000Z",
                    "Event dummy title $i",
                    "This is a dummy description of the event to mock the data and test the app functionality",
                    "Cairo, Egypt",
                    EventTime("2019-10-0${i}T11:00:00"),
                    EventTime("2019-10-0${i}T12:00:00"),
                    EventCreator("email@domain.com", "Creator", true),
                    null
                )
                eventsData.add(event)
            }

//            val eventsData = Gson().fromJson(dummyResponse, Events::class.java)
//            eventsList.postValue(eventsData.eventsList)

            eventsList.postValue(events.eventsList)

            loadingState.postValue(false) //Stop loading

            //Load weather data after loading events for cairo as this city name is assumed.
            loadWeatherForecast("cairo")

        }, 1000)
    }

    /**
     * Fetch weather data to be used in determine weather condition for events
     * @param cityName is the name of the city you want to get weather data for.
     **/
    fun loadWeatherForecast(cityName: String) {
        val apiService = WeatherApiService()
        GlobalScope.launch(Dispatchers.IO) {
            val weatherResponse = apiService.getForثcastWeather(cityName).await()
            weatherForecast.postValue(weatherResponse)
        }
    }

    /**
     * You should call this function after and only after the loadWeatherForecast() excuses,
     * This function update each event in the list with the weather condition if available.
     * */
    fun updateEventsWithWeather() {
        val tempEventsList = eventsList.value
        for (eventIndex in tempEventsList!!.withIndex()) {
            if (timeInRange(dateToMillis(eventIndex.value.startTime.eventTime.substringBefore("T"))))
                for (weather in weatherForecast.value!!.forecastList) {
                    val weatherDate = dateFormatter(weather.date)
                    val eventDate = eventIndex.value.startTime.eventTime.substringBefore("T")
                    if (weatherDate == eventDate) {
                        tempEventsList[eventIndex.index].weatherForecast = weather
                    }
                }
        }
        eventsList.postValue(tempEventsList)
    }

    fun getEvents(): LiveData<List<Event>> {
        return eventsList
    }

    fun getWeatherForecast(): LiveData<Weather> {
        return weatherForecast
    }

    fun isLoading(): LiveData<Boolean> {
        return loadingState
    }
}