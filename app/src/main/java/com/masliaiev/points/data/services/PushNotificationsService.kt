package com.masliaiev.points.data.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.masliaiev.points.R

class PushNotificationsService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(FIREBASE_EVENT_TAG, "FCM registration token has been updated -> $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val data = message.data
        showNotification(
            message.notification?.title,
            message.notification?.body,
            if (data.containsKey(KEY_CHANNEL_ID)) data[KEY_CHANNEL_ID]
                ?: GENERAL_CHANNEL_ID else GENERAL_CHANNEL_ID,
            message.messageId
        )

    }

    private fun showNotification(
        title: String?,
        text: String?,
        channelId: String,
        messageId: String?
    ) {
        val notificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val notificationChannel = NotificationChannel(
            channelId,
            when (channelId) {
                PRIVATE_CHANNEL_ID -> PRIVATE_CHANNEL_NAME
                else -> GENERAL_CHANNEL_NAME
            },
            NotificationManager.IMPORTANCE_HIGH
        )
        notificationManager.createNotificationChannel(notificationChannel)
        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(R.drawable.ic_map_pin)
            .build()
        notificationManager.notify(messageId?.hashCode() ?: DEFAULT_MESSAGE_ID, notification)
    }

    companion object {
        private const val FIREBASE_EVENT_TAG = "firebase_event"
        private const val DEFAULT_MESSAGE_ID = 1
        private const val KEY_CHANNEL_ID = "channelId"
        private const val GENERAL_CHANNEL_ID = "1"
        private const val GENERAL_CHANNEL_NAME = "General notifications"
        private const val PRIVATE_CHANNEL_ID = "2"
        private const val PRIVATE_CHANNEL_NAME = "Private notifications"
    }

}