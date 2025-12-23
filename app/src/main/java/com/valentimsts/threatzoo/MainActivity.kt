package com.valentimsts.threatzoo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import com.valentimsts.threatzoo.navigation.NavigationController
import com.valentimsts.threatzoo.ui.theme.ThreatZooTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ThreatZooTheme {
                NavigationController()
            }
        }
    }
}