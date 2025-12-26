package com.valentimsts.threatzoo.presentation.core

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.valentimsts.threatzoo.presentation.core.notifications.NotificationManager
import com.valentimsts.threatzoo.presentation.core.permissions.components.PermissionRequiredCard

@Composable
fun NotificationScreenContainer(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    notificationManager: NotificationManager,
    content: @Composable () -> Unit
) {
    var isAccessGranted by remember { mutableStateOf(notificationManager.isNotificationListenerEnabled()) }
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner, notificationManager) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                isAccessGranted = notificationManager.isNotificationListenerEnabled()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            ScreenTitleBar(title, subtitle)

            if (isAccessGranted) {
                content()
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    PermissionRequiredCard(
                        onGrantPermissionClick = {
                            notificationManager.requestNotificationListenerAccess()
                        }
                    )
                }
            }
        }
    }
}