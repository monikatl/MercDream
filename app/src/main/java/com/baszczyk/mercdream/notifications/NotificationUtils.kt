package com.baszczyk.mercdream.notifications
//
//import android.app.NotificationManager
//import android.content.Context
//import androidx.core.app.NotificationCompat
//import com.baszczyk.mercdream.R
//
//
//private val NOTIFICATION_ID = 0
//
//fun NotificationManager.sendNotification(messageBody: String, applicationContext: Context){
//    val builder = NotificationCompat.Builder(
//        applicationContext,
//        applicationContext.getString(R.string.piggy_notification_channel_id)
//    )
//
//        .setSmallIcon(R.drawable.mercedes_logo)
//        .setContentTitle(applicationContext.getString(R.string.notification_title))
//        .setContentText(messageBody)
//
//    notify(NOTIFICATION_ID, builder.build())
//}