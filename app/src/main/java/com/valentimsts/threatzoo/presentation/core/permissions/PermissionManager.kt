package com.valentimsts.threatzoo.presentation.core.permissions

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat


abstract class PermissionHandler(
    val context: Context,
    val activity: Activity,
    val rationaleTextProvider: RationaleTextProvider
) {
    abstract fun isGranted(): Boolean
    abstract fun shouldShowRationale(): Boolean
    abstract fun launch()
}

class SinglePermissionHandler(
    context: Context,
    activity: Activity,
    rationaleTextProvider: RationaleTextProvider,
    private val launcher: ActivityResultLauncher<String>,
    private val permission: String,
) : PermissionHandler(context, activity, rationaleTextProvider) {
    /**
     * Returns true if the permission has been granted.
     */
    override fun isGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            context, permission
        ) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Returns true if the permission has been denied.
     */
    override fun shouldShowRationale(): Boolean {
        return activity.shouldShowRequestPermissionRationale(permission)
    }

    /**
     * Launches the permission request.
     */
    override fun launch() {
        launcher.launch(permission)
    }
}

class MultiplePermissionHandler(
    context: Context,
    activity: Activity,
    private val launcher: ActivityResultLauncher<Array<String>>,
    private val permissions: Map<String, RationaleTextProvider>
) {
    // TODO(): Implement this.
}


@Composable
fun rememberSinglePermissionHandler(
    permission: String,
    rationaleTextProvider: RationaleTextProvider,
    onPermissionResult: (isGranted: Boolean) -> Unit
) : SinglePermissionHandler {
    val context = LocalContext.current
    val activity = context.findActivity()

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = {
            onPermissionResult(it)
        }
    )

    return remember(permission, launcher) {
        SinglePermissionHandler(
            context = context,
            activity = activity,
            launcher = launcher,
            permission = permission,
            rationaleTextProvider = rationaleTextProvider
        )
    }
}

@Composable
fun RememberMultiplePermissionHandler(
    permissions: Array<String>,
    onPermissionResult: (isGranted: Boolean) -> Unit
) {
    // TODO(): Implement this.
}

fun Context.findActivity(): Activity {
    var currentContext = this
    while (currentContext is ContextWrapper) {
        if (currentContext is Activity) {
            return currentContext
        }
        currentContext = currentContext.baseContext
    }
    throw IllegalStateException("Permissions should be called in the context of an Activity")
}

fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}