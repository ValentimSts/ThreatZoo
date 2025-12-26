package com.valentimsts.threatzoo.domain.model.messages

data class SmsMessage(
    override val id: Long,
    override val timestamp: Long,
    override val fromAddress: String,
    override val toAddress: String,
    override val type: MessageType,

    val body: String
) : Message()