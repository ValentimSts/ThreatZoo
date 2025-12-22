package com.valentimsts.threatzoo.domain.model.messages

sealed class Message {
    abstract val id: Long
    abstract val timestamp: Long
    abstract val fromAddress: String
    abstract val toAddress: String
}