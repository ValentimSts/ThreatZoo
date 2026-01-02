package com.valentimsts.threatzoo.presentation.core.permissions

import android.Manifest
import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Context.ACCESSIBILITY_SERVICE
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.text.TextUtils
import android.view.accessibility.AccessibilityManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.valentimsts.threatzoo.services.UninstallPreventionService

/**
 * Generic permission interface
 */
sealed interface Permission {
    /**
     * The Android permission constant
     *
     * @see android.Manifest.permission
     */
    val permission: String?

    /**
     * A human readable name for the permission
     */
    val name: String

    /**
     * A human readable description of the permission
     */
    val description: String


    /**
     * Returns true if the permission has been granted.
     */
    fun isGranted(context: Context): Boolean

    /**
     *
     */
    fun shouldShowRationale(context: Context): Boolean
}


/**
 * Represents a runtime permission.
 *
 * @property permission The Android permission constant.
 * @property description A description of the permission.
 */
class RuntimePermission(
    override val permission: String,
    override val name: String,
    override val description: String
) : Permission {

    override fun shouldShowRationale(context: Context): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(
            context.findActivity(),
            permission
        )
    }

    override fun isGranted(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }
}


/**
 * Represents a special access permission.
 *
 * @property permission The Android permission constant.
 * @property description A description of the permission.
 */
sealed class SpecialAccessPermission(
    override val permission: String,
    override val name: String,
    override val description: String
) : Permission {
    override fun shouldShowRationale(context: Context): Boolean = false

    abstract fun createIntent(context: Context): Intent
}

object BindNotificationListenerServicePermission : SpecialAccessPermission(
    permission = Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE,
    name = "Notification Access",
    description = "Allows the app to access notifications",
) {
    override fun isGranted(context: Context): Boolean {
        return NotificationManagerCompat.getEnabledListenerPackages(context)
            .contains(context.packageName)
    }

    override fun createIntent(context: Context): Intent {
        return Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
    }
}

object BindAccessibilityServicePermission : SpecialAccessPermission(
    permission = Manifest.permission.BIND_ACCESSIBILITY_SERVICE,
    name = "Accessibility Access",
    description = "Allows the app to configure accessibility features"
) {
    override fun isGranted(context: Context): Boolean {
        val am = context.getSystemService(ACCESSIBILITY_SERVICE) as AccessibilityManager
        return am.isEnabled
    }

    override fun createIntent(context: Context): Intent {
        return Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
}


fun Context.findActivity(): Activity {
    return when (this) {
        is Activity -> this
        is ContextWrapper -> baseContext.findActivity()
        else -> throw IllegalStateException("Context does not contain Activity")
    }
}

fun Activity.openAppSettings() {
    Intent(
        Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
        Uri.fromParts("package", packageName, null)
    ).also(::startActivity)
}
