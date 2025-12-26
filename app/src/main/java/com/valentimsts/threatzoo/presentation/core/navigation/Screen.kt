package com.valentimsts.threatzoo.presentation.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CircleNotifications
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Sms
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    object Home : Screen("home", Icons.Filled.Home, "Home")
    object Contacts : Screen("contacts", Icons.Filled.Contacts, "Contacts")
    object Messages : Screen("messages", Icons.Filled.Sms, "Messages")
    object Notifications: Screen("notifications", Icons.Filled.CircleNotifications, "Notifications")
}
