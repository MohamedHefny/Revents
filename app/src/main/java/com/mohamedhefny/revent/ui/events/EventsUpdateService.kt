package com.mohamedhefny.revent.ui.events

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.mohamedhefny.revent.dataSource.repositories.EventsRepo
import java.util.*


class EventsUpdateService : Service() {

    private var timer: Timer? = null
    private var timerTask: TimerTask? = null

    private val SYNC_INTERVAL: Long = 30 * 1000

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        startTimer()
        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    private fun startTimer() {
        //set a new Timer
        timer = Timer()

        //initialize the TimerTask's job
        initializeTimerTask()

        //schedule the timer, to wake up every 30 second
        timer!!.schedule(timerTask, 0, SYNC_INTERVAL)
    }

    /**
     * it sets the timer to update events every x seconds
     */
    private fun initializeTimerTask() {
        timerTask = object : TimerTask() {
            override fun run() {
                //TODO: CAll {EventsRepo.updateEvents()} instead of {EventsRepo.getEvents()}
                EventsRepo.getEvents()
            }
        }
    }

    private fun stopTimerTask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer!!.cancel()
            timer = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopTimerTask()
    }
}