package com.valentimsts.threatzoo.presentation.core.permissions

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext

@Composable
fun PermissionHandler(
    permission: Permission,
    onResult: (granted: Boolean) -> Unit
) {
    val context = LocalContext.current

    when (permission) {
        is RuntimePermission -> {
            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.RequestPermission()
            ) { granted ->
                onResult(granted)
            }

            LaunchedEffect(permission) {
                if (permission.isGranted(context)) {
                    onResult(true)
                } else {
                    launcher.launch(permission.permission)
                }
            }
        }

        is SpecialAccessPermission -> {
            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartActivityForResult()
            ) {
                onResult(permission.isGranted(context))
            }

            LaunchedEffect(permission) {
                if (permission.isGranted(context)) {
                    onResult(true)
                } else {
                    launcher.launch(permission.createIntent(context))
                }
            }
        }
    }
}