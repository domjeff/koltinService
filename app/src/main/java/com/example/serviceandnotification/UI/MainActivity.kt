package com.example.serviceandnotification.UI

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.serviceandnotification.Others.Constants.SHOW_FOREGROUND_SERVICE1_FRAGMENT
import com.example.serviceandnotification.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var navHostFragment: NavHostFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navHostFragment.findNavController()

        navigateToForegroundService1FragmentIfNeeded(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        navigateToForegroundService1FragmentIfNeeded(intent)
    }

    private fun navigateToForegroundService1FragmentIfNeeded(intent: Intent?) {
        if (intent?.action == SHOW_FOREGROUND_SERVICE1_FRAGMENT) {
            navHostFragment.findNavController()
                .navigate(R.id.action_global_foreground_service_fragment)
        }
    }
}