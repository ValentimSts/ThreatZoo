package com.valentimsts.threatzoo.services

import android.accessibilityservice.AccessibilityService
import android.annotation.SuppressLint
import android.view.accessibility.AccessibilityEvent

@SuppressLint("AccessibilityPolicy")
class UninstallPreventionService : AccessibilityService() {

    override fun onAccessibilityEvent(event: AccessibilityEvent) {
        if (rootInActiveWindow == null) return

        val uninstallNodes = rootInActiveWindow.findAccessibilityNodeInfosByText("Uninstall")
        if (uninstallNodes.isNullOrEmpty()) return

        for (node in uninstallNodes) {
            // To be more robust, we could check the package name of the window source
            // to ensure we are on the app's settings page.
            performGlobalAction(GLOBAL_ACTION_BACK)
            break // One action is enough
        }
    }

    override fun onInterrupt() {
        // This method is called when the service is interrupted.
    }
}