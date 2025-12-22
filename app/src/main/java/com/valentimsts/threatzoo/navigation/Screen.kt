package com.valentimsts.threatzoo.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(
    val route: String,
    val icon: ImageVector,
    val label: String
) {
    object Home : Screen("home", Icons.Filled.Home, "Home")
    object Contacts : Screen("contacts", Icons.Filled.Email, "Contacts")
    object Messages : Screen("messages", Icons.Filled.Phone, "Messages")
}
