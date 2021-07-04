package com.example.serviceandnotification.Services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class Service1 : Service() {
    override fun onBind(p0: Intent?): IBinder? = null

    private val TAG = "Service1 Tag"

    init {
        Log.d(TAG, "Service 1 is running")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val dataString = intent?.getStringExtra("EXTRA DATA")
        dataString?.let {
            Log.d(TAG, "onStartCommand: dataString is $dataString")
        }
        Thread {
            while (true) {
            }
        }.start()

        return START_STICKY
    }

}