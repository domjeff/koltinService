package com.example.serviceandnotification.UI

import android.content.*
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import com.example.serviceandnotification.BroadcastReceiver.ForegroundServiceBroadcastReceiver
import com.example.serviceandnotification.Others.Constants.ACTION_START_OR_RESUME_FOREGROUND_SERVICE
import com.example.serviceandnotification.R
import com.example.serviceandnotification.Services.ForegroundService
import kotlinx.android.synthetic.main.foreground_service_fragment.*


class ForegroundService1Fragment : Fragment(R.layout.foreground_service_fragment) {

    private lateinit var broadcastReceiver: ForegroundServiceBroadcastReceiver
//    private val broadcastReceiver = ForegroundServiceBroadcastReceiver(this)

    private lateinit var mService: ForegroundService
//    private var mBound: Boolean = false

//    private val connection = object : ServiceConnection {
//        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
//            val binder = p1 as ForegroundService.LocalBinder
//            mService = binder.getService()
//            mBound = true
//        }
//
//        override fun onServiceDisconnected(p0: ComponentName?) {
//            mBound = false
//        }
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        broadcastReceiver = ForegroundServiceBroadcastReceiver(this)
        val intentFilter = IntentFilter()
        intentFilter?.addAction("com.example.serviceandnotification.UI");
        requireContext().registerReceiver(broadcastReceiver, intentFilter)
        btnStartService.setOnClickListener {
            sendCommandToService(ACTION_START_OR_RESUME_FOREGROUND_SERVICE)

//            if (mBound) {
//                etServiceStatus.setText(mService.timerCountdown.toString())
//            }
        }
    }

    override fun onDestroyView() {
        requireContext().unregisterReceiver(broadcastReceiver)
        super.onDestroyView()
//        requireContext().unbindService(connection)
//        mBound = false
    }

    private fun sendCommandToService(action: String) {
        Intent(requireContext(), ForegroundService::class.java).also {
            it.action = action
            requireContext().startService(it)

//            bindServiceToUI(it)
        }
    }

//    private fun bindServiceToUI(intent: Intent) {
//        requireContext().bindService(intent, connection, Context.BIND_AUTO_CREATE)
//    }
}