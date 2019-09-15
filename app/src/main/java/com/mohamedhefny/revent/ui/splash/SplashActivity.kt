package com.mohamedhefny.revent.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.mohamedhefny.revent.R
import com.mohamedhefny.revent.ui.events.EventsActivity
import com.mohamedhefny.revent.ui.setup.SetupActivity

class SplashActivity : AppCompatActivity() {

    private var isLoggedIn = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val splashDelayTime: Long = 1000

        isLoggedIn = getCurrentUser() != null

        Handler().postDelayed({
            startNextActivity(isLoggedIn)
            finish()
        }, splashDelayTime)
    }

    /**
     * Inline method
     * @return the current signing in user or null if there is the first time to use the app
     **/
    private fun getCurrentUser(): GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(this)

    /**
     * This method check for the user state and start
     * the SetupActivity if user is not loggedIn or EventsActivity otherwise
     **/
    private fun startNextActivity(isLoggedIn: Boolean = false) {
        if (isLoggedIn)
            startActivity(Intent(this@SplashActivity, EventsActivity::class.java))
        else
            startActivity(Intent(this@SplashActivity, SetupActivity::class.java))
    }
}
