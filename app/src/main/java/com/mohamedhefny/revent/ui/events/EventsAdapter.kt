package com.mohamedhefny.revent.ui.events

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mohamedhefny.revent.R
import com.mohamedhefny.revent.dataSource.entities.Event
import kotlinx.android.synthetic.main.item_event.view.*

class EventsAdapter(private val eventsList: List<Event>) :
    RecyclerView.Adapter<EventsAdapter.EventViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_event,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.eventTime.text = eventsList[position].created
        holder.eventTitle.text = eventsList[position].summary
        holder.eventDetails.text = eventsList[position].description

        when (eventsList[position].status) {
            "confirmed" -> {
                holder.rejectBtn.visibility = View.GONE
                holder.statusConfirmed.visibility = View.VISIBLE
                holder.statusConfirmed.setText(R.string.confirmed)
            }
            "denied" -> {
                holder.acceptBtn.visibility = View.GONE
                holder.statusDenied.visibility = View.VISIBLE
                holder.statusDenied.setText(R.string.denied)
            }
            //TODO: Check if there is any other status!
        }
    }

    override fun getItemCount(): Int {
        return eventsList.size
    }

    class EventViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var eventTime = itemView.findViewById<TextView>(R.id.item_event_time_tv)
        var eventDate = itemView.findViewById<TextView>(R.id.item_event_date_tv)
        var eventTitle = itemView.findViewById<TextView>(R.id.item_event_title_tv)
        var eventDetails = itemView.findViewById<TextView>(R.id.item_event_details_tv)
        var weatherTemp = itemView.findViewById<TextView>(R.id.item_event_temp_tv)
        var weatherHum = itemView.findViewById<TextView>(R.id.item_event_hum_tv)
        var weatherIcon = itemView.findViewById<ImageView>(R.id.item_event_weather_ic)
        var acceptBtn = itemView.findViewById<ImageView>(R.id.item_event_accept_ic)
        var rejectBtn = itemView.findViewById<ImageView>(R.id.item_event_reject_ic)
        var statusConfirmed = itemView.findViewById<TextView>(R.id.item_event_confirmed)
        var statusDenied = itemView.findViewById<TextView>(R.id.item_event_status_denied)
    }
}