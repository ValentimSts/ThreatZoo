package com.valentimsts.threatzoo.data.repository

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.Telephony

import com.valentimsts.threatzoo.domain.model.messages.*
import com.valentimsts.threatzoo.domain.repository.MessageRepository

class MessageRepositoryImpl(
    private val contentResolver: ContentResolver
) : MessageRepository {

    override suspend fun fetchSmsMessages(): List<Message> {
        val messages = mutableListOf<Message>()

        val cursor: Cursor? = contentResolver.query(
            Telephony.Sms.CONTENT_URI,
            arrayOf(
                Telephony.Sms._ID,
                Telephony.Sms.DATE,
                Telephony.Sms.ADDRESS,
                Telephony.Sms.BODY,
                Telephony.Sms.TYPE,
            ),
            null,
            null,
            "${Telephony.Sms.DATE} DESC"
        )

        cursor?.use {
            val idIndex = it.getColumnIndexOrThrow(Telephony.Sms._ID)
            val dateIndex = it.getColumnIndexOrThrow(Telephony.Sms.DATE)
            val addressIndex = it.getColumnIndexOrThrow(Telephony.Sms.ADDRESS)
            val bodyIndex = it.getColumnIndexOrThrow(Telephony.Sms.BODY)
            val typeIndex = it.getColumnIndexOrThrow(Telephony.Sms.TYPE)

            while (it.moveToNext()) {
                val id = it.getLong(idIndex)
                val timestamp = it.getLong(dateIndex)
                val address = it.getString(addressIndex) ?: ""

                var fromAddress: String
                var toAddress: String
                var messageType: MessageType

                val type = it.getInt(typeIndex)
                when(type) {
                    Telephony.Sms.MESSAGE_TYPE_SENT -> {
                        fromAddress = "Device"
                        toAddress = address
                        messageType = MessageType.SENT

                    }
                    Telephony.Sms.MESSAGE_TYPE_INBOX -> {
                        fromAddress = address
                        toAddress = "Device"
                        messageType = MessageType.RECEIVED
                    }
                    else -> {
                        fromAddress = "Device"
                        toAddress = address
                        messageType = MessageType.UNKNOWN
                    }
                }

                val body = it.getString(bodyIndex) ?: ""

                val sms = SmsMessage(
                    id = id,
                    timestamp = timestamp,
                    fromAddress = fromAddress,
                    toAddress = toAddress,
                    body = body,
                    type = messageType
                )
                messages.add(sms)
            }
        }

        return messages
    }

    override suspend fun fetchMmsMessages(): List<Message> {
        val messages = mutableListOf<Message>()

        // TODO(): Implement this later.

        return messages
    }

    override suspend fun fetchAllMessages(): List<Message> {
        val smsList = fetchSmsMessages()
        val mmsList = fetchMmsMessages()
        return (smsList + mmsList).sortedByDescending { it.timestamp }
    }
}
