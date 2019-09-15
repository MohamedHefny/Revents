package com.mohamedhefny.revent.ui.setup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.android.gms.tasks.Task
import com.mohamedhefny.revent.R
import com.mohamedhefny.revent.ui.events.EventsActivity
import kotlinx.android.synthetic.main.activity_setup.*

/**
 * This is a simple signing in activity.
 * So, as we don't need to separate a data from ui
 * we can mix architectures and write a mini code in the activity
 **/

class SetupActivity : AppCompatActivity() {

    private lateinit var googleSignInClient: GoogleSignInClient

    private val RC_SIGN_IN = 9001

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setup)

        setup_login_btn.setOnClickListener {
            setup_loading_bar.visibility = View.VISIBLE

            prepareSigning()

            val signInIntent = googleSignInClient.signInIntent

            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    /**
     * Prepare signing and request scopes you need to access form the user account
     **/
    private fun prepareSigning() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.SERVER_CLIENT_ID))
            .requestScopes(Scope("https://www.googleapis.com/auth/calendar.events"))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    /**
     * This method handel the result of signing in. So, here you can update a ui, open events screen
     * or show error message in case of error
     **/
    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)

            if (account != null) { // Signed in successfully.
                startActivity(Intent(this, EventsActivity::class.java))
                finish()
            }
        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            Log.w("AccountException", "signInResult:failed code=" + e.statusCode)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        setup_loading_bar.visibility = View.GONE

        if (requestCode == RC_SIGN_IN)
            handleSignInResult(GoogleSignIn.getSignedInAccountFromIntent(data))
    }
}
