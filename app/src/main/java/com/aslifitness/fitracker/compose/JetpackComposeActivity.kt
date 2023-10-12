package com.aslifitness.fitracker.compose

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
//import androidx.compose.ui.platform.ComposeView
import androidx.core.view.WindowCompat

class JetpackComposeActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
//        setContentView(
//            ComposeView(this).apply {
//
//            }
//        )
    }
}