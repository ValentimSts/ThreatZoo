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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner

import com.valentimsts.threatzoo.presentation.core.permissions.PermissionHandler
import com.valentimsts.threatzoo.presentation.core.permissions.components.PermissionRequiredCard
import com.valentimsts.threatzoo.presentation.core.permissions.components.RationaleDialog
import com.valentimsts.threatzoo.presentation.core.permissions.findActivity
import com.valentimsts.threatzoo.presentation.core.permissions.openAppSettings

@Composable
fun ScreenContainer(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    permissionHandler: PermissionHandler? = null,
    content: @Composable () -> Unit
) {
    var isPermissionGranted by remember { mutableStateOf(permissionHandler?.isGranted() ?: true) }
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner, permissionHandler) {
        if (permissionHandler == null) return@DisposableEffect onDispose {}

        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                isPermissionGranted = permissionHandler.isGranted()
            }
        }

        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    var showRationaleDialog by remember { mutableStateOf(false) }
    var permissionRequested by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val activity = remember(context) { context.findActivity() }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            ScreenTitleBar(title, subtitle)

            if (permissionHandler == null || isPermissionGranted) {
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
                            if (!permissionRequested) {
                                permissionHandler.launch()
                                permissionRequested = true
                            } else {
                                showRationaleDialog = true
                            }
                        }
                    )
                }

                if (showRationaleDialog) {
                    val isPermanentlyDeclined = !permissionHandler.shouldShowRationale()
                    RationaleDialog(
                        isPermanentlyDeclined = isPermanentlyDeclined,
                        rationaleTextProvider = permissionHandler.rationaleTextProvider,
                        onConfirm = {
                            showRationaleDialog = false
                            if (isPermanentlyDeclined) {
                                activity.openAppSettings()
                            } else {
                                permissionHandler.launch()
                            }
                        },
                        onDismiss = { showRationaleDialog = false }
                    )
                }
            }
        }
    }
}


