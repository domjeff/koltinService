package com.example.serviceandnotification.UI

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.serviceandnotification.R
import com.example.serviceandnotification.Services.Service1
import kotlinx.android.synthetic.main.service1_fragment.*
import kotlinx.android.synthetic.main.service1_fragment.btnSendService
import kotlinx.android.synthetic.main.service1_fragment.btnStartService
import kotlinx.android.synthetic.main.service1_fragment.btnStopService
import kotlinx.android.synthetic.main.service1_fragment.etDataString
import kotlinx.android.synthetic.main.service1_fragment.etServiceStatus

class Service1Fragment : Fragment(R.layout.service1_fragment) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnStartService.setOnClickListener {
            Intent(context, Service1::class.java).also {
                val activity = activity
                activity!!.startService(it)
                etServiceStatus.setText("Service Running")
            }
        }
        btnStopService.setOnClickListener {
            Intent(context, Service1::class.java).also {
                val activity = activity
                activity!!.stopService(it)
                etServiceStatus.setText("Service Not Running")
            }
        }

        btnSendService.setOnClickListener {
            Intent(context, Service1::class.java).also {
                val dataString = etDataString.text.toString()
                it.putExtra("EXTRA DATA", dataString)
                val activity = activity
                activity!!.startService(it)
            }
        }

        btnToForegroundServiceFragment.setOnClickListener {
            val action =
                Service1FragmentDirections.actionService1FragmentToForegroundService1Fragment()
            findNavController().navigate(action)
        }

//        btnToForegroundServiceFragment.setOnClickListener {
//        }
    }
}