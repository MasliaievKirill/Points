package com.masliaiev.points.presentation.screens

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.masliaiev.points.presentation.core.theme.PointsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFullScreenMode()
        getFirebaseToken()

        setContent {
            PointsTheme {
                MainNavigation()
            }
        }
    }

    private fun setFullScreenMode() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }

    private fun getFirebaseToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.d(FIREBASE_EVENT_TAG, "Fetching FCM registration token failed", task.exception)
                return@addOnCompleteListener
            }
            val token = task.result
            Log.d(FIREBASE_EVENT_TAG, "FCM registration token -> $token")
        }
    }

    companion object {
        private const val FIREBASE_EVENT_TAG = "firebase_event"
    }

}
