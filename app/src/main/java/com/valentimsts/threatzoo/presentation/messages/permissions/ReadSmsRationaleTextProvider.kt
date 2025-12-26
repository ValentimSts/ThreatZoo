package com.valentimsts.threatzoo.presentation.messages.permissions

import com.valentimsts.threatzoo.presentation.core.permissions.RationaleTextProvider

class ReadSmsRationaleTextProvider : RationaleTextProvider {
    override fun getTitle(): String {
        return "Permission Required"
    }

    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            "Please grant the read sms permission in the app's settings."
        } else {
            "We need to access your SMS messages to display them in the app."
        }
    }
}