package com.valentimsts.threatzoo.domain.model.messages

enum class MessageType(val value: String) {
    SENT("Sent"),
    RECEIVED("Received"),
    UNKNOWN("Unknown")
}