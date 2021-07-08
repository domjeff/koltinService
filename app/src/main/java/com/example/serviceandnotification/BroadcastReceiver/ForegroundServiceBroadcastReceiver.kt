package com.example.serviceandnotification.BroadcastReceiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.serviceandnotification.UI.ForegroundService1Fragment
import kotlinx.android.synthetic.main.foreground_service_fragment.*

class ForegroundServiceBroadcastReceiver
    (lmd: (String) -> Unit = {}) :
    BroadcastReceiver() {


//    var serviceStatus: EditText = foregroundService1Fragment.etServiceStatus

    private var lambdaAction = lmd

    override fun onReceive(p0: Context?, p1: Intent?) {

        when (p1?.action) {
            "com.example.serviceandnotification.UI" -> {
                Log.e("aa", "onReceive triggered ")
//                serviceStatus.setText(p1?.getStringExtra("CURRENT CLOCK"))
                lambdaAction(p1?.getStringExtra("CURRENT CLOCK")!!)
                Log.e("This is a tag", "onReceive: ${p1?.getStringExtra("CURRENT CLOCK")}")
            }
            else -> {
                Log.e("This is a tag", "Action is unknown ")
            }
        }
    }
}