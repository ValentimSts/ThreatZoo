package com.valentimsts.threatzoo.presentation.core.permissions

import android.Manifest

object RequiredPermissions {
    val ReadMessages= RuntimePermission(
        permission = Manifest.permission.READ_SMS,
        name = "Messages",
        description = "Allows the app to access relevant messages"
    )

    val ReadContacts = RuntimePermission(
        permission = Manifest.permission.READ_CONTACTS,
        name = "Contacts",
        description = "Allows the app to access relevant contacts"
    )

    val AccessNotifications = BindNotificationListenerServicePermission

    val AccessAccessibilityFeatures = BindAccessibilityServicePermission

}