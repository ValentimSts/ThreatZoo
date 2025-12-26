package com.valentimsts.threatzoo.presentation.notifications.permissions

import com.valentimsts.threatzoo.presentation.core.permissions.RationaleTextProvider

class BindNotificationListenerRationaleTextProvider : RationaleTextProvider {
    override fun getTitle(): String {
        return "Permission Required"
    }

    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            "Please grant the bind notification listener permission in the app's settings."
        } else {
            "We need to access your notifications to display them in the app."
        }
    }
}