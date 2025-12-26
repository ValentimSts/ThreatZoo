package com.valentimsts.threatzoo.domain.repository

import com.valentimsts.threatzoo.domain.model.Notification
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun getAllNotifications(): Flow<List<Notification>>
    fun addNotification(notification: Notification)
}
