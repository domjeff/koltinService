package com.example.serviceandnotification.Services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_LOW
import android.app.PendingIntent
import android.app.PendingIntent.FLAG_UPDATE_CURRENT
import android.content.Context
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.CountDownTimer
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import com.example.serviceandnotification.Others.Constants.ACTION_PAUSE_FOREGROUND_SERVICE
import com.example.serviceandnotification.Others.Constants.ACTION_START_OR_RESUME_FOREGROUND_SERVICE
import com.example.serviceandnotification.Others.Constants.ACTION_STOP_FOREGROUND_SERVICE
import com.example.serviceandnotification.Others.Constants.FOREGROUND_SERVICE_CHANNEL_ID
import com.example.serviceandnotification.Others.Constants.FOREGROUND_SERVICE_CHANNEL_NAME
import com.example.serviceandnotification.Others.Constants.FOREGROUND_SERVICE_LOG_TAG
import com.example.serviceandnotification.Others.Constants.NOTIFICATION_ID
import com.example.serviceandnotification.Others.Constants.SHOW_FOREGROUND_SERVICE1_FRAGMENT
import com.example.serviceandnotification.R
import com.example.serviceandnotification.UI.MainActivity

class ForegroundService : LifecycleService() {

    var isFirstRun = true
    lateinit var thread: Thread;

    var timerCountdown: Long = 0
//    val binder = LocalBinder()

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when (it.action) {
                ACTION_START_OR_RESUME_FOREGROUND_SERVICE -> {
                    if (isFirstRun) {
                        startForegroundService()
                        isFirstRun = false
                        Log.d(FOREGROUND_SERVICE_LOG_TAG, "starting foreground service")
                        makeTimer(it).start()
                    } else {
                        Log.d(FOREGROUND_SERVICE_LOG_TAG, "resuming foreground service")
                    }
                }
                ACTION_PAUSE_FOREGROUND_SERVICE -> {
                    Log.d(FOREGROUND_SERVICE_LOG_TAG, "pausing foreground service")
                }
                ACTION_STOP_FOREGROUND_SERVICE -> {
                    Log.d(FOREGROUND_SERVICE_LOG_TAG, "stopping foreground service")
                }
                else -> Unit
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun startForegroundService() {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }

        val notificationBuilder =
            NotificationCompat.Builder(this, FOREGROUND_SERVICE_CHANNEL_ID)
                .setAutoCancel(false)
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_baseline_run_circle_24)
                .setContentTitle("Foreground Service Notification")
                .setContentText("This is description for foreground service notification")
                .setContentIntent(getMainActivityPendingIntent())

        startForeground(NOTIFICATION_ID, notificationBuilder.build())
    }

    private fun updateNotification(secondsRemaining: Long) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE)
                as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager)
        }

        val notificationBuilder =
            NotificationCompat.Builder(this, FOREGROUND_SERVICE_CHANNEL_ID)
                .setAutoCancel(false)
                .setOngoing(true)
                .setSmallIcon(R.drawable.ic_baseline_run_circle_24)
                .setContentTitle("Foreground Service Notification")
                .setContentText(
                    "This is description for foreground service notification: " +
                            "$secondsRemaining second(s) remaining"
                )
                .setContentIntent(getMainActivityPendingIntent())

        notificationManager.notify(
            NOTIFICATION_ID,
            notificationBuilder.build(),
        )
    }

    private fun getMainActivityPendingIntent() = PendingIntent.getActivity(
        this,
        0,
        Intent(this, MainActivity::class.java).also {
            it.action = SHOW_FOREGROUND_SERVICE1_FRAGMENT
        },
        FLAG_UPDATE_CURRENT
    )

    private fun makeTimer(intent: Intent?) = object : CountDownTimer(20 * 1000, 2 * 1000) {
//        init {
//            Log.e(FOREGROUND_SERVICE_LOG_TAG, "Making timer")
//        }

        override fun onTick(p0: Long) {
            timerCountdown = p0 / 1000
            updateNotification(timerCountdown)

            val sendBroadcastIntent = Intent();
            sendBroadcastIntent.action = "com.example.serviceandnotification.UI"
            sendBroadcastIntent.flags = Intent.FLAG_INCLUDE_STOPPED_PACKAGES
            sendBroadcastIntent.putExtra("CURRENT CLOCK", (p0 / 1000).toString())
            sendBroadcast(sendBroadcastIntent)


//            intent?.action = "com.example.serviceandnotification.UI";
//            intent?.putExtra("CURRENT CLOCK", p0 / 1000)
//            sendBroadcast(intent)

            Log.d(
                "Tag from service",
                "seconds remaining: " + p0 / 1000
            )
        }

        override fun onFinish() {
            intent?.putExtra("CURRENT CLOCK", "Service Finished")
            sendBroadcast(intent)
            this@ForegroundService.stopSelf()
        }
    }

//    inner class LocalBinder : Binder() {
//        fun getService() = this@ForegroundService
//    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationManager: NotificationManager) {
        val channel = NotificationChannel(
            FOREGROUND_SERVICE_CHANNEL_ID,
            FOREGROUND_SERVICE_CHANNEL_NAME,
            IMPORTANCE_LOW
        )
        notificationManager.createNotificationChannel(channel)
    }

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
//        return binder
        return null
    }


    companion object {

    }
}