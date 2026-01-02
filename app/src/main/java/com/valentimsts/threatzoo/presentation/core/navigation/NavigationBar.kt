package com.valentimsts.threatzoo.presentation.core.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun NavigationBar(navController: NavHostController) {
    val items = listOf(
        Screen.Home,
        Screen.Contacts,
        Screen.Messages,
        Screen.Notifications
    )

    NavigationBar(
        modifier = Modifier.clip(
            RoundedCornerShape(topStart = 36.dp, topEnd = 36.dp)
        )
    ) {
        // Observe current back stack entry
        val currentBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = currentBackStackEntry?.destination?.route

        items.forEach { item ->
            NavigationBarItem(

                icon = { Icon(item.icon, contentDescription = item.label) },
                label = { Text(item.label) },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        // Ensure we don’t have multiple copies of the same destination
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
