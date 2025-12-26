package com.valentimsts.threatzoo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint

import com.valentimsts.threatzoo.presentation.core.navigation.NavigationController
import com.valentimsts.threatzoo.presentation.theme.ThreatZooTheme

@AndroidEntryPoint
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