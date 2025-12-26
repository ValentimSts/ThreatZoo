package com.valentimsts.threatzoo.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

import com.valentimsts.threatzoo.domain.model.Notification
import com.valentimsts.threatzoo.domain.repository.NotificationRepository

class NotificationRepositoryImpl() : NotificationRepository {
    private val _notification = MutableStateFlow<List<Notification>>(emptyList())

    override fun getAllNotifications(): Flow<List<Notification>> = _notification.asStateFlow()

    override fun addNotification(notification: Notification) {
        val currentNotification = _notification.value.toMutableList()
        currentNotification.add(0, notification)
        _notification.value = currentNotification
    }
}
