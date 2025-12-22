package com.valentimsts.threatzoo.domain.model.messages

data class MmsMessage(
    override val id: Long,
    override val timestamp: Long,
    override val fromAddress: String,
    override val toAddress: String,

    val subject: String?,
    val parts: List<MmsPart>
) : Message()