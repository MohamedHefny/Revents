package com.mohamedhefny.revent.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.mohamedhefny.revent.R
import com.mohamedhefny.revent.ui.events.EventsActivity
import com.mohamedhefny.revent.ui.setup.SetupActivity

class SplashActivity : AppCompatActivity() {

    private var isLoggedIn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        isLoggedIn = false

        val splashDelayTime: Long = 1000

        Handler().postDelayed({
            startNextActivity(isLoggedIn)
            finish()
        }, splashDelayTime)
    }


    /**This method check for the user state and start the SetupActivity if user is not loggedIn or EventsActivity otherwise*/
    private fun startNextActivity(isLoggedIn: Boolean = false) {
        if (isLoggedIn)
            startActivity(Intent(this@SplashActivity, EventsActivity::class.java))
        else
            startActivity(Intent(this@SplashActivity, SetupActivity::class.java))
    }
}
