package com.valentimsts.threatzoo.presentation.core.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.valentimsts.threatzoo.presentation.home.HomeScreen
import com.valentimsts.threatzoo.presentation.contacts.ContactsScreen
import com.valentimsts.threatzoo.presentation.messages.MessagesScreen
import com.valentimsts.threatzoo.presentation.notifications.NotificationsScreen

@Composable
fun NavigationController(startDestination: String = Screen.Home.route) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar(navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen()
            }
            composable(Screen.Contacts.route) {
                ContactsScreen()
            }
            composable(Screen.Messages.route) {
                MessagesScreen()
            }
            composable(Screen.Notifications.route) {
                NotificationsScreen()
            }
        }
    }
}
