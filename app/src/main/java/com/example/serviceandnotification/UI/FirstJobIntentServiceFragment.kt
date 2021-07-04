package com.example.serviceandnotification.UI

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.serviceandnotification.R
import kotlinx.android.synthetic.main.first_job_intent_service_fragment.*

class FirstJobIntentServiceFragment : Fragment(R.layout.first_job_intent_service_fragment) {
    private lateinit var firstIntent: Intent
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firstIntent = Intent(
            this.context, FirstJobIntentServiceFragment::class.java
        )
        FirstJobIntentService.enqueueWork(
            this.context,
            firstIntent
        )

        toService1.setOnClickListener {
            val action =
                FirstJobIntentServiceFragmentDirections.actionFirstJobIntentServiceFragmentToService1Fragment()
            findNavController().navigate(action)
        }
    }
}