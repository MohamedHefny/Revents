package com.mohamedhefny.revent.ui.events

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mohamedhefny.revent.R
import com.mohamedhefny.revent.viewModels.EventsViewModel
import kotlinx.android.synthetic.main.activity_events.*

class EventsActivity : AppCompatActivity() {

    private lateinit var eventsViewModel: EventsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_events)

        eventsViewModel = ViewModelProviders.of(this).get(EventsViewModel::class.java)

        observeEvents()
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
}
