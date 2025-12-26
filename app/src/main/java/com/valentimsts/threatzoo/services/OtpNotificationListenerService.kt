package com.valentimsts.threatzoo.services

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

import com.valentimsts.threatzoo.domain.model.Notification as ThreatZooNotification
import com.valentimsts.threatzoo.domain.repository.NotificationRepository

@AndroidEntryPoint
class OtpNotificationListenerService : NotificationListenerService() {

    @Inject
    lateinit var notificationRepository: NotificationRepository

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val notification = sbn.notification
        val packageName = sbn.packageName
        val text = notification.extras.getCharSequence(Notification.EXTRA_TEXT)?.toString()

        if (!text.isNullOrEmpty()) {
            extractOtpFromText(text)?.let {
                val noti = ThreatZooNotification(
                    id = 0,
                    originApp = packageName,
                    timestamp = System.currentTimeMillis(),
                    content = text
                )
                notificationRepository.addNotification(noti)
            }
        }
    }

    private fun extractOtpFromText(text: String): String? {
        val otpPattern = "\\d{4,8}".toRegex()
        return otpPattern.find(text)?.value
    }
}
