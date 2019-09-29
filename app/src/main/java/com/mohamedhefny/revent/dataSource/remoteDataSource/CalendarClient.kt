package com.mohamedhefny.revent.dataSource.remoteDataSource

import androidx.lifecycle.LiveData
import com.google.api.client.util.DateTime
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.calendar.Calendar
import com.mohamedhefny.revent.dataSource.entities.Events
import java.io.IOException


class CalendarClient(token: String) {

    companion object {
        private val APPLICATION_NAME = "Revent"
        private val JSON_FACTORY = JacksonFactory.getDefaultInstance()
    }

    //TODO here is an issue. So, you need to solve the OAuth 2.0 problem and get a full working GoogleCredential object.
    private val credentials = GoogleCredential().setAccessToken(token)

    @Throws(IOException::class)
    fun getEventsList(): LiveData<List<Events>>? {
        // Build a new authorized API client service.
        val service = Calendar.Builder(NetHttpTransport(), JSON_FACTORY, credentials)
            .setApplicationName(APPLICATION_NAME)
            .build()

        // List the next 10 events from the primary calendar.
        val now = DateTime(System.currentTimeMillis())
        val events = service.events().list("primary")
            .setMaxResults(15)
            .setTimeMin(now)
            .setOrderBy("startTime")
            .setSingleEvents(true)
            .execute()
        val items = events.items
        if (items.isEmpty()) {
            println("No upcoming events found.")
        } else {
            //TODO handel event response and return as a LiveData object
        }
        return null
    }
}