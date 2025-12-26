package com.valentimsts.threatzoo.presentation.core.notifications

import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Manages access to the Notification Listener service.
 *
 * This class provides utility functions to check if the user has granted notification access
 * and to direct them to the system settings screen where they can grant the permission.
 * The BIND_NOTIFICATION_LISTENER_SERVICE permission is a special permission that cannot be
 * requested at runtime via the standard permission dialog.
 */
@Singleton
class NotificationManager @Inject constructor(
    @param:ApplicationContext private val context: Context
) {

    /**
     * Checks if the app has been granted notification listener access.
     *
     * @return `true` if access is enabled, `false` otherwise.
     */
    fun isNotificationListenerEnabled(): Boolean {
        return NotificationManagerCompat.getEnabledListenerPackages(context)
            .contains(context.packageName)
    }

    /**
     * Opens the system's Notification Access settings screen.
     *
     * This is the correct way to prompt the user to grant notification listener access.
     */
    fun requestNotificationListenerAccess() {
        val intent = Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
        // Required when starting from a non-Activity context
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}
