package com.valentimsts.threatzoo.presentation.contacts.permissions

import com.valentimsts.threatzoo.presentation.core.permissions.RationaleTextProvider

class ReadContactsRationaleTextProvider : RationaleTextProvider {
    override fun getTitle(): String {
        return "Permission Required"
    }

    override fun getDescription(isPermanentlyDeclined: Boolean): String {
        return if (isPermanentlyDeclined) {
            "Please grant the read contacts permission in the app's settings."
        } else {
            "We need to access your contacts to display them in the app."
        }
    }
}