package com.valentimsts.threatzoo.presentation.core.permissions

import android.content.Intent
import android.media.SubtitleData
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.valentimsts.threatzoo.presentation.core.components.ScreenTitleBar
import com.valentimsts.threatzoo.presentation.core.permissions.components.PermissionRequestCard

@Composable
fun PermissionScreenWrapper(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    permissions: List<Permission>,
    rationaleMessage: String = "The app needs these permissions to continue",
    allGrantedContent: @Composable () -> Unit
) {
    val context = LocalContext.current

    // Track granted states
    val grantedStates = remember {
        permissions.associateWith { mutableStateOf(it.isGranted(context)) }
    }

    // Track how many times each runtime permission was requested
    val requestCounts = remember { mutableMapOf<Permission, Int>() }

    // Current permission we should request with PermissionHandler
    var currentRequest by remember { mutableStateOf<Permission?>(null) }

    // Which permission needs rationale UI
    var rationalePerm by remember { mutableStateOf<Permission?>(null) }

    // Which permission should go to settings UI
    var settingsPerm by remember { mutableStateOf<Permission?>(null) }

    // Helper: next unmet permission
    val nextPending by remember {
        derivedStateOf {
            permissions.firstOrNull { !(grantedStates[it]?.value ?: false) }
        }
    }

    // True when we should trigger Android system question
    var triggerSystemRequest by remember { mutableStateOf(false) }

    // Helper to check if a permission is “permanently denied”
    fun isPermanentlyDenied(perm: Permission): Boolean {
        return perm is RuntimePermission
                && requestCounts.getOrDefault(perm, 0) >= 1
                && !perm.shouldShowRationale(context)
    }

    val allGranted = grantedStates.values.all { it.value }

    // UI
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            ScreenTitleBar(title, subtitle)

            if (allGranted) {
                allGrantedContent()
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    PermissionRequestCard(
                        missingPermissions = permissions.filter { !(grantedStates[it]?.value ?: false) },
                    ) {
                        // User tapped "Grant Permissions" button
                        val pending = nextPending
                        if (pending != null) {
                            when (pending) {
                                is RuntimePermission -> {
                                    // If permanently denied → settings flow
                                    if (isPermanentlyDenied(pending)) {
                                        settingsPerm = pending
                                    } else {
                                        // First click/second click → trigger system dialog
                                        triggerSystemRequest = true
                                    }
                                }
                                is SpecialAccessPermission -> {
                                    // Direct settings flow
                                    settingsPerm = pending
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // If system request should fire for a runtime perm
    if (triggerSystemRequest) {
        nextPending?.let { perm ->
            if (perm is RuntimePermission) {
                PermissionHandler(perm) { granted ->
                    grantedStates[perm]?.value = granted

                    // Count request attempts (for permanent denial logic)
                    val prev = requestCounts.getOrDefault(perm, 0)
                    requestCounts[perm] = prev + 1

                    triggerSystemRequest = false
                }
            }
        }
    }

    // Rationale dialog (optional; shown only after user denied once)
    rationalePerm?.let { perm ->
        AlertDialog(
            onDismissRequest = { rationalePerm = null },
            title = { Text("Permission Needed") },
            text = { Text(rationaleMessage) },
            confirmButton = {
                TextButton(onClick = {
                    rationalePerm = null
                    triggerSystemRequest = true
                }) { Text("Continue") }
            },
            dismissButton = {
                TextButton(onClick = {
                    rationalePerm = null
                    settingsPerm = perm
                }) { Text("Settings") }
            }
        )
    }

    // Settings dialog
    settingsPerm?.let { perm ->
        AlertDialog(
            onDismissRequest = { settingsPerm = null },
            title = { Text("Permission Required") },
            text = { Text("Please enable ${perm.name} in settings to continue.") },
            confirmButton = {
                TextButton(onClick = {
                    settingsPerm = null
                    val intent = when (perm) {
                        is RuntimePermission -> Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            .apply { data = Uri.fromParts("package", context.packageName, null) }

                        is SpecialAccessPermission -> perm.createIntent(context)

                        else -> Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                            .apply { data = Uri.fromParts("package", context.packageName, null) }
                    }
                    context.startActivity(intent)
                }) { Text("Open Settings") }
            },
            dismissButton = {
                TextButton(onClick = { settingsPerm = null }) { Text("Cancel") }
            }
        )
    }
}


