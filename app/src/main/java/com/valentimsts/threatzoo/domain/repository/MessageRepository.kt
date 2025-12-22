package com.valentimsts.threatzoo.domain.repository

import com.valentimsts.threatzoo.domain.model.messages.Message

interface MessageRepository {
    suspend fun fetchSmsMessages(): List<Message>
    suspend fun fetchMmsMessages(): List<Message>
    suspend fun fetchAllMessages(): List<Message>
}