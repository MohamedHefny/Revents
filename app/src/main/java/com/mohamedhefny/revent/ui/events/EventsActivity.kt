package com.mohamedhefny.revent.ui.events

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.mohamedhefny.revent.R
import com.mohamedhefny.revent.dataSource.remoteDataSource.retrofit.WeatherApiService
import com.mohamedhefny.revent.viewModels.EventsViewModel
import kotlinx.android.synthetic.main.activity_events.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class EventsActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var eventsViewModel: EventsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)

        eventsViewModel = ViewModelProviders.of(this).get(EventsViewModel::class.java)

        observeEvents()
        observeWeather()
        observeLoading()

        events_refresh_layout.setOnRefreshListener(this)

        //Service to update events every 30 seconds
        startService(Intent(this, EventsUpdateService::class.java))
    }

    /**
     * Observe user events and update the ui when the events list loaded
     **/
    private fun observeEvents() {
        eventsViewModel.getEvents()?.observe(this, Observer {

            //Update ui with events list
            events_recycler.adapter = EventsAdapter(it)
        })
    }

    private fun observeWeather() {
        eventsViewModel.getWeather().observe(this, Observer {
            eventsViewModel.updateEventsWeatherStatus()
        })
    }

    /**
     * Observe loading state to update the ui with indicator
     **/
    private fun observeLoading() {
        eventsViewModel.getLoadingStatus().observe(this, Observer {
            events_refresh_layout.isRefreshing = it
        })
    }

    /**
     *Handle events refresh
     * */
    override fun onRefresh() {
        //Refresh and reload events again
        eventsViewModel.loadEvents()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this, EventsUpdateService::class.java))
    }
}
