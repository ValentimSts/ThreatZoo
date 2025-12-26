package com.valentimsts.threatzoo.domain.model

data class Notification(
    val id: Long,
    val originApp: String,
    val timestamp: Long,
    val content: String,
)
